package domain;

import java.util.*;
import java.lang.Math;

/**
 * LZ78 ASCII compression algorithm
 *
 * @author Pol Monroig Company
 *
 */

public class LZ78 extends Algoritme {

    /** Sets the number of bits the a char uses,
     *  we use UTF-8 so CHAR_SIZE = 8
     * */

    private int original_size;
    private int compressed_size;
    private float compression_ratio;

    public int getOriginalSize(){
        return this.original_size;
    }

    public int getCompressedSize(){
        return this.compressed_size;
    }

    public float getCompression_ratio(){
        return this.compression_ratio;
    }

    /**
     * <p>The compression method makes a compression of a given text</>
     * @param texto the text to compress
     * @return the compressed text
     */
    @Override
    public byte[] comprimir(byte[] texto) {
        String text = new String(texto);
        SortedMap< String, Integer> dict = new TreeMap<>(); ;
        String current = "";
        Integer lastWordPos = 0;
        String coding = "";
        boolean inDict = false;
        char phrase = ' ';
        for(int i = 0; i < text.length(); ++i){
            phrase = text.charAt(i);
            current += phrase;
            inDict = false;
            if(dict.containsKey(current)){ // if phrase in dict
                lastWordPos = dict.get(current);
                inDict = true;
            }
            else{ // else new phrase
                coding += Integer.toString(lastWordPos)+ ","  + phrase ;
                dict.put(current, dict.size() + 1);
                lastWordPos = 0;
                current = "";
            }
        }
        if(inDict)coding += Integer.toString(lastWordPos);
        String binary_string = toBinaryString(coding);
        String decoded = toString(toByteArray(binary_string));
        if(decoded.equals(binary_string)){
            System.out.println("EQUAL == TRUE");
        }
        this.original_size = text.length();
        this.compressed_size = (int)Math.ceil(binary_string.length() / 8.0);
        this.compression_ratio = ((float)compressed_size / (float)text.length()) * 100;
        return toByteArray(binary_string);
    }

    @Override
    public byte[] descomprimir(byte[] texto) {
        String text = toString(texto);
        String coding = "";
        double log_2 = Math.log(2.0);
        int current_index = 0;
        ArrayList<String> phrases = new ArrayList<>();
        int current_index_size = 1;
        String comparison_coding = "";
        for(int i = 0; i < text.length(); ++i){
            if(current_index >= 2){
                double aux = (Math.log(current_index) / log_2);
                if(aux == Math.round(aux)){
                    current_index_size += 1;
                }
            }
            String index_string = Character.toString(text.charAt(i));
            i++;
            while(index_string.length() < current_index_size){

                index_string += Character.toString(text.charAt(i));
                ++i;
            }
            if(current_index == 256){
                int y = 0;
            }
            int index = Integer.parseInt(index_string, 2);
            comparison_coding += Integer.toString(index)+ ",";
            String word = "";
            if(i != text.length()){
                word = LZ78.getLetter(text, i);
                comparison_coding += word;
            }
            i += 7;
            if(index == 0){
                phrases.add(word);
            }
            else{
                word = phrases.get(index - 1) + word;
                phrases.add(word);
            }
            coding += word;
            current_index++;
        }

        return coding.getBytes();
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
        String result = "";
        for(int i = s.length() - 1; i >= s.length() - binary_size && i >= 0; --i){
            result = s.charAt(i) + result;
        }
        return result;
    }

    /**
     * <p>Transforms a given LZ78 compression into a binary string</>
     * @param coding text to transform into binary
     * @return the encoding as a binary text
     */
    private String toBinaryString(String coding){
        String binary_string = "";
        int current_index_size = 1;
        double log_2 = Math.log(2);
        int current_index = 0;

        for(int i = 0; i < coding.length(); i++){
            if(current_index == 257){
                int y = 0;
            }
            String index = "";
            while(i < coding.length() && coding.charAt(i) != ','){
                index += coding.charAt(i);
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
            binary_string += addZeros(intToString(Integer.parseInt(index), binary_size), current_index_size); // PROBLEMA <<<<<
            if(i  < coding.length()){
                char value = coding.charAt(i);
                binary_string += addZeros(intToString(value, 8), 8);
            }
            current_index++;
        }
        return binary_string;
    }

    /**
     * <p>Adds a specified number of 0s at the left side of a string</>
     * @param binary binary string
     * @param n_zeros size of the final string
     * @return returns a binary string where its size >= n_zeros
     */
    static public String addZeros(String binary, int n_zeros){ // CORRECTA
        while(binary.length() < n_zeros){
            binary = "0" + binary;
        }
        return binary;
    }


    static public int getIntFromString(String text, int i){
        String letter = "";
        for(int j = i; j < i + 8 && j < text.length(); ++j){
            letter += Character.toString(text.charAt(j));
        }
        return Integer.parseInt(letter, 2);
    }


    static private String getLetter(String text,  int i){
        return Character.toString((char) getIntFromString(text, i));
    }

    static  private String toString(byte[] byte_coding){ // CORRECTA
        String binary_string = "";
        int zeros_offset = byte_coding[0];
        for(int i = 1; i < byte_coding.length - 1; ++i){
            binary_string += addZeros(intToString(byte_coding[i], 8), 8);
        }
        if(zeros_offset == 0){
            zeros_offset = 8;
        }
        binary_string += addZeros(intToString(byte_coding[byte_coding.length - 1], 8), zeros_offset) ;
        return binary_string;
    }





}
