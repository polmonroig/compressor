import java.io.*;
import java.sql.Array;
import java.util.*;


public class LZ78 extends Algoritme{
    @Override
    public String comprimir(String texto) {

        String out = "";
        //HashMap< String,IntegerPair> dict =
        //        new HashMap<String,IntegerPair>(); ;
        SortedMap< String,IntegerPair> dict =
                new TreeMap<String,IntegerPair>(); ;
        String current = "";
        Integer size = 1;
        Integer lastWordPos = 0;
        String displayed_coding = "";
        String coding = "";
        boolean inDict = false;
        for(int i = 0; i < texto.length(); ++i){
            char phrase = texto.charAt(i);
            current += phrase;
            inDict = false;
            if(dict.containsKey(current)){ // if phrase in dict
                if(i != texto.length() - 1)lastWordPos = dict.get(current).getIndex();
                inDict = true;
            }
            else{ // else new phrase
                coding += Integer.toString(lastWordPos) + phrase;
                dict.put(current, new IntegerPair(size, lastWordPos));
                //displayed_coding += "(" + lastWordPos + "," + current + "), ";
                lastWordPos = 0;
                ++size;
                current = "";
            }
        }
        if(inDict){
            dict.put(current, new IntegerPair(size, lastWordPos));
        }
        return out;
    }

    @Override
    public String descomprimir(String texto) {
        return null;
    }

    private class IntegerPair{
        Integer index, lastWord;

        IntegerPair(Integer i, Integer l){
            index = i;
            lastWord = l;
        }

        Integer getIndex(){
            return index;
        }

        Integer getLastWord(){
            return lastWord;
        }

    }
}
