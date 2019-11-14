package domain;

import java.lang.Math;
import java.util.ArrayList;

/*

 @author Adrián Álvarez
 JPEG COMPRESSION ALGORTIHM

 */

public class JPEG extends Algoritme{

    private int calidad = 0;

    public void setCalidad(int calidad){
        this.calidad = calidad;
    }

    private static double [][][] QtablesLuminance = {
        {
            {32,33,51,81,66,39,34,17},{33,36,48,47,28,23,12,12},{51,48,47,28,23,12,12,12},{81,47,28,23,12,12,12,12},{66,28,23,12,12,12,12,12},{39,23,12,12,12,12,12,12},{34,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},
        },
        {
            {27,26,41,65,66,39,34,17},{26,29,38,47,28,23,12,12},{41,38,47,28,23,12,12,12},{65,47,28,23,12,12,12,12},{66,28,23,12,12,12,12,12},{39,23,12,12,12,12,12,12},{34,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},
        },
        {
            {20,17,26,41,51,39,34,17},{17,18,24,39,28,23,12,12},{26,24,32,28,23,12,12,12},{41,39,28,23,12,12,12,12},{51,28,23,12,12,12,12,12},{39,23,12,12,12,12,12,12},{34,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},
        },
        {
            {18,14,22,35,44,39,34,17},{14,16,21,34,28,23,12,12},{22,21,27,28,23,12,12,12},{35,34,28,23,12,12,12,12},{44,28,23,12,12,12,12,12},{39,23,12,12,12,12,12,12},{34,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},
        },
        {
            {16,11,17,27,34,39,34,17},{11,12,16,26,28,23,12,12},{17,16,21,28,23,12,12,12},{27,26,28,23,12,12,12,12},{34,28,23,12,12,12,12,12},{39,23,12,12,12,12,12,12},{34,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},
        },
        {
            {12,8,13,21,26,32,34,17},{8,9,12,20,27,23,12,12},{13,12,16,26,23,12,12,12},{21,20,26,23,12,12,12,12},{26,27,23,12,12,12,12,12},{32,23,12,12,12,12,12,12},{34,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},
        },
        {
            {8,6,9,14,17,21,28,17},{6,6,8,13,18,23,12,12},{9,8,11,17,23,12,12,12},{14,13,17,23,12,12,12,12},{17,18,23,12,12,12,12,12},{21,23,12,12,12,12,12,12},{28,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},
        },
        {
            {10,7,11,18,22,27,34,17},{7,8,10,17,23,23,12,12},{11,10,14,22,23,12,12,12},{18,17,22,23,12,12,12,12},{22,23,23,12,12,12,12,12},{27,23,12,12,12,12,12,12},{34,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},
        },
        {
            {6,4,7,11,14,17,22,17},{4,5,6,10,14,19,12,12},{7,6,8,14,19,12,12,12},{11,10,14,19,12,12,12,12},{14,14,19,12,12,12,12,12},{17,19,12,12,12,12,12,12},{22,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},
        },
        {
            {4,3,4,7,9,11,14,17},{3,3,4,7,9,12,12,12},{4,4,5,9,12,12,12,12},{7,7,9,12,12,12,12,12},{9,9,12,12,12,12,12,12},{11,12,12,12,12,12,12,12},{14,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},
        },
        {
            {2,2,3,4,5,6,8,11},{2,2,2,4,5,7,9,11},{3,2,3,5,7,9,11,12},{4,4,5,7,9,11,12,12},{5,5,7,9,11,12,12,12},{6,7,9,11,12,12,12,12},{8,9,11,12,12,12,12,12},{11,11,12,12,12,12,12,12},
        },
        {
            {1,1,1,2,3,3,4,5},{1,1,1,2,3,4,4,6},{1,1,2,3,4,4,5,7},{2,2,3,4,4,5,7,8},{3,3,4,4,5,7,8,8},{3,4,4,5,7,8,8,8},{4,4,5,7,8,8,8,8},{5,6,7,8,8,8,8,8},
        },
        {
            {1,1,1,1,1,1,1,2},{1,1,1,1,1,1,1,2},{1,1,1,1,1,1,2,2},{1,1,1,1,1,2,2,3},{1,1,1,1,2,2,3,3},{1,1,1,2,2,3,3,3},{1,1,2,2,3,3,3,3},{2,2,2,3,3,3,3,3},
        }
    };

