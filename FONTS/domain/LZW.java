package domain;
import java.util.*;

/**
 * LZW ASCII compression algorithm
 *
 * @author Jaume Bernaus Serra
 *
 */
public class LZW implements Algorithm {

    /**
     * <p>The compression method makes a compression of a given text</>
     * @param binaryFile the text to compress
     * @return the compressed text
     */
    @Override
    public byte[] compress(byte[] binaryFile) {
        Map<String, Integer> dict = new HashMap<String, Integer>(); //map per guardar el diccionari
        String charChain = new String(binaryFile); // convertir l'array de bytes en un String
        StringBuilder s = new StringBuilder();

 
        int dictSize = 256;
        for (int i = 0; i < 256; i++) {
            dict.put("" + (char)i, i);
        }

        String w = "";
        String k = "";
        String wk = "";

        for (int i = 0; i < charChain.length(); ++i) {
            k = String.valueOf(charChain.charAt(i));
            wk = w+k;
            if (dict.containsKey(wk)) {
                w = wk;
            }
            else {
                s.append(to16bit(dict.get(w)));

                if (dictSize < 65536) {
                    dict.put(wk,dictSize);
                    dictSize++;
                }

                w = k;
            }
        }
        s.append(to16bit(dict.get(w)));

        return Utils.toByteArray(s.toString());
    }

    /**
     * <p>The method to16bit makes a 16 bit binary string from an integer</>
     * @param i is the integer
     * @return binary string
     */
    private String to16bit(int i) {
        StringBuilder temp = new StringBuilder(Integer.toBinaryString(i));
        while (temp.length() < 16) {
            temp.insert(0, "0");
        }
        return temp.toString();
    }



    /**
     * <p>The decompression method takes a compressed file and returns the original text</>
     * @param binaryFile the text to decompress
     * @return the decompressed text
     */
    @Override
    public byte[] decompress(byte[] binaryFile) {

        if (binaryFile.length > 0) {
            String s = Utils.toString(binaryFile);

            Map<Integer,String> dict = new HashMap<Integer,String>();

            StringBuilder res = new StringBuilder();


            int dictSize = 256;
            for (int i = 0; i < 256; i++) {
                dict.put(i, "" + (char)i);
            }

            int cod_nou;
            String chain;
            int cod_vell = Integer.parseInt(s.substring(0, 16),2);
            String caracter = dict.get(cod_vell);
            res.append(caracter);

            for (int i = 16; i < s.length(); i = i+ 16) {
                cod_nou = Integer.parseInt(s.substring(i, i+16),2);
                if (!dict.containsKey(cod_nou)) {
                    chain = dict.get(cod_vell) + caracter;
                }
                else {
                    chain = dict.get(cod_nou);
                }
                res.append(chain);
                caracter = String.valueOf(chain.charAt(0));
                String dic_inp = dict.get(cod_vell)+caracter;
                dict.put(dictSize, dic_inp);
                ++dictSize;
                cod_vell = cod_nou;



            }

            return res.toString().getBytes();
        }

        return binaryFile;

    }


}
