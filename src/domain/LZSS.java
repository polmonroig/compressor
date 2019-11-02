package domain;

import java.util.*;

public class LZSS extends Algoritme{

    private static final short RING_SIZE = 4096;

    private static final short RING_WRAP = RING_SIZE - 1;

    private static final int MAX_STORE_LENGTH=18;

    private static final int THRESHOLD=3;

    private static final short NOT_USED=RING_SIZE;

    public LZSS(){
        ringBuffer=new byte[RING_SIZE+MAX_STORE_LENGTH-1];
        dad=new short[RING_SIZE+1];
        leftSon=new short[RING_SIZE+1];
        rightSon=new short[RING_SIZE+257];
    }

    @Override
    public byte[] comprimir(byte[] texto){
        //COMPROBAR SI ESTA BIEN ESTO
        //byte[] out = new byte[BUF_SIZE];

        System.out.println(texto.length);

        short i; // an iterator
        short r; // node number in the binary tree
        short s; // position in the ring buffer
        short len; // length of initial string
        short lastMatchLength; // length of last match
        short codeBufPos; // position in the output buffer
        byte[] codeBuff = new byte[17]; // the output buffer
        byte mask; // bit mask for byte 0 of out input
        byte c; // character read from string

        initTree();

        codeBuff[0] = 0;//Aqui gurado los flags
        codeBufPos = 1;

        mask = 1;
        s = 0;
        r = RING_SIZE - MAX_STORE_LENGTH;

        Arrays.fill(ringBuffer, 0, r, (byte) ' ');
        //Para los reads
        int x = readxBytes(texto, r, MAX_STORE_LENGTH);
        if(x <= 0) return out.getBytes();
        len = (short) x;

        for (i=1; i<=MAX_STORE_LENGTH; i++) insertNode((short) (r-i));

        insertNode(r);

        do {
            if (matchLength > len) matchLength = len;

            if (matchLength < THRESHOLD){
                matchLength = 1;
                codeBuff[0] |= mask;
                codeBuff[codeBufPos++] = ringBuffer[r];
            }
            else{
                codeBuff[codeBufPos++] = (byte) matchPosition;
                codeBuff[codeBufPos++] = (byte) (((matchPosition >> 4) & 0xF0) | (matchLength - THRESHOLD));
            }
            mask <<= 1;

            if (mask == 0){
                writexBytes(codeBuff, codeBufPos);
                //reset
                codeBuff[0] = 0;
                codeBufPos = 1;
            }

            lastMatchLength = matchLength;

            for (i = 0; i < lastMatchLength; ++i) {
                x = readxBytes(texto, 0, 1);
                if (x == -1) break;
                c = (byte) x;

                deleteNode(s);

                ringBuffer[s] = c;
                if (s < MAX_STORE_LENGTH - 1) ringBuffer[s + RING_SIZE] = c;

                s = (short) ((s + 1) & RING_WRAP);
                r = (short) ((r + 1) & RING_WRAP);

                insertNode(r);
            }
            while (i++ < lastMatchLength){
                deleteNode(s);

                s = (short) ((s + 1) & RING_WRAP);
                r = (short) ((r + 1) & RING_WRAP);

                if (--len != 0) insertNode(r);
            }
        } while (len > 0);

        if (codeBufPos > 1) writexBytes(codeBuff, codeBufPos);

        return  out.getBytes();
    }


    public byte[] descomprimir(byte[] texto) {
        byte[] c = new byte[MAX_STORE_LENGTH]; //array de chars
        byte flags; //8 bits de flags

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
                int readResult = readDecodification(texto, c, 1);
                if (readResult == -1)   break;

                flags = (byte) (readResult & 0xFF);
                flagCount = 7;
            }

            if ((flags & 1) != 0) {//viene un caracter no codificado
                if (readDecodification(texto, c, 1) != 1) break;
                writexBytes(c, 1);

                ringBuffer[r] = c[0];
                r = (short) ((r + 1) & RING_WRAP);
            }
            else {//viene un pair de posicion(12 bits) y longitud (4 bits)
                if (readDecodification(texto, c, 2) != 2)    break;
            }

            short pos = (short) ((c[0] & 0xFF) | ((c[1] & 0xF0) << 4));
            short len = (short) ((c[1] & 0x0F) + THRESHOLD);//+ threshold para obtener una longitud de 18 con 4 bits

            for (int k = 0; k < len; k++) {
                c[k] = ringBuffer[(pos + k) & RING_WRAP];
                ringBuffer[r] = c[k];
                r = (r + 1) & RING_WRAP;
            }
            writexBytes(c, len);
        }
        return out.getBytes();
    }

    private void initTree() {
        Arrays.fill(dad, 0, dad.length, NOT_USED);
        Arrays.fill(leftSon, 0, leftSon.length, NOT_USED);
        Arrays.fill(rightSon, 0, rightSon.length, NOT_USED);
    }

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

    private int readxBytes(byte[] texto, int offset, int x){
        int j = 0;
        for (int i = 0; i < x && lecturePoint < texto.length; ++i,++j){
            ringBuffer[offset+i] = texto[lecturePoint];
            ++lecturePoint;
        }
        if(j == 0) return -1;
        else return j;
    }

    private int readDecodification(byte[] texto, byte[] chars, int x){
        int j = 0;
        for (int i = 0; i < x && lecturePoint < texto.length; ++i,++j){
            chars[i] = texto[lecturePoint];
            ++lecturePoint;
        }
        if(j == 0) return -1;
        else return j;
    }

    private void writexBytes(byte[] code, int x){
        byte[] aux = new byte[x];
        for (int i = 0; i < x; ++i) aux[i] = code[i];
        String auxStr = new String(aux);
        out += auxStr;
    }

    private byte[] ringBuffer;

    private short matchPosition;
    private short matchLength;

    private int lecturePoint = 0;

    private short[] dad;
    private short[] leftSon;
    private short[] rightSon;


    private String out = "";
}
