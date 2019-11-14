package domain;

import java.util.ArrayList;
import java.util.Hashtable;

/*
DIT donde empieza las YCBCR y todo para leer
 */


public class Huffman {

    int Dit = 41;

    private final String[] huffmanDC = {"00", "010", "011", "100", "101", "110", "1110", "11110", "111110", "1111110", "11111110", "111111110"};

    private final String[][] huffmanAC =    {
            {"00", "01", "100", "1011", "11010", "1111000", "11111000", "1111110110", "1111111110000010", "1111111110000011"},
            {"1100", "11011", "1111001", "111110110", "11111110110", "1111111110000100", "1111111110000101", "1111111110000110","1111111110000111", "1111111110001000"},
            {"11100", "11111001", "1111110111", "111111110100", "1111111110001001", "1111111110001010", "1111111110001011", "1111111110001100", "1111111110001101", "1111111110001110"},
            {"111010", "111110111", "111111110101", "1111111110001111", "1111111110010000", "1111111110010001", "1111111110010010", "1111111110010011", "1111111110010100", "1111111110010101"},
            {"111011", "1111111000", "1111111110010110", "1111111110010111", "1111111110011000", "1111111110011001", "1111111110011010", "1111111110011011", "1111111110011100", "1111111110011101"},
            {"1111010", "11111110111", "1111111110011110", "1111111110011111", "1111111110100000", "1111111110100001", "1111111110100010", "1111111110100011", "1111111110100100", "1111111110100101"},
            {"1111011", "111111110110", "1111111110100110", "1111111110100111", "1111111110101000", "1111111110101001", "1111111110101010", "1111111110101011", "1111111110101100", "1111111110101101"},
            {"11111010", "111111110111", "1111111110101110", "1111111110101111", "1111111110110000", "1111111110110001", "1111111110110010", "1111111110110011", "1111111110110100", "1111111110110101"},
            {"111111000", "111111111000000", "1111111110110110", "1111111110110111", "1111111110111000", "1111111110111001", "1111111110111010", "1111111110111011", "1111111110111100", "1111111110111101"},
            {"111111001", "1111111110111110", "1111111110111111", "1111111111000000", "1111111111000001", "1111111111000010", "1111111111000011", "1111111111000100", "1111111111000101", "1111111111000110"},
            {"111111010", "1111111111000111", "1111111111001000", "1111111111001001", "1111111111001010", "1111111111001011", "1111111111001100", "1111111111001101", "1111111111001110", "1111111111001111"},
            {"1111111001", "1111111111010000", "1111111111010001", "1111111111010010", "1111111111010011", "1111111111010100", "1111111111010101", "1111111111010110", "1111111111010111", "1111111111011000"},
            {"1111111010", "1111111111011001", "1111111111011010", "1111111111011011", "1111111111011100", "1111111111011101", "1111111111011110", "1111111111011111", "1111111111100000", "1111111111100001"},
            {"11111111000", "1111111111100010", "1111111111100011", "1111111111100100", "1111111111100101", "1111111111100110", "1111111111100111", "1111111111101000", "1111111111101001", "1111111111101010"},
            {"1111111111101011", "1111111111101100", "1111111111101101", "1111111111101110", "1111111111101111", "1111111111110000", "1111111111110001", "1111111111110010", "1111111111110011", "1111111111110100"},
            {"1111111111110101", "1111111111110110", "1111111111110111", "1111111111111000", "1111111111111001", "1111111111111010", "1111111111111011", "1111111111111100", "1111111111111101", "1111111111111110"}
    };
    private final String EOB = "1010";
    private final String ZRL = "11111111001";



    private Hashtable<String, String> decodeHuffDC;
    private Hashtable<String, String> decodeHuffAC;