    private static double [][][] QtablesChrominance = {
        {
            {34,51,52,34,20,20,17,17},{51,38,24,14,14,12,12,12},{52,24,14,14,12,12,12,12},{34,14,14,12,12,12,12,12},{20,14,12,12,12,12,12,12},{20,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},
        },
        {
            {29,41,52,34,20,20,17,17},{41,38,24,14,14,12,12,12},{52,24,14,14,12,12,12,12},{34,14,14,12,12,12,12,12},{20,14,12,12,12,12,12,12},{20,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},
        },
        {
            {21,26,33,34,20,20,17,17},{26,29,24,14,14,12,12,12},{33,24,14,14,12,12,12,12},{34,14,14,12,12,12,12,12},{20,14,12,12,12,12,12,12},{20,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},
        },
        {
            {20,22,29,34,20,20,17,17},{22,25,24,14,14,12,12,12},{29,24,14,14,12,12,12,12},{34,14,14,12,12,12,12,12},{20,14,12,12,12,12,12,12},{20,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},
        },
        {
            {17,17,22,34,20,20,17,17},{17,19,22,14,14,12,12,12},{22,22,14,14,12,12,12,12},{34,14,14,12,12,12,12,12},{20,14,12,12,12,12,12,12},{20,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},
        },
        {
            {13,13,17,27,20,20,17,17},{13,14,17,14,14,12,12,12},{17,17,14,14,12,12,12,12},{27,14,14,12,12,12,12,12},{20,14,12,12,12,12,12,12},{20,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},
        },
        {
            {9,9,11,18,20,20,17,17},{9,10,11,14,14,12,12,12},{11,11,14,14,12,12,12,12},{18,14,14,12,12,12,12,12},{20,14,12,12,12,12,12,12},{20,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},
        },
        {
            {11,14,31,34,20,20,17,17},{14,19,24,14,14,12,12,12},{31,24,14,14,12,12,12,12},{34,14,14,12,12,12,12,12},{20,14,12,12,12,12,12,12},{20,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},
        },
        {
            {7,9,19,34,20,20,17,17},{9,12,19,14,14,12,12,12},{19,19,14,14,12,12,12,12},{34,14,14,12,12,12,12,12},{20,14,12,12,12,12,12,12},{20,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},
        },
        {
            {4,6,12,22,20,20,17,17},{6,8,12,14,14,12,12,12},{12,12,14,14,12,12,12,12},{22,14,14,12,12,12,12,12},{20,14,12,12,12,12,12,12},{20,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},{17,12,12,12,12,12,12,12},
        },
        {
            {3,3,7,13,15,15,15,15},{3,4,7,13,14,12,12,12},{7,7,13,14,12,12,12,12},{13,13,14,12,12,12,12,12},{15,14,12,12,12,12,12,12},{15,12,12,12,12,12,12,12},{15,12,12,12,12,12,12,12},{15,12,12,12,12,12,12,12},
        },
        {
            {1,2,4,7,8,8,8,8},{2,2,4,7,8,8,8,8},{4,4,7,8,8,8,8,8},{7,7,8,8,8,8,8,8},{8,8,8,8,8,8,8,8},{8,8,8,8,8,8,8,8},{8,8,8,8,8,8,8,8},{8,8,8,8,8,8,8,8},
        },
        {
            {1,1,1,2,3,3,3,3},{1,1,1,2,3,3,3,3},{1,1,2,3,3,3,3,3},{2,2,3,3,3,3,3,3},{3,3,3,3,3,3,3,3},{3,3,3,3,3,3,3,3},{3,3,3,3,3,3,3,3},{3,3,3,3,3,3,3,3},
        }
    };
    
