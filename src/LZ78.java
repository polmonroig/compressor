import java.util.*;
import java.lang.Math;

public class LZ78 extends Algoritme{

    final private int CHAR_SIZE = 8;

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
                coding += Integer.toString(lastWordPos) + phrase;
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
        // System.out.println("Decompressed encoding: " + descomprimir(coding));

        return "";
    }

    private String toBinaryString(String coding){
        String binary_string = Integer.toBinaryString(coding.charAt(1)); // first character diff
        binary_string = addZeros(binary_string, CHAR_SIZE);
        int current_index_size = 0;
        double log_2 = Math.log(2);
        for(int i = 2; i < coding.length(); i += 2){
            char index = coding.charAt(i);

            binary_string += addZeros(Integer.toBinaryString(Character.getNumericValue(index)), current_index_size);
            if(i + 1 < coding.length()){
                char value = coding.charAt(i + 1);
                binary_string += addZeros(Integer.toBinaryString(value), CHAR_SIZE);
            }
            current_index_size = (int)(Math.log(2 * ((i >> 1) - 1)) / log_2); // log(2  * (pos - 1)) / log 2 = k

        }
        return binary_string;
    }

    private String toCharacterString(String binary_string){
        String coding = "";
        ArrayList<Character> dict;
        for(int i = 0; i < binary_string.length(); i += 2){
            char index = binary_string.charAt(i);
            if(i + 1 < binary_string.length()){
                char value = binary_string.charAt(i + 1);

            }

        }
        return coding;
    }


    static private String addZeros(String binary, int n_zeros){
        while(binary.length() < n_zeros){
            binary = "0" + binary;
        }
        return binary;
    }

    @Override
    public String descomprimir(String texto) {
        // FUNCION MAL HECHA PARA INDICES CON 2 CIFRAS
        String coding = "";
        ArrayList<String> dict = new ArrayList<>();
        for(int i = 0; i < texto.length(); i += 2){
            int index = Character.getNumericValue(texto.charAt(i));
            String aux = "";
            if(i + 1 < texto.length()){
                String value = Character.toString(texto.charAt(i + 1));
                coding += GetText(texto, index, value);
            }
            else {
                coding += GetText(texto, Character.getNumericValue(texto.charAt((index * 2) - 2)),
                        Character.toString(texto.charAt((index * 2) - 1)));
            }


        }
        return coding;
    }


    static private String GetText(String text, int index, String current){
        if(index > 0){
            return GetText(text, Character.getNumericValue(text.charAt((index * 2) - 2)),
                    Character.toString(text.charAt((index * 2) - 1))) + current;
        }
        return current;
    }

}
