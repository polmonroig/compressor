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

    private int lecturePoint;
    private int writePoint;

    private Tree binTree;

    private byte[] out;


    public LZSS(){
        binTree = new Tree();
    }
    /**
     * <p>El metodo de comprimir hace una compresion del texto introducido, con la codificación para LZSS</>
     * @param binaryFile el texto a comprimir
     * @return el texto comprimido
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
        byte[] codeBuff = new byte[17];
        // buffer de la codificacion, [0] guarda los flags y las otras 16 sirven para guardar 8 unidades de codigo
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

        for (i=1; i<=MAX_STORE_LENGTH; i++) binTree.insertNode((short) (r-i));

        binTree.insertNode(r);

        do {
            if (binTree.getMatchLength() > len) binTree.setMatchLength(len);

            if (binTree.getMatchLength() < THRESHOLD){//si hay una coincidencia de 0, 1 o 2 caracteres mejor guardar el caracter sin codificar
                binTree.setMatchLength((short) 1);
                codeBuff[0] |= mask;
                codeBuff[codeBufPos++] = binTree.ringBuffer[r];
            }
            else{
                codeBuff[codeBufPos++] = (byte) binTree.getMatchPosition();
                codeBuff[codeBufPos++] = (byte) (((binTree.getMatchPosition() >> 4) & 0xF0) | (binTree.getMatchLength()- THRESHOLD));
                //2Bytes guardo los 12 primeros bits la posicion de match y la longitud de match en los otros 4
            }
            mask <<= 1;

            if (mask == 0){//hemos almacenado 8 caracteres
                readXBytes(codeBuff, codeBufPos);
                codeBuff[0] = 0;
                codeBufPos = 1;
                mask = 1;
            }

            lastMatchLength = binTree.getMatchLength();

            for (i = 0; i < lastMatchLength; ++i) {
                byte[] aux = new byte[1];
                x = readDecoding(binaryFile, aux, 1);
                if (x == -1) break;
                c = aux[0];

                binTree.deleteNode(s);
                //duplico el principio y el final del buffer
                binTree.ringBuffer[s] = c;
                if (s < MAX_STORE_LENGTH - 1) binTree.ringBuffer[s + RING_SIZE] = c;
                //incremento la posicion y reinicio si ya estoy al final del taanom
                s = (short) ((s + 1) & RING_WRAP);
                r = (short) ((r + 1) & RING_WRAP);
                //nueva string
                binTree.insertNode(r);
            }
            //podriamos haber salido porque no quedaban caracteres, entonces acabamos el trabajo que nos queedaba
            while (i++ < lastMatchLength){
                binTree.deleteNode(s);

                s = (short) ((s + 1) & RING_WRAP);
                r = (short) ((r + 1) & RING_WRAP);

                if (--len != 0) binTree.insertNode(r);
            }
        } while (len > 0);//hasta que no queden caracteres por comprimir

        if (codeBufPos > 1) readXBytes(codeBuff, codeBufPos);

        double bytes = Math.log(binaryFile.length)/Math.log(256);
        int bytesTamFchr = (int) bytes;
        if ((bytes - bytesTamFchr) != 0)  ++bytesTamFchr;
        byte[] ret = new byte[writePoint+bytesTamFchr+1];

        if (writePoint >= 0) System.arraycopy(out, 0, ret, 0, writePoint);
        ret[writePoint] = '#';
        byte[] aux = putTextSize(binaryFile, bytesTamFchr);
        for (int j = 0; j < bytesTamFchr; ++j)
            ret[writePoint+j+1] = aux[j];

        return ret;
    }

    /**
     * <p>El metodo de descomprimir hace una descompresion del texto introducido</>
     * @param binaryFile el texto a comprimir, codificado con formato LZSS
     * @return el texto descomprimido
     */
    @Override
    public byte[] decompress(byte[] binaryFile) {
        byte[] c = new byte[MAX_STORE_LENGTH]; //array de chars que escriben el texto inicial
        byte flags; //8 bits de flags
        out = new byte[getTextSize(binaryFile)-1];
        lecturePoint = 0;
        writePoint = 0;

        int r = RING_SIZE - MAX_STORE_LENGTH;
        Arrays.fill(binTree.ringBuffer, 0, r, (byte) ' ');
        flags = 0;
        int flagCount = 0;

        while(true){
            if (flagCount > 0){
                flags = (byte) (flags >> 1);
                --flagCount;
            }
            else {
                byte[] aux = new byte[1];
                int readResult = readDecoding(binaryFile, aux, 1);
                if (readResult == -1)   break;

                flags = (byte) (aux[0] & 0xFF);
                flagCount = 7;
            }

            if ((flags & 1) != 0) {//viene un caracter no codificado
                if (readDecoding(binaryFile, c, 1) != 1) break;
                readXBytes(c, 1);

                binTree.ringBuffer[r] = c[0];
                r = (short) ((r + 1) & RING_WRAP);
            }
            else {//viene un pair de posicion(12 bits) y longitud (4 bits)
                if (readDecoding(binaryFile, c, 2) != 2) break;
                //De estos dos bytes sacamos su string de coincidencia del ringBuffer
                short pos = (short) ((c[0] & 0xFF) | ((c[1] & 0xF0) << 4));
                short len = (short) ((c[1] & 0x0F) + THRESHOLD);//+ threshold para obtener una longitud de 18 con 4 bits

                for (int k = 0; k < len; k++) {
                    c[k] = binTree.ringBuffer[(pos + k) & RING_WRAP];
                    binTree.ringBuffer[r] = c[k];
                    r = (r + 1) & RING_WRAP;
                }
                readXBytes(c, len);
            }
        }
        return out;
    }

    /**
     * <p></>
     * @param
     * @return
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
     * <p></>
     * @param
     * @return
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
     * <p></>
     * @param
     * @return
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
     * <p></>
     * @param
     * @return
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
     * <p></>
     * @param
     * @return
     */
    private void readXBytes(byte[] code, int x){
        for (int i = 0; i < x && writePoint < out.length; ++i){
            out[writePoint] = code[i];
            ++writePoint;
        }
    }
}
