package domain;

import java.util.Arrays;

/**
 * Data structure used for LZSS algorithm
 *
 */
public class Tree {
    /**
     * <p>12 Bytes length value used for setting lengths</p>
     * */
        public static final short RING_SIZE = 4096;
    /**
     * <p>11 bits all set to 1, used for values that we don't want to be more than RING_SIZE to be used as %RING_SIZE</p>
     * */
        public static final short RING_WRAP = RING_SIZE - 1;
    /**
     * <p>Max legth of characters can be stored on the bits stored for the compression</p>
     * */
        public static final int MAX_STORE_LENGTH=18;
    /**
     * <p>If the match is less than this value we won't translate the characters seqquence</p>
     * */
        public static final int THRESHOLD=3;
    /**
     * <p>Value that indicates if a node hasn't any son or dad, as we always use values inferiors to RING_SIZE we won't misunderstand the value</p>
     * */
        public static final short NOT_USED=RING_SIZE;
    /**
     * <p>The 4096 first positions stores the last characters read, and the next 17 are used to store the first 17 ones for some useful reasons</p>
     * */
        public byte[] ringBuffer;//buffer para encontrar coincidencias
    /**
     * <p>Has the value of the position in which the last match is found on the ringBuffer</p>
     * */
        private short matchPosition;//posicion de match en el ring buffer, se calcula en insertNode
    /**
     * <p>Has the value of the number of consecutive last characters that has been found of the last character on the ringBuffer</p>
     * */
        private short matchLength;//numero de coincidencias consecutivas en ringBuffer
    /**
     * <p>dad[i] for i = 0 up to RING_SIZE will be used for the dad of each node i, one for each position on the ringBuffer</p>
     * */
        private short[] dad;
    /**
     * <p>leftSon[i] is the left son from node i</p>
     * */
        private short[] leftSon;
    /**
     * <p>righttSon[i] is the right son from node i, also rightSon[RING_SIZE + i + 1]
     *  contains the roots of the tree for the strings started with character i</p>
     * */
        private short[] rightSon;

        public Tree(){
            ringBuffer=new byte[RING_SIZE+MAX_STORE_LENGTH-1];//4096+17 bytes para encontrar coincidencias
            dad=new short[RING_SIZE+1];
            leftSon=new short[RING_SIZE+1];
            rightSon=new short[RING_SIZE+257];
        }

        /**
         * <p>Fills all the buffers of the tree with ' ', which represents a not used value</p>
         */
        public void initTree() {
            Arrays.fill(dad, 0, dad.length, NOT_USED);
            Arrays.fill(leftSon, 0, leftSon.length, NOT_USED);
            Arrays.fill(rightSon, 0, rightSon.length, NOT_USED);
        }

        /**
         * <p>The string to be inserted is identified by pos. If the matched length is MAX_STORE_lENGTH, then an old node is removed in favour of the new one.
         *  Modifies global variables matchPosition and matchLength with the maximum values it can achieve with the inserted string</p>
         * @param pos The position in the ringBuffer and a tree node
         */
        //Busca para el nuevo caracter todas las coincidencias y se queda con la mas grande
        public void insertNode(short pos) {
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
         * <p>Remove a node from the tree</p>
         * @param node the node to remove
         */
        //Borra el arbol creado para encontrar las coincidencias, porque ya se ha usado
        public void deleteNode(short node) {
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
     * <p>A getter for matchLength</p>
     * @return The matchLength value
     */
    public short getMatchLength() {
        return matchLength;
    }
    /**
     * <p>A setter for matchLength</p>
     * @param matchLength Puts on the Tree matchLength matchLength value
     */
    public void setMatchLength(short matchLength) {
        this.matchLength = matchLength;
    }
    /**
     * <p>A getter for matchPosition</p>
     * @return The value of matchPosition
     */
    public short getMatchPosition() {
        return matchPosition;
    }
}

