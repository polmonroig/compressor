package domain;

import java.nio.charset.StandardCharsets;
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
        if(texto.length == 0)return texto; // empty file
        String text = new String(texto, StandardCharsets.UTF_8);
        SortedMap< String, Integer> dict = new TreeMap<>();
        String current = "";
        Integer lastWordPos = 0;
        StringBuilder coding = new StringBuilder();
        boolean inDict = false;
        char phrase;
        for(int i = 0; i < text.length(); ++i){
            phrase = text.charAt(i);
            current += phrase;
            inDict = false;
            if(dict.containsKey(current)){ // if phrase in dict
                lastWordPos = dict.get(current);
                inDict = true;
            }
            else{ // else new phrase
                coding.append(lastWordPos).append(",").append(phrase);
                dict.put(current, dict.size() + 1);
                lastWordPos = 0;
                current = "";
            }
        }
        if(inDict) coding.append(lastWordPos);
        String binary_string = toBinaryString(coding.toString());

        this.original_size = text.length();
        this.compressed_size = (int)Math.ceil(binary_string.length() / 8.0);
        this.compression_ratio = ((float)compressed_size / (float)text.length()) * 100;
        return toByteArray(binary_string);
    }

    @Override
    public byte[] descomprimir(byte[] texto) {
        if(texto.length == 0)return texto; // empty file
        String text = toString(texto);
        StringBuilder coding = new StringBuilder();
        double log_2 = Math.log(2.0);
        int current_index = 0;
        ArrayList<String> phrases = new ArrayList<>();
        int current_index_size = 1;
        for(int i = 0; i < text.length(); ++i){
            if(current_index >= 2){
                double aux = (Math.log(current_index) / log_2);
                if(aux == Math.round(aux)){
                    current_index_size += 1;
                }
            }
            StringBuilder index_string = new StringBuilder(Character.toString(text.charAt(i)));
            i++;
            while(index_string.length() < current_index_size){

                index_string.append(text.charAt(i));
                ++i;
            }
            int index = Integer.parseInt(index_string.toString(), 2);
            String word = "";
            if(i != text.length()){
                word = LZ78.getLetter(text, i);
            }
            i += 7;
            if(index == 0){
                phrases.add(word);
            }
            else{
                word = phrases.get(index - 1) + word;
                phrases.add(word);
            }
            coding.append(word);
            current_index++;
        }

        return coding.toString().getBytes();
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





}
