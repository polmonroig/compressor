package domain;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.Math;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This is one of the algortihm to compress files, in specific, it
 * compress image files. This class implements de JPEG algortihm.
 **/

public class JPEG implements Algorithm {
    /**
     * This is the quality of the image
     **/
    private int quality = 0;
    /**
     * These are the variables to make the decompression part
     **/
    private int width;
    private int height;
    private int sizeY;
    private int sizeCB;
    private int sizeCR;
    private int sizeYc;
    private int sizeCBc;
    private int sizeCRc;
    private int iteradorFreq;
    private int i;
    private Map<Integer, Integer> FreqY;
    private Map<Integer, Integer> FreqCB;
    private Map<Integer, Integer> FreqCR;
    private ArrayList<Integer> Ydes;
    private ArrayList<Integer> CBdes;
    private ArrayList<Integer> CRdes;
    private int[][] FinalR;
    private int[][] FinalG;
    private int[][] FinalB;
    /**
     * These are the variables to make the compression part
     **/
    private boolean anchura;
    private int iterator;
    private int[][][] imagenYCbCr;
    private ArrayList<Integer> Yencoding;
    private ArrayList<Integer> Cbencoding;
    private ArrayList<Integer> Crencoding;
    private StringBuilder FY;
    private StringBuilder FCB;
    private StringBuilder FCR;
    /**
     * These are the two tables of quality, it has 12 different qualities
     **/
    private static double[][][] QtablesLuminance = {
            {
                    {32, 33, 51, 81, 66, 39, 34, 17}, {33, 36, 48, 47, 28, 23, 12, 12}, {51, 48, 47, 28, 23, 12, 12, 12}, {81, 47, 28, 23, 12, 12, 12, 12}, {66, 28, 23, 12, 12, 12, 12, 12}, {39, 23, 12, 12, 12, 12, 12, 12}, {34, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {27, 26, 41, 65, 66, 39, 34, 17}, {26, 29, 38, 47, 28, 23, 12, 12}, {41, 38, 47, 28, 23, 12, 12, 12}, {65, 47, 28, 23, 12, 12, 12, 12}, {66, 28, 23, 12, 12, 12, 12, 12}, {39, 23, 12, 12, 12, 12, 12, 12}, {34, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {20, 17, 26, 41, 51, 39, 34, 17}, {17, 18, 24, 39, 28, 23, 12, 12}, {26, 24, 32, 28, 23, 12, 12, 12}, {41, 39, 28, 23, 12, 12, 12, 12}, {51, 28, 23, 12, 12, 12, 12, 12}, {39, 23, 12, 12, 12, 12, 12, 12}, {34, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {18, 14, 22, 35, 44, 39, 34, 17}, {14, 16, 21, 34, 28, 23, 12, 12}, {22, 21, 27, 28, 23, 12, 12, 12}, {35, 34, 28, 23, 12, 12, 12, 12}, {44, 28, 23, 12, 12, 12, 12, 12}, {39, 23, 12, 12, 12, 12, 12, 12}, {34, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {16, 11, 17, 27, 34, 39, 34, 17}, {11, 12, 16, 26, 28, 23, 12, 12}, {17, 16, 21, 28, 23, 12, 12, 12}, {27, 26, 28, 23, 12, 12, 12, 12}, {34, 28, 23, 12, 12, 12, 12, 12}, {39, 23, 12, 12, 12, 12, 12, 12}, {34, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {12, 8, 13, 21, 26, 32, 34, 17}, {8, 9, 12, 20, 27, 23, 12, 12}, {13, 12, 16, 26, 23, 12, 12, 12}, {21, 20, 26, 23, 12, 12, 12, 12}, {26, 27, 23, 12, 12, 12, 12, 12}, {32, 23, 12, 12, 12, 12, 12, 12}, {34, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {8, 6, 9, 14, 17, 21, 28, 17}, {6, 6, 8, 13, 18, 23, 12, 12}, {9, 8, 11, 17, 23, 12, 12, 12}, {14, 13, 17, 23, 12, 12, 12, 12}, {17, 18, 23, 12, 12, 12, 12, 12}, {21, 23, 12, 12, 12, 12, 12, 12}, {28, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {10, 7, 11, 18, 22, 27, 34, 17}, {7, 8, 10, 17, 23, 23, 12, 12}, {11, 10, 14, 22, 23, 12, 12, 12}, {18, 17, 22, 23, 12, 12, 12, 12}, {22, 23, 23, 12, 12, 12, 12, 12}, {27, 23, 12, 12, 12, 12, 12, 12}, {34, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {6, 4, 7, 11, 14, 17, 22, 17}, {4, 5, 6, 10, 14, 19, 12, 12}, {7, 6, 8, 14, 19, 12, 12, 12}, {11, 10, 14, 19, 12, 12, 12, 12}, {14, 14, 19, 12, 12, 12, 12, 12}, {17, 19, 12, 12, 12, 12, 12, 12}, {22, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {4, 3, 4, 7, 9, 11, 14, 17}, {3, 3, 4, 7, 9, 12, 12, 12}, {4, 4, 5, 9, 12, 12, 12, 12}, {7, 7, 9, 12, 12, 12, 12, 12}, {9, 9, 12, 12, 12, 12, 12, 12}, {11, 12, 12, 12, 12, 12, 12, 12}, {14, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {2, 2, 3, 4, 5, 6, 8, 11}, {2, 2, 2, 4, 5, 7, 9, 11}, {3, 2, 3, 5, 7, 9, 11, 12}, {4, 4, 5, 7, 9, 11, 12, 12}, {5, 5, 7, 9, 11, 12, 12, 12}, {6, 7, 9, 11, 12, 12, 12, 12}, {8, 9, 11, 12, 12, 12, 12, 12}, {11, 11, 12, 12, 12, 12, 12, 12},
            },
            {
                    {1, 1, 1, 2, 3, 3, 4, 5}, {1, 1, 1, 2, 3, 4, 4, 6}, {1, 1, 2, 3, 4, 4, 5, 7}, {2, 2, 3, 4, 4, 5, 7, 8}, {3, 3, 4, 4, 5, 7, 8, 8}, {3, 4, 4, 5, 7, 8, 8, 8}, {4, 4, 5, 7, 8, 8, 8, 8}, {5, 6, 7, 8, 8, 8, 8, 8},
            },
            {
                    {1, 1, 1, 1, 1, 1, 1, 2}, {1, 1, 1, 1, 1, 1, 1, 2}, {1, 1, 1, 1, 1, 1, 2, 2}, {1, 1, 1, 1, 1, 2, 2, 3}, {1, 1, 1, 1, 2, 2, 3, 3}, {1, 1, 1, 2, 2, 3, 3, 3}, {1, 1, 2, 2, 3, 3, 3, 3}, {2, 2, 2, 3, 3, 3, 3, 3},
            }
    };

    private static double[][][] QtablesChrominance = {
            {
                    {34, 51, 52, 34, 20, 20, 17, 17}, {51, 38, 24, 14, 14, 12, 12, 12}, {52, 24, 14, 14, 12, 12, 12, 12}, {34, 14, 14, 12, 12, 12, 12, 12}, {20, 14, 12, 12, 12, 12, 12, 12}, {20, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {29, 41, 52, 34, 20, 20, 17, 17}, {41, 38, 24, 14, 14, 12, 12, 12}, {52, 24, 14, 14, 12, 12, 12, 12}, {34, 14, 14, 12, 12, 12, 12, 12}, {20, 14, 12, 12, 12, 12, 12, 12}, {20, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {21, 26, 33, 34, 20, 20, 17, 17}, {26, 29, 24, 14, 14, 12, 12, 12}, {33, 24, 14, 14, 12, 12, 12, 12}, {34, 14, 14, 12, 12, 12, 12, 12}, {20, 14, 12, 12, 12, 12, 12, 12}, {20, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {20, 22, 29, 34, 20, 20, 17, 17}, {22, 25, 24, 14, 14, 12, 12, 12}, {29, 24, 14, 14, 12, 12, 12, 12}, {34, 14, 14, 12, 12, 12, 12, 12}, {20, 14, 12, 12, 12, 12, 12, 12}, {20, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {17, 17, 22, 34, 20, 20, 17, 17}, {17, 19, 22, 14, 14, 12, 12, 12}, {22, 22, 14, 14, 12, 12, 12, 12}, {34, 14, 14, 12, 12, 12, 12, 12}, {20, 14, 12, 12, 12, 12, 12, 12}, {20, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {13, 13, 17, 27, 20, 20, 17, 17}, {13, 14, 17, 14, 14, 12, 12, 12}, {17, 17, 14, 14, 12, 12, 12, 12}, {27, 14, 14, 12, 12, 12, 12, 12}, {20, 14, 12, 12, 12, 12, 12, 12}, {20, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {9, 9, 11, 18, 20, 20, 17, 17}, {9, 10, 11, 14, 14, 12, 12, 12}, {11, 11, 14, 14, 12, 12, 12, 12}, {18, 14, 14, 12, 12, 12, 12, 12}, {20, 14, 12, 12, 12, 12, 12, 12}, {20, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {11, 14, 31, 34, 20, 20, 17, 17}, {14, 19, 24, 14, 14, 12, 12, 12}, {31, 24, 14, 14, 12, 12, 12, 12}, {34, 14, 14, 12, 12, 12, 12, 12}, {20, 14, 12, 12, 12, 12, 12, 12}, {20, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {7, 9, 19, 34, 20, 20, 17, 17}, {9, 12, 19, 14, 14, 12, 12, 12}, {19, 19, 14, 14, 12, 12, 12, 12}, {34, 14, 14, 12, 12, 12, 12, 12}, {20, 14, 12, 12, 12, 12, 12, 12}, {20, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {4, 6, 12, 22, 20, 20, 17, 17}, {6, 8, 12, 14, 14, 12, 12, 12}, {12, 12, 14, 14, 12, 12, 12, 12}, {22, 14, 14, 12, 12, 12, 12, 12}, {20, 14, 12, 12, 12, 12, 12, 12}, {20, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12}, {17, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {3, 3, 7, 13, 15, 15, 15, 15}, {3, 4, 7, 13, 14, 12, 12, 12}, {7, 7, 13, 14, 12, 12, 12, 12}, {13, 13, 14, 12, 12, 12, 12, 12}, {15, 14, 12, 12, 12, 12, 12, 12}, {15, 12, 12, 12, 12, 12, 12, 12}, {15, 12, 12, 12, 12, 12, 12, 12}, {15, 12, 12, 12, 12, 12, 12, 12},
            },
            {
                    {1, 2, 4, 7, 8, 8, 8, 8}, {2, 2, 4, 7, 8, 8, 8, 8}, {4, 4, 7, 8, 8, 8, 8, 8}, {7, 7, 8, 8, 8, 8, 8, 8}, {8, 8, 8, 8, 8, 8, 8, 8}, {8, 8, 8, 8, 8, 8, 8, 8}, {8, 8, 8, 8, 8, 8, 8, 8}, {8, 8, 8, 8, 8, 8, 8, 8},
            },
            {
                    {1, 1, 1, 2, 3, 3, 3, 3}, {1, 1, 1, 2, 3, 3, 3, 3}, {1, 1, 2, 3, 3, 3, 3, 3}, {2, 2, 3, 3, 3, 3, 3, 3}, {3, 3, 3, 3, 3, 3, 3, 3}, {3, 3, 3, 3, 3, 3, 3, 3}, {3, 3, 3, 3, 3, 3, 3, 3}, {3, 3, 3, 3, 3, 3, 3, 3},
            }
    };

    /**
     * <p>setQuality set the quality of the image</p>
     *
     * @param quality It has the quality of the image
     */
    public void setQuality(int quality) {
        this.quality = quality;
    }

    /**
     * <p>decompress makes the decompression of an image</p>
     *
     * @param imagen It is the images to decompress
     * @return Array of bytes with the final decompression
     */
    @Override
    public byte[] decompress(byte[] imagen) {
        resetVarD();
        /*
        Declaraciones de variables SI VEO QUE EL ULTIMO BIT ES UN 1 HACER AND OF STRING PASARLO A INT Y MULTIPLICAR POR 1
         */
        char[] imagenaux = new char[imagen.length];
        for (int j = 0; j < imagen.length; j++) {
            imagenaux[j] = (char) (imagen[j] & 0xFF);
        }

        StringBuilder imageBYTES = new StringBuilder(imagen.length * Byte.SIZE);
        for (i = 0; i < Byte.SIZE * imagen.length; i++)
            imageBYTES.append((imagen[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        String aux = imageBYTES.toString();
        i = 0;

        ReadDecompression(aux);

        FinalR = new int[height][width];
        FinalG = new int[height][width];
        FinalB = new int[height][width];

        ReadFreq(imagenaux);

        ReadHuffman(aux);

        /*
        SE ACABA LA LECTURA DEL ARCHIVO
         */

        CreateDecompression();

        return PPMfile();
    }

    /**
     * <p>compress makes the compression of an image</p>
     *
     * @param imagen It is the images to compress
     * @return Array of bytes with the final compression
     */
    @Override
    public byte[] compress(byte[] imagen) {
        ResetVarC();
        /*
        Declaraciones de variables
         */
        char[] imagenaux = new char[imagen.length];
        for (int j = 0; j < imagen.length; j++) {
            imagenaux[j] = (char) (imagen[j] & 0xFF);
        }

        getWidthandHeight(imagenaux);

        imagenYCbCr = new int[height][width][3];
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                int[] RGB = {(int) imagenaux[iterator], (int) imagenaux[iterator + 1], (int) imagenaux[iterator + 2]};
                imagenYCbCr[i][j] = RGBtoYCbCr(RGB);
                iterator += 3;
            }
        }

        CreateCompression();


        Huffman comprimirY = new Huffman();
        Huffman comprimirCB = new Huffman();
        Huffman comprimirCR = new Huffman();

        String Yen = comprimirY.compressHuffman(Yencoding);
        String Cben = comprimirCB.compressHuffman(Cbencoding);
        String Cren = comprimirCR.compressHuffman(Crencoding);


        Map<Integer, Integer> freqY = comprimirY.getFrequencies();
        Map<Integer, Integer> freqCb = comprimirCB.getFrequencies();
        Map<Integer, Integer> freqCr = comprimirCR.getFrequencies();


        CreateFreq(freqY, freqCb, freqCr);

        return JPEGFile(freqY, freqCb, freqCr, Yen, Cben, Cren);
    }

    /**
     * <p>ResetVarc resets the variables for a new compression</p>
     */
    private void ResetVarC() {
        anchura = true;
        iterator = 0;
        Yencoding = new ArrayList<Integer>();
        Cbencoding = new ArrayList<Integer>();
        Crencoding = new ArrayList<Integer>();
        FY = new StringBuilder();
        FCB = new StringBuilder();
        FCR = new StringBuilder();
    }

    /**
     * <p>JPEGFile makes the final encoding for the compression</p>
     *
     * @param Cben   Encoding of the CB variable
     * @param Cren   Encoding of the CR variable
     * @param Yen    Encoding of the Y variable
     * @param freqCb Frequencies for the CB variable
     * @param freqCr Frequencies for the CR variable
     * @param freqY  Frequencies for the Y variable
     * @return Array of bytes with the final compression
     */
    private byte[] JPEGFile(Map<Integer, Integer> freqY, Map<Integer, Integer> freqCb, Map<Integer, Integer> freqCr, String Yen, String Cben, String Cren) {
        String sizeY = Integer.toBinaryString(freqY.size());
        String sizeCB = Integer.toBinaryString(freqCb.size());
        String sizeCR = Integer.toBinaryString(freqCr.size());
        String calidadE = Integer.toBinaryString(quality);
        String widthE = Integer.toBinaryString(width);
        String heightE = Integer.toBinaryString(height);
        String sizeYc = Integer.toBinaryString(Yen.length());
        String sizeCBc = Integer.toBinaryString(Cben.length());
        String sizeCRc = Integer.toBinaryString(Cren.length());
        while (calidadE.length() < 8) calidadE = "0" + calidadE;
        while (widthE.length() < 16) widthE = "0" + widthE;
        while (heightE.length() < 16) heightE = "0" + heightE;
        while (sizeY.length() < 32) sizeY = "0" + sizeY;
        while (sizeCB.length() < 32) sizeCB = "0" + sizeCB;
        while (sizeCR.length() < 32) sizeCR = "0" + sizeCR;
        while (sizeYc.length() < 32) sizeYc = "0" + sizeYc;
        while (sizeCBc.length() < 32) sizeCBc = "0" + sizeCBc;
        while (sizeCRc.length() < 32) sizeCRc = "0" + sizeCRc;
        String result = calidadE + widthE + heightE + sizeY + sizeCB + sizeCR + sizeYc + sizeCBc + sizeCRc;
        String result2 = FY.toString() + FCB.toString() + FCR.toString();
        String result3 = Yen.toString() + Cben.toString() + Cren.toString();
        byte[] a = Utils.toByteArray2(result);
        byte[] b = result2.getBytes();
        byte[] c = Utils.toByteArray2(result3);
        byte[] code1 = new byte[a.length + b.length];
        System.arraycopy(a, 0, code1, 0, a.length);
        System.arraycopy(b, 0, code1, a.length, b.length);
        byte[] finalcode = new byte[code1.length + c.length];
        System.arraycopy(code1, 0, finalcode, 0, code1.length);
        System.arraycopy(c, 0, finalcode, code1.length, c.length);
        return finalcode;
    }

    /**
     * <p>CreateFreq make the encoding of the frequencies</p>
     *
     * @param freqY  Frequencies for the Y variable
     * @param freqCr Frequencies for the Cr variable
     * @param freqCb Frequencies for the Cb variable
     * @return Array of bytes with the final decompression
     */
    private void CreateFreq(Map<Integer, Integer> freqY, Map<Integer, Integer> freqCb, Map<Integer, Integer> freqCr) {
        for (int key : freqY.keySet()) {
            int aux = key;
            String auxs = Integer.toBinaryString(aux);
            FY.append("/");
            FY.append(auxs);
            auxs = Integer.toBinaryString(freqY.get(key));
            FY.append("/");
            FY.append(auxs);
        }

        for (int key : freqCb.keySet()) {
            int aux = key;
            String auxs = Integer.toBinaryString(aux);
            FCB.append("/");
            FCB.append(auxs);
            auxs = Integer.toBinaryString(freqCb.get(key));
            FCB.append("/");
            FCB.append(auxs);
        }

        for (int key : freqCr.keySet()) {
            int aux = key;
            String auxs = Integer.toBinaryString(aux);
            FCR.append("/");
            FCR.append(auxs);
            auxs = Integer.toBinaryString(freqCr.get(key));
            FCR.append("/");
            FCR.append(auxs);
        }
        FCR.append("/");
    }

    /**
     * <p>CreateCompression makes the whole computing part of compression of the algorithm</p>
     **/
    private void CreateCompression() {
        double[][] Ydct = new double[8][8];
        double[][] Cbdct = new double[8][8];
        double[][] Crdct = new double[8][8];

        /*
        Tenemos que coger una matriz de 8x8 que sera nuestro pixel a comprimir. Si la matriz se sale de los parametros
        altura y anchura, haremos padding con 0, para que sea negro.
         */


        for (int i = 0; i < height; i += 8) {
            for (int j = 0; j < width; j += 8) {
                for (int x = 0; x < 8; ++x) {
                    for (int y = 0; y < 8; ++y) {
                        if (i + x >= height || j + y >= width) {
                            Ydct[x][y] = 0;
                            Cbdct[x][y] = 0;
                            Crdct[x][y] = 0;
                        } else {
                            Ydct[x][y] = (double) imagenYCbCr[x + i][y + j][0] - 128;
                            Cbdct[x][y] = (double) imagenYCbCr[x + i][y + j][1] - 128;
                            Crdct[x][y] = (double) imagenYCbCr[x + i][y + j][2] - 128;
                        }
                    }
                }


            /*
            Haremos el DCT2 de cada componente de color para el pixel
             */


                Ydct = dct2(Ydct);
                Cbdct = dct2(Cbdct);
                Crdct = dct2(Crdct);

            /*
            Quantizamos los DCT de cada componente del color
             */

                for (int v = 0; v < 8; ++v) {
                    for (int h = 0; h < 8; ++h) {
                        Ydct[v][h] = Math.round(Ydct[v][h] / QtablesLuminance[quality][v][h]);
                        Cbdct[v][h] = Math.round(Cbdct[v][h] / QtablesChrominance[quality][v][h]);
                        Crdct[v][h] = Math.round(Crdct[v][h] / QtablesChrominance[quality][v][h]);

                    }
                }


            /*
            Hacemos el encoding con Huffman HAY QUE RECORRER EN ZIGZAG CADA MATRIZ
             */

                int u = 1;
                int k = 1;
                int contador = 0;
                for (int element = 0; element < 64; ++element, ++contador) {
                    Yencoding.add((int) Ydct[u - 1][k - 1]);
                    Cbencoding.add((int) Cbdct[u - 1][k - 1]);
                    Crencoding.add((int) Crdct[u - 1][k - 1]);
                    if ((k + u) % 2 != 0) {
                        if (k < 8)
                            k++;
                        else
                            u += 2;
                        if (u > 1)
                            u--;
                    } else {
                        if (u < 8)
                            u++;
                        else
                            k += 2;
                        if (k > 1)
                            k--;
                    }
                }
            }
        }
    }

    /**
     * <p>getWidthandHeight achieve the height and width for the decompression</p>
     *
     * @param imagenaux Is the image to decompress
     */
    private void getWidthandHeight(char[] imagenaux) {
        for (iterator = 3; imagenaux[iterator] != '\n'; ++iterator) {
            if (imagenaux[iterator] == ' ') anchura = false;
            else {
                if (anchura) {
                    int aux = Character.getNumericValue(imagenaux[iterator]);
                    width *= 10;
                    width += aux;
                } else {
                    int aux = Character.getNumericValue(imagenaux[iterator]);
                    height *= 10;
                    height += aux;
                }
            }
        }
        ++iterator;
        while (imagenaux[iterator] != '\n') { ++iterator; }
        ++iterator;
    }

    /**
     * <p>ResetVarc resets the variables for a new compression</p>
     */
    private void resetVarD() {
        width = 0;
        height = 0;
        sizeY = 0;
        sizeCB = 0;
        sizeCR = 0;
        sizeYc = 0;
        sizeCBc = 0;
        sizeCRc = 0;
        iteradorFreq = 0;
        i = 0;
        FreqY = new HashMap<>();
        FreqCB = new HashMap<>();
        FreqCR = new HashMap<>();
        Ydes = new ArrayList<>();
        CBdes = new ArrayList<>();
        CRdes = new ArrayList<>();
    }

    /**
     * <p>PPMfile makes the final encoding for the decompression</p>
     *
     * @return Array of byte with the final encoding
     **/
    private byte[] PPMfile() {
        StringBuilder Finald = new StringBuilder();
        Finald.append("P6");
        Finald.append("\n");
        Finald.append(width);
        Finald.append(" ");
        Finald.append(height);
        Finald.append("\n");
        Finald.append(255);
        Finald.append("\n");
        for (int x = 0; x < height; ++x) {
            for (int y = 0; y < width; ++y) {
                char auxe = (char) FinalR[x][y];
                Finald.append(auxe);
                auxe = (char) FinalG[x][y];
                Finald.append(auxe);
                auxe = (char) FinalB[x][y];
                Finald.append(auxe);
            }
        }
        String Finaldes = Finald.toString();
        return Finaldes.getBytes();
    }

    /**
     * <p>CreateDecompression makes the whole computing part of compression of the algorithm</p>
     **/
    private void CreateDecompression() {
        int iteradorY = 0;
        int iteradorarray = 0;
        int fila = 0;
        int columna = 0;
        while (iteradorY < width / 8 * height / 8) {
            int u = 1;
            int k = 1;
            double[][] Y = new double[8][8];
            double[][] CB = new double[8][8];
            double[][] CR = new double[8][8];
            for (int element = 0; element < 64; ++element) {
                Y[u - 1][k - 1] = (double) Ydes.get(iteradorarray);
                CB[u - 1][k - 1] = (double) CBdes.get(iteradorarray);
                CR[u - 1][k - 1] = (double) CRdes.get(iteradorarray);
                if ((k + u) % 2 != 0) {
                    if (k < 8)
                        k++;
                    else
                        u += 2;
                    if (u > 1)
                        u--;
                } else {
                    if (u < 8)
                        u++;
                    else
                        k += 2;
                    if (k > 1)
                        k--;
                }
                iteradorarray++;
            }
            ++iteradorY;
            //DESQUANTIZAMOS
            for (int m = 0; m < 8; ++m) {
                for (int n = 0; n < 8; ++n) {
                    Y[m][n] = Y[m][n] * QtablesLuminance[quality][m][n];
                    CB[m][n] = CB[m][n] * QtablesChrominance[quality][m][n];
                    CR[m][n] = CR[m][n] * QtablesChrominance[quality][m][n];
                }
            }
            //INVERSA DE LA DCT2
            double[][] Ydct = dct3(Y);
            double[][] CBdct = dct3(CB);
            double[][] CRdct = dct3(CR);

            //SUMAR 128
            for (int m = 0; m < 8; ++m) {
                for (int n = 0; n < 8; ++n) {
                    Ydct[m][n] = Ydct[m][n] + 128;
                    CBdct[m][n] = CBdct[m][n] + 128;
                    CRdct[m][n] = CRdct[m][n] + 128;
                    int[] YCbCr = {(int) Ydct[m][n], (int) CBdct[m][n], (int) CRdct[m][n]};
                    int[] RGB = YCbCrtoRGB(YCbCr);
                    if (fila + m < height & columna + n < width) {
                        FinalR[fila + m][columna + n] = RGB[0];
                        FinalG[fila + m][columna + n] = RGB[1];
                        FinalB[fila + m][columna + n] = RGB[2];
                    }
                }
            }
            if (columna + 8 < width) columna = columna + 8;
            else {
                fila = fila + 8;
                columna = 0;
            }
        }
    }

    /**
     * <p>ReadHuffman makes reading part of the decompression</p>
     *
     * @param aux This is the image for decompress
     **/
    private void ReadHuffman(String aux) {

        Huffman DY = new Huffman();
        Huffman DCB = new Huffman();
        Huffman DCR = new Huffman();
        DY.setFrequencies(FreqY);
        DCB.setFrequencies(FreqCB);
        DCR.setFrequencies(FreqCR);

        StringBuilder encoding = new StringBuilder();
        int iteradorMatrix = aux.length() - sizeYc - sizeCBc - sizeCRc;
        for (int x = 0; x < sizeYc; ++x) {
            encoding.append(aux.charAt(iteradorMatrix));
            ++iteradorMatrix;
        }
        Ydes = DY.decompressHuffman(encoding.toString());
        encoding = new StringBuilder();
        for (int x = 0; x < sizeCBc; ++x) {
            encoding.append(aux.charAt(iteradorMatrix));
            ++iteradorMatrix;
        }
        CBdes = DCB.decompressHuffman(encoding.toString());
        encoding = new StringBuilder();
        for (int x = 0; x < sizeCRc; ++x) {
            encoding.append(aux.charAt(iteradorMatrix));
            ++iteradorMatrix;
        }
        CRdes = DCR.decompressHuffman(encoding.toString());
    }

    /**
     * <p>ReadFreq makes reading part of the decompression for the frequencies</p>
     *
     * @param imagenaux This is the image for decompress
     **/
    private void ReadFreq(char[] imagenaux) {
        if (imagenaux[29] == '/') iteradorFreq = 29;
        else iteradorFreq = 28;
        for (int x = 0; x < sizeY; ++x) {
            StringBuilder Key = new StringBuilder();
            StringBuilder Value = new StringBuilder();
            int n;
            int f;
            if (imagenaux[iteradorFreq] == '/') {
                ++iteradorFreq;
                while (imagenaux[iteradorFreq] != '/') {
                    Key.append(imagenaux[iteradorFreq]);
                    ++iteradorFreq;
                }
                ++iteradorFreq;
                while (imagenaux[iteradorFreq] != '/') {
                    Value.append(imagenaux[iteradorFreq]);
                    ++iteradorFreq;
                }
            }
            if (Key.length() > 9) {
                //negativo
                String auxi = Key.toString();
                auxi = Utils.andOfString(auxi);
                n = Integer.parseInt(auxi, 2);
                ++n;
                n = -1 * n;
            } else {
                n = Integer.parseInt(Key.toString(), 2);
            }
            f = Integer.parseInt(Value.toString(), 2);
            FreqY.put(n, f);
        }

        for (int x = 0; x < sizeCB; ++x) {
            StringBuilder Key = new StringBuilder();
            StringBuilder Value = new StringBuilder();
            int n;
            int f;
            if (imagenaux[iteradorFreq] == '/') {
                ++iteradorFreq;
                while (imagenaux[iteradorFreq] != '/') {
                    Key.append(imagenaux[iteradorFreq]);
                    ++iteradorFreq;
                }
                ++iteradorFreq;
                while (imagenaux[iteradorFreq] != '/') {
                    Value.append(imagenaux[iteradorFreq]);
                    ++iteradorFreq;
                }
            }
            if (Key.length() > 9) {
                //negativo
                String auxi = Key.toString();
                auxi = Utils.andOfString(auxi);
                n = Integer.parseInt(auxi, 2);
                ++n;
                n = -1 * n;
            } else {
                n = Integer.parseInt(Key.toString(), 2);
            }
            f = Integer.parseInt(Value.toString(), 2);
            FreqCB.put(n, f);
        }

        for (int x = 0; x < sizeCR; ++x) {
            StringBuilder Key = new StringBuilder();
            StringBuilder Value = new StringBuilder();
            int n;
            int f;
            if (imagenaux[iteradorFreq] == '/') {
                ++iteradorFreq;
                while (imagenaux[iteradorFreq] != '/') {
                    Key.append(imagenaux[iteradorFreq]);
                    ++iteradorFreq;
                }
                ++iteradorFreq;
                while (imagenaux[iteradorFreq] != '/') {
                    Value.append(imagenaux[iteradorFreq]);
                    ++iteradorFreq;
                }
            }
            if (Key.length() > 9) {
                //negativo
                String auxi = Key.toString();
                auxi = Utils.andOfString(auxi);
                n = Integer.parseInt(auxi, 2);
                ++n;
                n = -1 * n;
            } else {
                n = Integer.parseInt(Key.toString(), 2);
            }
            f = Integer.parseInt(Value.toString(), 2);
            FreqCR.put(n, f);
        }
    }

    /**
     * <p>ReadDecompression makes reading part of heading of the decompression</p>
     *
     * @param aux This is the image for decompress
     **/
    private void ReadDecompression(String aux) {
        StringBuilder calidadS = new StringBuilder();
        while (i < 8) {
            calidadS.append(aux.charAt(i));
            ++i;
        }
        quality = Integer.parseInt(calidadS.toString(), 2);
        StringBuilder widthS = new StringBuilder();
        while (i < 24) {
            widthS.append(aux.charAt(i));
            ++i;
        }
        width = Integer.parseInt(widthS.toString(), 2);
        StringBuilder heightS = new StringBuilder();
        while (i < 40) {
            heightS.append(aux.charAt(i));
            ++i;
        }
        height = Integer.parseInt(heightS.toString(), 2);
        StringBuilder sizeYS = new StringBuilder();
        while (i < 72) {
            sizeYS.append(aux.charAt(i));
            ++i;
        }
        sizeY = Integer.parseInt(sizeYS.toString(), 2);
        StringBuilder sizeCBS = new StringBuilder();
        while (i < 104) {
            sizeCBS.append(aux.charAt(i));
            ++i;
        }
        sizeCB = Integer.parseInt(sizeCBS.toString(), 2);
        StringBuilder sizeCRS = new StringBuilder();
        while (i < 136) {
            sizeCRS.append(aux.charAt(i));
            ++i;
        }
        sizeCR = Integer.parseInt(sizeCRS.toString(), 2);
        StringBuilder sizeYcS = new StringBuilder();
        while (i < 168) {
            sizeYcS.append(aux.charAt(i));
            ++i;
        }
        sizeYc = Integer.parseInt(sizeYcS.toString(), 2);
        StringBuilder sizeCBcS = new StringBuilder();
        while (i < 200) {
            sizeCBcS.append(aux.charAt(i));
            ++i;
        }
        sizeCBc = Integer.parseInt(sizeCBcS.toString(), 2);
        StringBuilder sizeCRcS = new StringBuilder();
        while (i < 232) {
            sizeCRcS.append(aux.charAt(i));
            ++i;
        }
        sizeCRc = Integer.parseInt(sizeCRcS.toString(), 2);
    }

    /**
     * <p>initMatrix make the inizialization of the matrix for the dct</p>
     *
     * @param c This is the matrix to inizialize
     * @return A matrix c with the inizialation made
     **/
    static private double[][] initMatrix(double[][] c) {
        final int N = c.length;
        final double value = 1 / Math.sqrt(2.0);

        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {
                c[i][j] = 1;
            }
        }

        for (int i = 0; i < N; i++) {
            c[i][0] = value;
            c[0][i] = value;
        }
        c[0][0] = 0.5;
        return c;
    }

    /**
     * <p>dct2 makes Discrete Cousine Transform for the compression part</p>
     *
     * @param input This is the image for compress part
     * @return A Matrix with the dct made
     **/
    static private double[][] dct2(double[][] input) {
        final int N = input.length;
        final double mathPI = Math.PI;
        final int halfN = N / 2;
        final double doubN = 2.0 * N;

        double[][] c = new double[N][N];
        c = initMatrix(c);

        double[][] output = new double[N][N];

        for (int u = 0; u < N; u++) {
            double temp_u = u * mathPI;
            for (int v = 0; v < N; v++) {
                double temp_v = v * mathPI;
                double sum = 0.0;
                for (int x = 0; x < N; x++) {
                    int temp_x = 2 * x + 1;
                    for (int y = 0; y < N; y++) {
                        sum += input[x][y] * Math.cos((temp_x / doubN) * temp_u) * Math.cos(((2 * y + 1) / doubN) * temp_v);
                    }
                }
                sum *= c[u][v] / halfN;
                output[u][v] = sum;
            }
        }
        return output;
    }

    /**
     * <p>RBGtoYCbCR make the transform of the two forms</p>
     *
     * @param RGB The colour to transform
     * @return An Array with the transform made
     **/
    static private int[] RGBtoYCbCr(int[] RGB) {
        int[] YCbCr = new int[3];

        float r = (float) RGB[0];
        float g = (float) RGB[1];
        float b = (float) RGB[2];

        float y = (float) (0 + (0.299 * r) + (0.587 * g) + (0.114 * b));
        float cb = (float) (128 - (0.168736 * r) - (0.331264 * g) + (0.5 * b));
        float cr = (float) (128 + (0.5 * r) - (0.418688 * g) - (0.081312 * b));


        YCbCr[0] = Math.round(y);
        YCbCr[1] = Math.round(cb);
        YCbCr[2] = Math.round(cr);


        return YCbCr;
    }

    /**
     * <p>dct3 makes Inverse of Discrete Cousine Transform for the decompression part</p>
     *
     * @param input This is the image for decompress part
     * @return A Matrix with the inversion of the dct made
     **/
    static private double[][] dct3(double[][] input) {
        final int N = input.length;
        final double mathPI = Math.PI;
        final int halfN = N / 2;
        final double doubN = 2.0 * N;

        double[][] c = new double[N][N];
        c = initMatrix(c);

        double[][] output = new double[N][N];


        for (int x = 0; x < N; x++) {
            int temp_x = 2 * x + 1;
            for (int y = 0; y < N; y++) {
                int temp_y = 2 * y + 1;
                double sum = 0.0;
                for (int u = 0; u < N; u++) {
                    double temp_u = u * mathPI;
                    for (int v = 0; v < N; v++) {
                        sum += c[u][v] * input[u][v] * Math.cos((temp_x / doubN) * temp_u) * Math.cos((temp_y / doubN) * v * mathPI);
                    }
                }
                sum /= halfN;
                output[x][y] = sum;
            }
        }
        return output;
    }

    /**
     * <p>YCbCRtoRBG make the transform of the two forms</p>
     *
     * @param YCbCr The colour to transform
     * @return An Array with the transform made
     **/
    static private int[] YCbCrtoRGB(int[] YCbCr) {
        int[] RGB = new int[3];

        float Y = (float) YCbCr[0];
        float CB = (float) YCbCr[1];
        float CR = (float) YCbCr[2];

        float R = (float) (Y + 1.402 * (CR - 128));
        float G = (float) (Y + -0.344136 * (CB - 128) + -0.714136 * (CR - 128));
        float B = (float) (Y + 1.772 * (CB - 128));

        if (R > 255) R = 255;
        if (G > 255) G = 255;
        if (B > 255) B = 255;

        RGB[0] = Math.round(R);
        RGB[1] = Math.round(G);
        RGB[2] = Math.round(B);

        return RGB;
    }

    /**
     * <p>makePPM makes a PPM for the view to be displayed</p>
     *
     * @param aux The image to make
     * @return A BufferedImage instance to display
     **/
    public static BufferedImage makePPM(byte[] aux) {
        int w = 0, h = 0;
        boolean fin = true;

        char[] iaux = new char[aux.length];
        for (int j = 0; j < aux.length; j++) {
            iaux[j] = (char) (aux[j] & 0xFF);
        }

        for (int i = 3; iaux[i] != '\n'; ++i) {
            if (iaux[i] == ' ') fin = false;
            else {
                if (fin) {
                    int a = Character.getNumericValue(iaux[i]);
                    w *= 10;
                    w += a;
                } else {
                    int a = Character.getNumericValue(iaux[i]);
                    h *= 10;
                    h += a;
                }
            }
        }

        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int r, g, b, k = 0, pixel;
        for (int y = 0; y < h; y++) {
            for (int x = 0; (x < w) && ((k + 3) < aux.length); x++) {
                r = aux[k++] & 0xFF;
                g = aux[k++] & 0xFF;
                b = aux[k++] & 0xFF;
                pixel = 0xFF000000 + (r << 16) + (g << 8) + b;
                image.setRGB(x, y, pixel);
            }
        }

        return image;
    }
}
