package domain;
import java.util.*;

public class LZW {
    public byte[] comprimir(byte[] text) {
        System.out.println("bytes de l'arxiu " + text.length);
        SortedMap< String, Integer> dict = new TreeMap<>(); //map per guardar el diccionari
        String cadenachars = new String(text); // convertir l'array de bytes en un String
        List<Integer> res = new ArrayList<Integer>();
        String s = "";


        int dictSize = 256;
        for (int i = 0; i < 256; i++) {
            dict.put("" + (char)i, i);
        }

        String w = "";
        String k = "";
        String wk = "";

        for (int i = 0; i < cadenachars.length(); ++i) {
            k = String.valueOf(cadenachars.charAt(i));
            wk = w+k;
            if (dict.containsKey(wk)) {
                w = wk;
            }
            else {
                //System.out.println("sortida: " + dict.get(w));
                res.add(dict.get(w));
                s += to12bit(dict.get(w));

                if (dictSize < 4096) {
                    dict.put(wk,dictSize);
                    dictSize++;
                }
                w = k;
            }
        }
        res.add(dict.get(w));
        s += to12bit(dict.get(w));


        s = round2byte(s);


        int retsize = s.length()/8;

        byte[] ret = toByteArray(s);

        //System.out.println(retsize);
        for (int i = 0; i < retsize; i++ ){
            int j = i*8;
            String e = "" + s.charAt(j) + s.charAt(i*8+1) + s.charAt(j+2) + s.charAt(j+3) + s.charAt(j+4) + s.charAt(j+5) + s.charAt(j+6) + s.charAt(j+7);
            //System.out.println(e + " " + ret[i] + " " );

        }

        return ret;
    }


    /** Convert 8 bit to 12 bit */
    public String to12bit(int i) {
        String temp = Integer.toBinaryString(i);
        while (temp.length() < 12) {
            temp = "0" + temp;
        }
        return temp;
    }

    public String to8bit(int i) {
        String temp = Integer.toBinaryString(i);
        while (temp.length() < 8) {
            temp = "0" + temp;
        }
        return temp;
    }

    public String round2byte(String s) {
        while (s.length()%8 != 0) {
            s = s + "0";
        }
        return s;
    }

    private static byte[] toByteArray(String binary_string){
        int size = (int)Math.ceil(binary_string.length() / 8.0) + 1; // number of bytes + offset byte
        byte [] byte_coding = new byte[size];
        int it = 0;
        byte_coding[0] = (byte)(binary_string.length() % 8); // offset of 0
        for(int i = 1; i < size; ++i){
            byte_coding[i] = (byte)getIntFromString(binary_string, it);
            it += 8;
        }

        return  byte_coding;
    }

    public static String intToString(int b, int binary_size){
        String s = Integer.toBinaryString(b);
        StringBuilder result = new StringBuilder();
        for(int i = s.length() - 1; i >= s.length() - binary_size && i >= 0; --i){
            result.insert(0, s.charAt(i));
        }
        return result.toString();
    }

    /**
     * <p>Transforms a given LZ78 compression into a binary string</>
     * @param coding text to transform into binary
     * @return the encoding as a binary text
     */
    private String toBinaryString(String coding){
        StringBuilder binary_string = new StringBuilder();
        int current_index_size = 1;
        double log_2 = Math.log(2);
        int current_index = 0;

        for(int i = 0; i < coding.length(); i++){
            StringBuilder index = new StringBuilder();
            while(i < coding.length() && coding.charAt(i) != ','){
                index.append(coding.charAt(i));
                ++i;
            }
            ++i;
            if(current_index >= 2){
                double aux = (Math.log(current_index) / log_2);
                if(aux == Math.round(aux)){
                    current_index_size += 1;
                }
            }
            int binary_size = 8;
            if(current_index_size > 8){
                binary_size = current_index_size;
            }
            binary_string.append(addZeros(intToString(Integer.parseInt(index.toString()), binary_size), current_index_size)); // PROBLEMA <<<<<
            if(i  < coding.length()){
                char value = coding.charAt(i);
                binary_string.append(addZeros(intToString(value, 8), 8));
            }
            current_index++;
        }
        return binary_string.toString();
    }

    /**
     * <p>Adds a specified number of 0s at the left side of a string</>
     * @param binary binary string
     * @param n_zeros size of the final string
     * @return returns a binary string where its size >= n_zeros
     */
    static public String addZeros(String binary, int n_zeros){ // CORRECTA
        StringBuilder binaryBuilder = new StringBuilder(binary);
        while(binaryBuilder.length() < n_zeros){
            binaryBuilder.insert(0, "0");
        }
        binary = binaryBuilder.toString();
        return binary;
    }


    static public int getIntFromString(String text, int i){
        StringBuilder letter = new StringBuilder();
        for(int j = i; j < i + 8 && j < text.length(); ++j){
            letter.append(text.charAt(j));
        }
        return Integer.parseInt(letter.toString(), 2);
    }


    static private String getLetter(String text,  int i){
        return Character.toString((char) getIntFromString(text, i));
    }

    static  private String toString(byte[] byte_coding){ // CORRECTA
        StringBuilder binary_string = new StringBuilder();
        int zeros_offset = byte_coding[0];
        for(int i = 1; i < byte_coding.length - 1; ++i){
            binary_string.append(addZeros(intToString(byte_coding[i], 8), 8));
        }
        if(zeros_offset == 0){
            zeros_offset = 8;
        }
        binary_string.append(addZeros(intToString(byte_coding[byte_coding.length - 1], 8), zeros_offset));
        return binary_string.toString();
    }



    public byte[] descomprimir(byte[] text) {


        String s = toString(text);
        List<Integer> l = new ArrayList<Integer>();

        //System.out.println(s.length());

        for (int i = 0; i < s.length()-4; i = i + 12) {
            String e = "" + s.charAt(i) + s.charAt(i+1) + s.charAt(i+2) + s.charAt(i+3) + s.charAt(i+4) + s.charAt(i+5) + s.charAt(i+6) + s.charAt(i+7) + s.charAt(i+8) + s.charAt(i+9) + s.charAt(i+10) + s.charAt(i+11) ;l.add(Integer.parseInt(e, 2));

        }

        SortedMap< Integer, String> dict = new TreeMap<>();

        String res = "";


        int dictSize = 256;
        for (int i = 0; i < 256; i++) {
            dict.put(i, "" + (char)i);
        }

        int cod_nou;
        String cadena;
        int cod_vell = l.get(0);
        String caracter = dict.get(cod_vell);
        res += caracter;

        for (int i = 1; i < l.size(); ++i) {
            cod_nou = l.get(i);
            if (!dict.containsKey(cod_nou)) {
                cadena = dict.get(cod_vell) + caracter;
            }
            else {
                cadena = dict.get(cod_nou);
            }
            res += cadena;
            caracter = String.valueOf(cadena.charAt(0));
            String dic_inp = dict.get(cod_vell)+caracter;
            dict.put(dictSize, dic_inp);
            ++dictSize;
            cod_vell = cod_nou;



        }

        byte[] ret = res.toString().getBytes();

        return ret;
    }


}


