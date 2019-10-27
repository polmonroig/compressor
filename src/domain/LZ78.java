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
    final private int CHAR_SIZE = 8;

    /**
     * <p>The compression method makes a compression of a given text</>
     * @param texto the text to compress
     * @return the compressed text
     */
    @Override
    public String comprimir(String texto) {
        SortedMap< String, Integer> dict = new TreeMap<>(); ;
        String current = "";
        Integer lastWordPos = 0;
        String coding = "";
        boolean inDict = false;
        char phrase = ' ';
        for(int i = 0; i < texto.length(); ++i){
            phrase = texto.charAt(i);
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

        System.out.println("Coding in letters: " + coding);
        System.out.println("Letter coding size: " + coding.length() + " bytes");
        String binary_string = toBinaryString(coding);
        System.out.println("Binary encoding: " + binary_string);
        System.out.println("Binary encoding length: " +  binary_string.length());
        System.out.println("Original size: " + texto.length() + " bytes");
        double compressed_size = Math.ceil(binary_string.length() / 8.0);
        System.out.println("Compressed size: " + compressed_size + " bytes");
        System.out.println("Compression ratio: " + (compressed_size / texto.length() * 100) + "%");
        System.out.println("Decompressed encoding: " + descomprimir(binary_string));

        return toBinaryString(coding);
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

            binary_string += addZeros(Integer.toBinaryString(Integer.parseInt(index)), current_index_size);
            if(i  < coding.length()){
                char value = coding.charAt(i);
                binary_string += addZeros(Integer.toBinaryString(value), CHAR_SIZE);
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
    static private String addZeros(String binary, int n_zeros){
        while(binary.length() < n_zeros){
            binary = "0" + binary;
        }
        return binary;
    }


    
    static private String getLetter(String text,  int i){
        String letter = "";
        for(int j = i; j < i + 8; ++j){
            letter += Character.toString(text.charAt(j));
        }
        int charCode = Integer.parseInt(letter, 2);
        return Character.toString((char) charCode);
    }

    @Override
    public String descomprimir(String texto) {
        String coding = "";
        double log_2 = Math.log(2.0);
        int current_index = 0;
        ArrayList<String> phrases = new ArrayList<>();
        int current_index_size = 1;
        for(int i = 0; i < texto.length(); ++i){


            if(current_index >= 2){
                double aux = (Math.log(current_index) / log_2);
                if(aux == Math.round(aux)){
                    current_index_size += 1;
                }
            }
            String index_string = Character.toString(texto.charAt(i));
            i++;
            while(index_string.length() < current_index_size){

                index_string += Character.toString(texto.charAt(i));
                ++i;
            }
            int index = Integer.parseInt(index_string, 2);

            String word = "";
            if(i != texto.length()){
                word = LZ78.getLetter(texto, i);
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
        return coding;
    }



}