    @Override
    public byte[] descomprimir(byte [] imagen){

        /*
        Declaraciones de variables
         */

        int width = 0;
        int height = 0;
        int calidad = 0;
        int iterator = 0;
        String Yen = new String();
        String Cben = new String();
        String Cren = new String();

        StringBuilder imageBYTES = new StringBuilder(imagen.length * Byte.SIZE);
        for( int i = 0; i < Byte.SIZE * imagen.length; i++ ) imageBYTES.append((imagen[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        String aux = imageBYTES.toString();
        int i = 0;
        StringBuilder calidadS = new StringBuilder();
        while(i<8){
            calidadS.append(aux.charAt(i));
            ++i;
        }
        calidad = Integer.parseInt(calidadS.toString(), 2);
        StringBuilder widthS = new StringBuilder();
        while(i<24){
            widthS.append(aux.charAt(i));
            ++i;
        }
        width = Integer.parseInt(widthS.toString(), 2);
        StringBuilder heightS = new StringBuilder();
        while(i<40){
            heightS.append(aux.charAt(i));
            ++i;
        }
        height = Integer.parseInt(heightS.toString(), 2);

        Huffman descomprimir = new Huffman();
        descomprimir.InizialitingHashtable();

        int iteradorY = 0;
        while (0 < width/8*height/8){
            ArrayList<Integer> Y = descomprimir.desHuffman(aux);
            int u = 1;
            int k = 1;

            for (int element = 0; element < 64; ++element) {
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

            ++iteradorY;
        }
        return null;
    }

    @Override
    public byte[] comprimir (byte [] imagen){
        long startTime = System.nanoTime(); // empezar contador de tiempo
        /*
        Declaraciones de variables
         */
        int width = 0;
        int height = 0;
        int color = 0;
        boolean anchura = true;
        int iterator = 0;
        StringBuilder Yen = new StringBuilder();
        StringBuilder Cben = new StringBuilder();
        StringBuilder Cren = new StringBuilder();


        char [] imagenaux = new char[imagen.length];
        for (int j = 0; j < imagen.length; j++) {
            imagenaux[j] = (char) (imagen[j] & 0xFF);
        }
            /*
            Conseguimos la altura y anchura de la imagen a codificar
            */
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

            /*
            Conseguimos el color maximo de la imagen a codificar
            */

        ++iterator;
        while(imagenaux[iterator] != '\n'){
            int aux = Character.getNumericValue(imagenaux[iterator]);
            color *= 10;
            color += aux;
            ++iterator;
        }
        ++iterator;

            /*
            Cambiamos el color RGB original de la imagen a YCbCr
            */

       int [][][] imagenYCbCr = new int [width][height][3];
       for (int i = 0; i < width; ++i){
           for (int j = 0; j < height; ++j){
                int [] RGB = {(int)imagenaux[iterator],(int)imagenaux[iterator+1],(int)imagenaux[iterator+2]};
                imagenYCbCr[i][j] = RGBtoYCbCr(RGB);
                iterator+=3;
           }
       }

        double [][] Ydct  = new double[8][8];
        double [][] Cbdct = new double[8][8];
        double [][] Crdct = new double[8][8];

        /*
        Tenemos que coger una matriz de 8x8 que sera nuestro pixel a comprimir. Si la matriz se sale de los parametros
        altura y anchura, haremos padding con 0, para que sea negro.
         */


        for (int i = 0; i < width; i+=8) {
            for (int j = 0; j < height; j += 8) {
                for (int x = 0; x < 8; ++x) {
                    for (int y = 0; y < 8; ++y) {
                        if (i + x >= width || j + y >= height) {
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
                /*double[][] test = {{16,  11,  10,  16,  24,  40,  51,  61},
                {12,  12,  14,  19,  26,  58,  60,  55},
                {14,  13,  16,  24,  40,  57,  69,  56},
                    {14,  17,  22,  29,  51,  87,  80,  62},
                    {18,  22,  37,  56,  68, 109, 103,  77},
                    {24,  35,  55,  64,  81, 104, 113,  92},
                    {49,  64,  78,  87, 103, 121, 120, 101},
                    {72,  92,  95,  98, 112, 100, 103,  99} };

                test = dct2(test);*/

                Ydct = dct2(Ydct);
                Cbdct = dct2(Cbdct);
                Crdct = dct2(Crdct);

            /*
            Quantizamos los DCT de cada componente del color
             */

                for (int v = 0; v < 8; ++v) {
                    for (int h = 0; h < 8; ++h) {
                        Ydct[v][h] = Math.round(Ydct[v][h] / QtablesLuminance[calidad][v][h]);
                        Cbdct[v][h] = Math.round(Cbdct[v][h] / QtablesChrominance[calidad][v][h]);
                        Crdct[v][h] = Math.round(Crdct[v][h] / QtablesChrominance[calidad][v][h]);
                        //test[v][h] = test[v][h] / QtablesLuminance[calidad][v][h];

                    }
                }


            /*
            Hacemos el encoding con Huffman HAY QUE RECORRER EN ZIGZAG CADA MATRIZ
             */

            ArrayList<Integer> Yencoding = new ArrayList<Integer>();
            ArrayList<Integer> Cbencoding = new ArrayList<Integer>();
            ArrayList<Integer> Crencoding = new ArrayList<Integer>();

                Huffman comprimirY = new Huffman();
                Huffman comprimirCB = new Huffman();
                Huffman comprimirCR = new Huffman();

                int u = 1;
                int k = 1;
                int contador = 0;
                for (int element = 0; element < 64; ++element, ++contador) {
                    Yencoding.add((int)Ydct[u - 1][k - 1]);
                    Cbencoding.add((int)Cbdct[u - 1][k - 1]);
                    Crencoding.add((int)Crdct[u - 1][k - 1]);
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

                Yen.append(comprimirY.encode(Yencoding));
                Cben.append(comprimirCB.encode(Cbencoding));
                Cren.append(comprimirCR.encode(Crencoding));

            }
        }

            String calidadE =  Integer.toBinaryString(calidad);
            String widthE =  Integer.toBinaryString(width);
            String heightE =  Integer.toBinaryString(height);
            while(calidadE.length() < 8) calidadE = "0" + calidadE;
            while(widthE.length() < 16) widthE = "0" + widthE;
            while(heightE.length() < 16) heightE = "0" + heightE;
            String result = calidadE + widthE + heightE + Yen.toString() + Cben.toString() + Cren.toString();
            byte [] c = Utils.toByteArray2(result);





        /*

          SOI	FFD8	Start Of Image
          1111111111011000
          DQT	FFDB	One or more quantization tables DQT
          1111111111011011
          SOF	FFC0	Start Of Frame
          1111111111000000
          DHT	FFC4	One or more huffman tables DHT
          1111111111000100
          SOS	FFDA	Start Of Scan -1, -38
          1111111111011010
          EOI	FFD9	End of Image
          1111111111011001

          */


        /*
        Estadisticas locales de la compression
         */

        int original_size = imagen.length;
        int compressed_size = (int)Math.ceil(c.length);
        float compression_ratio = ((float)imagen.length /(float)compressed_size)*100;



        // Calculo estadisticas
        long endTime = System.nanoTime();
        this.estadisticaLocal.setMidaArxiuInicial(imagen.length);
        this.estadisticaLocal.setMidaArxiuFinal(compressed_size);
        this.estadisticaLocal.setGrauCompresio(compression_ratio);
        this.estadisticaLocal.setTiempoCompresio((float)((endTime - startTime) / 1000000.0)); // miliseconds
        this.estadisticaLocal.setVelocitatCompresio(imagen.length / this.estadisticaLocal.getTiempoCompresio());


        return c;
    }

    static private double[][] initMatrix(double [][] c) {
        final int N = c.length;
        final double value = 1/Math.sqrt(2.0);

        for (int i=1; i<N; i++)
        {
            for (int j=1; j<N; j++)
            {
                c[i][j]=1;
            }
        }

        for (int i=0; i<N; i++)
        {
            c[i][0] = value;
            c[0][i] = value;
        }
        c[0][0] = 0.5;
        return c;
    }

    static private double[][] dct2(double[][] input) {
        final int N = input.length;
        final double mathPI = Math.PI;
        final int halfN = N/2;
        final double doubN = 2.0*N;

        double[][] c = new double[N][N];
        c = initMatrix(c);

        double[][] output = new double[N][N];

        for (int u=0; u<N; u++)
        {
            double temp_u = u*mathPI;
            for (int v=0; v<N; v++)
            {
                double temp_v = v*mathPI;
                double sum = 0.0;
                for (int x=0; x<N; x++)
                {
                    int temp_x = 2*x+1;
                    for (int y=0; y<N; y++)
                    {
                        sum += input[x][y] * Math.cos((temp_x/doubN)*temp_u) * Math.cos(((2*y+1)/doubN)*temp_v);
                    }
                }
                sum *= c[u][v]/ halfN;
                output[u][v] = sum;
            }
        }
        return output;
    }

    static private int [] RGBtoYCbCr(int [] RGB){
        int [] YCbCr = new int [3];

        float r = (float)RGB[0];
        float g = (float)RGB[1];
        float b = (float)RGB[2];

        float y = (float)(16 + (65.738 * r)/256 + (129.057 * g)/256 + (25.064 * b)/256);
        float cb = (float)(128 - (37.945 * r)/256 - (74.494 * g)/256 + (112.439 * b)/256);
        float cr = (float)(128 + (112.439 * r)/256 - (94.154 * g)/256 - (18.285 * b)/256);

        YCbCr[0] = Math.round(y);
        YCbCr[1] = Math.round(cb);
        YCbCr[2] = Math.round(cr);

        return YCbCr;
    }

}
