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

  

    /**
     * <p>The compression method makes a compression of a given text</>
     * @param texto the text to compress
     * @return the compressed text
     */
    @Override
    public byte[] comprimir(byte[] texto) {
        long startTime = System.nanoTime(); // empezar contador de tiempo
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
        String binary_string = Utils.toBinaryString(coding.toString());



        byte[] compression = Utils.toByteArray(binary_string);

        // Calculo estadisticas
        long endTime = System.nanoTime();
        this.estadisticaLocal.setMidaArxiuInicial(texto.length);
        this.estadisticaLocal.setMidaArxiuFinal((int)Math.ceil(binary_string.length() / 8.0) + 1);
        this.estadisticaLocal.setGrauCompresio(((float)this.getCompressedSize() / (float)this.getOriginalSize()) * 100);

        this.estadisticaLocal.setTiempoCompresio((float)((endTime - startTime) / 1000000.0)); // miliseconds
        this.estadisticaLocal.setVelocitatCompresio(texto.length / this.estadisticaLocal.getTiempoCompresio());
        return compression;
    }

    @Override
    public byte[] descomprimir(byte[] texto) {
        if(texto.length == 0)return texto; // empty file
        String text = Utils.toString(texto);
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
