package domain;

import java.util.*;

import static domain.Tree.*;

/**
 * LZSS ASCII compression algorithm
 *
 * @author David Santos Plana
 *
 */
 
public class LZSS implements Algorithm {
    /**
     * <p>Variable that indicates the position of the text that has to be read</p>
     * */
    private int lecturePoint;
    /**
     * <p>Variable that indicates the position of out that has to be written</p>
     * */
    private int writePoint;
    /**
     * <p>The data structure that the algorithm takes use</p>
     * */
    private Tree binTree;
    /**
     * <p>Empty variable which is filled dinamically with the output</p>
     * */
    private byte[] out;


    public LZSS(){
        binTree = new Tree();
    }
    /**
     * <p>The method compress makes a compresion of the given input text</p>
     * @param binaryFile The byte[] of text to compress
     * @return The compressed text
     */
    @Override
    public byte[] compress(byte[] binaryFile){
        out = new byte[binaryFile.length];
        lecturePoint = 0;
        writePoint = 0;

        short i; // an iterator
        short r; // node number in the binary tree
        short s; // position in the ring buffer
        short len; // length of initial string
        short lastMatchLength;
        short codeBufPos;
        byte[] codeBuff = new byte[17];// buffer de la codificacion, [0] guarda los flags y las otras 16 sirven para guardar 8 unidades de codigo
        byte mask; // mete los 1 a codeBuff[0] cuando no hay coincidencia
        byte c; // character read from string

        binTree.initTree();

        codeBuff[0] = 0;//1 si es una letra no codificada, 0 si viene un pair<posicion,length>
        codeBufPos = 1;

        mask = 1;
        s = 0;
        r = RING_SIZE - MAX_STORE_LENGTH;

        Arrays.fill(binTree.ringBuffer, 0, r, (byte) ' ');

        int x = readXBytes(binaryFile, r, MAX_STORE_LENGTH);
        if(x <= 0) return binaryFile;
        len = (short) x;
        //añado a las ultimas posiciones del arbol las MAX_STORE_LENGTH strings, las cuales empezaran por uno o mas ' '
        for (i=1; i<=MAX_STORE_LENGTH; i++) binTree.insertNode((short) (r-i));

        binTree.insertNode(r);//añado la ultima string. De esta manera las variables matchLength y matcth position estan listas

        do {
            if (binTree.getMatchLength() > len) binTree.setMatchLength(len);//evito un posible error por si matchLength es mas grande que la medida del texto

            if (binTree.getMatchLength() < THRESHOLD){//si hay una coincidencia de 0, 1 o 2 caracteres mejor guardar el caracter sin codificar
                //marco que vendra un caracter no codificado y este lo añado
                binTree.setMatchLength((short) 1);
                codeBuff[0] |= mask;
                codeBuff[codeBufPos++] = binTree.ringBuffer[r];
            }
            else{
                codeBuff[codeBufPos++] = (byte) binTree.getMatchPosition();
                codeBuff[codeBufPos++] = (byte) (((binTree.getMatchPosition() >> 4) & 0xF0) | (binTree.getMatchLength()- THRESHOLD));
                //2Bytes guardo los 12 primeros bits la posicion de match y la longitud de match en los otros 4
            }
            mask <<= 1;//ajusto la mascara para el marcar el siguiente bit

            if (mask == 0){//hemos almacenado 8 caracteres vamos a ponerlos en el output y reiniciciamos las variables
                writeXBytes(codeBuff, codeBufPos);
                codeBuff[0] = 0;
                codeBufPos = 1;
                mask = 1;
            }

            lastMatchLength = binTree.getMatchLength();

            for (i = 0; i < lastMatchLength; ++i) {//
                byte[] aux = new byte[1];
                x = readDecoding(binaryFile, aux, 1);
                if (x == -1) break;
                c = aux[0];

                binTree.deleteNode(s);
                //duplico el principio y el final del buffer
                binTree.ringBuffer[s] = c;
                if (s < MAX_STORE_LENGTH - 1) binTree.ringBuffer[s + RING_SIZE] = c;
                //incremento la posicion y reinicio si ya estoy al final del tope
                s = (short) ((s + 1) & RING_WRAP);//nueva posicion para el ring buffer
                r = (short) ((r + 1) & RING_WRAP);//siguiente nodo para el arbol
                //nueva string
                binTree.insertNode(r);
            }
            //podriamos haber salido porque no quedaban caracteres de input, entonces acabamos el trabajo que nos quedaba
            while (i++ < lastMatchLength){
                binTree.deleteNode(s);//borramos strings viejas

                s = (short) ((s + 1) & RING_WRAP);
                r = (short) ((r + 1) & RING_WRAP);

                if (--len != 0) binTree.insertNode(r);//restamos len e añadimos nuevo nodo
            }
        } while (len > 0);//hasta que no queden caracteres por comprimir
        //si queda algo por escribir se escribe
        if (codeBufPos > 1) writeXBytes(codeBuff, codeBufPos);
        //calculamos tamaño del texto en base 256 para que quepa en lo minimo posible
        double bytes = Math.log(binaryFile.length)/Math.log(256);
        int bytesTamFchr = (int) bytes;
        if ((bytes - bytesTamFchr) != 0)  ++bytesTamFchr;
        byte[] ret = new byte[writePoint+bytesTamFchr+1];//ajustamos tamaño de retorno al minimo necesario

        if (writePoint >= 0) System.arraycopy(out, 0, ret, 0, writePoint);//ponemos los valores de out al nuevoo array
        ret[writePoint] = '#';
        //insertamos tamaño de fichero original para usarlo al descomprimir
        byte[] aux = putTextSize(binaryFile, bytesTamFchr);
        for (int j = 0; j < bytesTamFchr; ++j)
            ret[writePoint+j+1] = aux[j];

        return ret;
    }

