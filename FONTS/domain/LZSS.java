package domain;

import java.util.*;

/**
 * LZSS ASCII compression algorithm
 *
 * @author David Santos Plana
 *
 */
 
public class LZSS extends Algorithm {

    private static final short RING_SIZE = 4096;

    private static final short RING_WRAP = RING_SIZE - 1;

    private static final int MAX_STORE_LENGTH=18;

    private static final int THRESHOLD=3;

    private static final short NOT_USED=RING_SIZE;

    private byte[] ringBuffer;//buffer para encontrar coincidencias

    private short matchPosition;//posicion de match en el ring buffer, se calcula en insertNode
    private short matchLength;//numero de coincidencias consecutivas en ringBuffer

    private int lecturePoint;
    private int writePoint;

    private short[] dad;
    private short[] leftSon;
    private short[] rightSon;


    private byte[] out;


    public LZSS(){
        ringBuffer=new byte[RING_SIZE+MAX_STORE_LENGTH-1];//4096+17 bytes para encontrar coincidencias
        dad=new short[RING_SIZE+1];
        leftSon=new short[RING_SIZE+1];
        rightSon=new short[RING_SIZE+257];
    }


    /**
     * <p>El metodo de comprimir hace una compresion del texto introducido, con la codificación para LZSS</>
     * @param binaryFile el texto a comprimir
     * @return el texto comprimido
     */
    @Override
    public byte[] compress(byte[] binaryFile){
        long startTime = System.nanoTime(); // empezar contador de tiempo
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

        initTree();

        codeBuff[0] = 0;//1 si es una letra no codificada, 0 si viene un pair<posicion,length>
        codeBufPos = 1;

        mask = 1;
        s = 0;
        r = RING_SIZE - MAX_STORE_LENGTH;

        Arrays.fill(ringBuffer, 0, r, (byte) ' ');

        int x = readXBytes(binaryFile, r, MAX_STORE_LENGTH);
        if(x <= 0) return binaryFile;
        len = (short) x;

        for (i=1; i<=MAX_STORE_LENGTH; i++) insertNode((short) (r-i));

        insertNode(r);

        do {
            if (matchLength > len) matchLength = len;

            if (matchLength < THRESHOLD){//si hay una coincidencia de 0, 1 o 2 caracteres mejor guardar el caracter sin codificar
                matchLength = 1;
                codeBuff[0] |= mask;
                codeBuff[codeBufPos++] = ringBuffer[r];
            }
            else{
                codeBuff[codeBufPos++] = (byte) matchPosition;
                codeBuff[codeBufPos++] = (byte) (((matchPosition >> 4) & 0xF0) | (matchLength - THRESHOLD));
                //2Bytes guardo los 12 primeros bits la posicion de match y la longitud de match en los otros 4
            }
            mask <<= 1;

            if (mask == 0){//hemos almacenado 8 caracteres
                readXBytes(codeBuff, codeBufPos);
                codeBuff[0] = 0;
                codeBufPos = 1;
                mask = 1;
            }

            lastMatchLength = matchLength;

            for (i = 0; i < lastMatchLength; ++i) {
                byte[] aux = new byte[1];
                x = readDecoding(binaryFile, aux, 1);
                if (x == -1) break;
                c = aux[0];

                deleteNode(s);
                //duplico el principio y el final del buffer
                ringBuffer[s] = c;
                if (s < MAX_STORE_LENGTH - 1) ringBuffer[s + RING_SIZE] = c;
                //incremento la posicion y reinicio si ya estoy al final del taanom
                s = (short) ((s + 1) & RING_WRAP);
                r = (short) ((r + 1) & RING_WRAP);
                //nueva string
                insertNode(r);
            }
            //podriamos haber salido porque no quedaban caracteres, entonces acabamos el trabajo que nos queedaba
            while (i++ < lastMatchLength){
                deleteNode(s);

                s = (short) ((s + 1) & RING_WRAP);
                r = (short) ((r + 1) & RING_WRAP);

                if (--len != 0) insertNode(r);
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

        // Calculo estadisticas
        long endTime = System.nanoTime();
        this.localStats.setOriginalFileSize(binaryFile.length);
        this.localStats.setCompressedFileSize(ret.length);
        this.localStats.setCompressionDegree(((float)this.getCompressedSize() / (float)this.getOriginalSize()) * 100);
        this.localStats.setCompressionTime((float)((endTime - startTime) / 1000000.0)); // miliseconds
        this.localStats.setCompressionSpeed(binaryFile.length / this.localStats.getCompressionTime());

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
        Arrays.fill(ringBuffer, 0, r, (byte) ' ');
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

                ringBuffer[r] = c[0];
                r = (short) ((r + 1) & RING_WRAP);
            }
            else {//viene un pair de posicion(12 bits) y longitud (4 bits)
                if (readDecoding(binaryFile, c, 2) != 2) break;
                //De estos dos bytes sacamos su string de coincidencia del ringBuffer
                short pos = (short) ((c[0] & 0xFF) | ((c[1] & 0xF0) << 4));
                short len = (short) ((c[1] & 0x0F) + THRESHOLD);//+ threshold para obtener una longitud de 18 con 4 bits

                for (int k = 0; k < len; k++) {
                    c[k] = ringBuffer[(pos + k) & RING_WRAP];
                    ringBuffer[r] = c[k];
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
    private int getTextSize(byte[] text){
        int t = 0, i = text.length-1, j = -1;
        while(text[i] != '#' && i >= 0) {
            --i;
            ++j;
        }
        for (;i < text.length-1; ++i, --j){
            t += ((text[i+1] & 0xFF) << j*8);
        }
        return t;
    }

    /**
     * <p></>
     * @param
     * @return
     */
    private void initTree() {
        Arrays.fill(dad, 0, dad.length, NOT_USED);
        Arrays.fill(leftSon, 0, leftSon.length, NOT_USED);
        Arrays.fill(rightSon, 0, rightSon.length, NOT_USED);
    }

    /**
     * <p></>
     * @param
     * @return
     */
    //Busca para el nuevo caracter todas las coincidencias y se queda con la mas grande
    private void insertNode(short pos) {
        assert pos >= 0 && pos < RING_SIZE;

        int cmp = 1;
        short key = pos;

        short p = (short) (RING_SIZE + 1 + (ringBuffer[key] & 0xFF));
        assert p > RING_SIZE;

        leftSon[pos] = NOT_USED;
        rightSon[pos] = NOT_USED;


        matchLength = 0;

        while (true) {
            if (cmp >= 0) {
                if (rightSon[p] != NOT_USED) {
                    p = rightSon[p];
                } else {
                    rightSon[p] = pos;
                    dad[pos] = p;
                    return;
                }
            } else {
                if (leftSon[p] != NOT_USED) {
                    p = leftSon[p];
                } else {
                    leftSon[p] = pos;
                    dad[pos] = p;
                    return;
                }
            }

            short i;
            for (i = 1; i < MAX_STORE_LENGTH; i++) {
                cmp = (ringBuffer[key + i] & 0xFF) - (ringBuffer[p + i] & 0xFF);
                if (cmp != 0) {
                    break;
                }
            }

            if (i > matchLength) {
                matchPosition = p;
                matchLength = i;

                if (i >= MAX_STORE_LENGTH) {
                    break;
                }
            }
        }

        dad[pos] = dad[p];
        leftSon[pos] = leftSon[p];
        rightSon[pos] = rightSon[p];

        dad[leftSon[p]] = pos;
        dad[rightSon[p]] = pos;

        if (rightSon[dad[p]] == p) {
            rightSon[dad[p]] = pos;
        }
        else {
            leftSon[dad[p]] = pos;
        }

        // Remove "p"
        dad[p] = NOT_USED;
    }

    /**
     * <p></>
     * @param
     * @return
     */
    //Borra el arbol creado para encontrar las coincidencias, porque ya se ha usado
    private void deleteNode(short node) {
        assert node >= 0 && node < (RING_SIZE + 1);

        short q;

        if (dad[node] == NOT_USED) {
            // not in tree, nothing to do
            return;
        }

        if (rightSon[node] == NOT_USED) {
            q = leftSon[node];
        } else if (leftSon[node] == NOT_USED) {
            q = rightSon[node];
        } else {
            q = leftSon[node];
            if (rightSon[q] != NOT_USED) {
                do {
                    q = rightSon[q];
                } while (rightSon[q] != NOT_USED);

                rightSon[dad[q]] = leftSon[q];
                dad[leftSon[q]] = dad[q];
                leftSon[q] = leftSon[node];
                dad[leftSon[node]] = q;
            }

            rightSon[q] = rightSon[node];
            dad[rightSon[node]] = q;
        }

        dad[q] = dad[node];

        if (rightSon[dad[node]] == node) {
            rightSon[dad[node]] = q;
        } else {
            leftSon[dad[node]] = q;
        }

        dad[node] = NOT_USED;
    }

    /**
     * <p></>
     * @param
     * @return
     */
    private int readXBytes(byte[] text, int offset, int x){
        int j = 0;
        for (int i = 0; i < x && lecturePoint < text.length; ++i,++j){
            ringBuffer[offset+i] = text[lecturePoint];
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
