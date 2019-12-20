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
 
public class LZ78 implements Algorithm {



    /**
     * <p>The compression method makes a compression of a given text</>
     * @param binaryFile the text to compress
     * @return the compressed text
     */
    @Override
    public byte[] compress(byte[] binaryFile) {
        // In the first part of the algorithm we
        // convert the original text into a string of the codified text
        // making use of a dictionary
        if(binaryFile.length == 0)return binaryFile; // empty file
        String text = new String(binaryFile, StandardCharsets.UTF_8);
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

        // Now we convert the codified string into a string
        // of bits, this is necessary to ensure a maximum
        // compression rate
        String binary_string = Utils.toBinaryString(coding.toString());

        // Finally we convert the string of bits into a byte array
        return Utils.toByteArray(binary_string);
    }

    /**
     * <p>The decompression method takes a compressed file and returns the original text</>
     * @param binaryFile the text to decompress
     * @return the decompressed text
     */
    @Override
    public byte[] decompress(byte[] binaryFile) {
        if(binaryFile.length == 0)return binaryFile; // empty file
        String text = Utils.toString(binaryFile);
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
                word = Utils.getLetter(text, i);
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







}