    /**
     * <p>The method decompress makes a decompression of the given compressed text</p>
     * @param binaryFile The  byte[] of the content to decompress, with a LZSS format
     * @return The decompressed text
     */
    @Override
    public byte[] decompress(byte[] binaryFile) {
        byte[] c = new byte[MAX_STORE_LENGTH]; //array de chars que escriben el texto inicial
        byte flags = 0; //8 bits de flags
        out = new byte[getTextSize(binaryFile)-1];
        lecturePoint = 0;
        writePoint = 0;

        int r = RING_SIZE - MAX_STORE_LENGTH;
        Arrays.fill(binTree.ringBuffer, 0, r, (byte) ' ');
        int flagCount = 0;//contador de flags

        while(true){
            if (flagCount > 0){//si quedan flags cojemos nuevo flag
                flags = (byte) (flags >> 1);
                --flagCount;
            }
            else {//leemos el siguiente byte que tendra los flags
                byte[] aux = new byte[1];
                int readResult = readDecoding(binaryFile, aux, 1);
                if (readResult == -1)   break;//si no queda nada que leer terminamos

                flags = (byte) (aux[0] & 0xFF);
                flagCount = 7;
            }

            if ((flags & 1) != 0) {//viene un caracter no codificado
                if (readDecoding(binaryFile, c, 1) != 1) break;
                writeXBytes(c, 1);

                binTree.ringBuffer[r] = c[0];//añadimos el caracter al ringbuffer donde se encontraban las coincidencias
                r = (short) ((r + 1) & RING_WRAP);
            }
            else {//viene un pair de posicion(12 bits) y longitud (4 bits)
                if (readDecoding(binaryFile, c, 2) != 2) break;
                //De estos dos bytes sacamos su string de coincidencia del ringBuffer
                short pos = (short) ((c[0] & 0xFF) | ((c[1] & 0xF0) << 4));
                short len = (short) ((c[1] & 0x0F) + THRESHOLD);//+ threshold para obtener una longitud de 18 con 4 bits

                for (int k = 0; k < len; k++) {//cojemos los caracteres de la coincidencia de la posicion del arbol
                    //copiamos los caracteres repetidos a los mas nuevos del ringBuffer
                    c[k] = binTree.ringBuffer[(pos + k) & RING_WRAP];
                    binTree.ringBuffer[r] = c[k];
                    r = (r + 1) & RING_WRAP;
                }
                writeXBytes(c, len);//escribe los caracteres leidos en el buffer del output
            }
        }
        return out;
    }

    /**
     * <p>Gets of the first parameter its length and puts it on a byte[]</p>
     * @param text The byte[] you want to add its length on it
     * @param extra The size of text.length on basis 256
     * @return A byte[] of the text.length in basis 256
     */
    //añado al final del output #texto.length para saber la medida
    private byte[] putTextSize(byte[] text, int extra){
        byte[] aux = new byte[extra];
        int i = 0;
        for (int j = extra-1; j >= 0; --j, ++i){
            aux[i] = (byte) (text.length >>> j*8);
        }
        return aux;
    }

    /**
     * <p>Decodificates the value added on the function putTextSize</p>
     * @param text A byte[] with te last positions with {#,putTextSize} format
     * @return The value of the original text size
     */
    private int getTextSize(byte[] text) {
        int t = 0, i = text.length - 1, j = -1;
        while (text[i] != '#' && i >= 0) {
            --i;
            ++j;
        }
        for (; i < text.length - 1; ++i, --j) {
            t += ((text[i + 1] & 0xFF) << j * 8);
        }
        return t;
    }


    /**
     * <p>Simulates a reading on a file of the first parameter, puts on the ringBuffer of Tree x bytes starting on the offset position of new positions of the text.
     *  It modifies the global variable lecturePoint, adding at most x on it</p>
     * @param text The text which is being compressed
     * @param offset The position on the ringBuffer has to be inserted new characters
     * @param x The number of characters you want to read
     *
     * @return The bytes that have been read, -1 if anyone
     */
    private int readXBytes(byte[] text, int offset, int x){
        int j = 0;
        for (int i = 0; i < x && lecturePoint < text.length; ++i,++j){
            binTree.ringBuffer[offset+i] = text[lecturePoint];
            ++lecturePoint;
        }
        if(j == 0) return -1;
        else return j;
    }

    /**
     * <p>Simulates a reading on a file of the first parameter, puts on the second parameter x bytes of new positions of the text.
     *  It modifies the global variable lecturePoint, adding at most x on it</p>
     * @param text The text which is being decompressed
     * @param chars The byte[] you want to fill
     * @param x The number of characters you want to read
     *
     * @return The bytes that have been read, -1 if anyone
     */
    private int readDecoding(byte[] text, byte[] chars, int x){
        int j = 0;
        for (int i = 0; i < x && lecturePoint < text.length; ++i,++j){
            chars[i] = text[lecturePoint];
            ++lecturePoint;
        }
        if(j == 0) return -1;
        else return j;
    }


/**
 * <p>Simulates a writing on a file, puts on the global variable out at most x bytes, it's not necessary knowing how many bytes have been written.
 *  It modifies the global variable writePoint, adding at most x on it</p>
 * @param code The text which is being decompressed
 * @param x The number of characters you want to write
 * */
    private void writeXBytes(byte[] code, int x){
        for (int i = 0; i < x && writePoint < out.length; ++i){
            out[writePoint] = code[i];
            ++writePoint;
        }
    }
}