    public String encode(ArrayList<Integer> sequencia) {
        int zeros = 0;
        String binari = "";
        if(sequencia.get(0) < 0){
            int aux = -1*sequencia.get(0);
            String aux1 = Integer.toBinaryString(aux);
            binari = Utils.andOfString(aux1);
        }
        else binari = Integer.toBinaryString(sequencia.get(0));
        String codi = huffmanDC[binari.length()];
        String result = codi + binari;
        for (int i = 1; i < sequencia.size(); i++) {
            if (sequencia.get(i) == 0) zeros++;
            else {
                while (zeros >= 16) {
                    result = result + ZRL;
                    zeros = zeros -16;
                }
                if(sequencia.get(i) < 0){
                    int aux = -1*sequencia.get(i);
                    String aux1 = Integer.toBinaryString(aux);
                    binari = Utils.andOfString(aux1);
                }
                else binari = Integer.toBinaryString(sequencia.get(i));
                codi = huffmanAC[zeros][binari.length() - 1];
                result = result + codi + binari;
                zeros = 0;
            }
        }
        return result + EOB;
    }

    public void InizialitingHashtable() {
        //DC
        decodeHuffDC = new Hashtable<String, String>();
        decodeHuffAC = new Hashtable<String, String>();
        for (int i = 0; i < huffmanDC.length; i++) {
            decodeHuffDC.put(huffmanDC[i], String.valueOf(i));
        }
        //AC
        decodeHuffAC.put("1010", "EOB");
        decodeHuffAC.put("11111111001", "ZRL");
        for (int i = 0; i < huffmanAC.length; i++) {
            for (int j = 0; j < huffmanAC[0].length; j++) {
                String zeros = String.valueOf(i);
                String tam = String.valueOf(j);
                decodeHuffAC.put(huffmanAC[i][j], zeros + tam);
            }
        }
    }

    public ArrayList<Integer> desHuffman(String input) {
        int tam, zeros;
        String codi, par, bin = "";
        ArrayList<Integer> result = new ArrayList<Integer>();
        par = Character.toString(input.charAt(Dit));
        //System.out.println(par);
        codi = decodeHuffDC.get(par);
        while (codi == null) {
            Dit++;
            par = par + Character.toString(input.charAt(Dit));
            //System.out.println(par);
            codi = decodeHuffDC.get(par);
        }
        int size = Integer.parseInt(codi);
        int count = 1;
        while (count <= size) {
            bin = bin + Character.toString(input.charAt(Dit + count));
            count++;
        }
        Dit = Dit + size + 1;
        if (bin.charAt(0) == '0') {
            bin = Utils.andOfString(bin);
            result.add((-1)*Integer.parseInt(bin,2));
        }
        else result.add(Integer.parseInt(bin,2));
        Boolean acabat = false;
        while(Dit < input.length() && acabat == false) {
            //System.out.println(k);
            //imprimirLlista(result);
            par = Character.toString(input.charAt(Dit));
            //System.out.println(par);
            codi = decodeHuffAC.get(par);
            while (codi == null) {
                Dit++;
                par = par + Character.toString(input.charAt(Dit));
                //System.out.println(par);
                codi = decodeHuffAC.get(par);
            }
        /*System.out.print("Par: ");
        System.out.println(par);
        System.out.print("Codi: ");
        System.out.println(codi);*/

            if (codi == "EOB") {
                while(result.size() < 64)  result.add(0);
                acabat = true;
            }
            else if (codi == "ZRL") {
                for (int i = 0; i < 16; i++) result.add(0);
            }
            else {
                bin = "";
                zeros = Integer.parseInt(codi.substring(0, codi.length() - 1));
                tam = Integer.parseInt(codi.substring(codi.length() - 1)) + 1;
                for (int i = 0; i < zeros; i++) result.add(0);
                count = 1;
                while (count <= tam) {
                    bin = bin + Character.toString(input.charAt(Dit + count));
                    count++;
                }
                if (bin.charAt(0) == '0') {
                    bin = Utils.andOfString(bin);
                    result.add((-1)*Integer.parseInt(bin,2));
                }
                else result.add(Integer.parseInt(bin,2));
            /*System.out.print("Zeros: ");
            System.out.println(zeros);
            System.out.print("Tamany: ");
            System.out.println(tam);*/
                Dit = Dit + tam;
            }
            Dit++;
        }
        ++Dit;
        return result;
    }
}
