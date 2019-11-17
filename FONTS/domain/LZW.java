package domain;
import java.util.*;

public class LZW extends Algorithm {

    @Override
    public byte[] compress(byte[] binaryFile) {
        long startTime = System.nanoTime(); // empezar contador de tiempo
        SortedMap< String, Integer> dict = new TreeMap<>(); //map per guardar el diccionari
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
                s.append(to12bit(dict.get(w)));

                if (dictSize < 4096) {
                    dict.put(wk,dictSize);
                    dictSize++;
                }
                w = k;
            }
        }
        s.append(to12bit(dict.get(w)));


        s = new StringBuilder(round2byte(s.toString()));
        

        byte[] ret = Utils.toByteArray(s.toString());
        
        
        // Stats
        long endTime = System.nanoTime();
        this.localStats.setOriginalFileSize(binaryFile.length);
        this.localStats.setCompressedFileSize(ret.length);
        this.localStats.setCompressionDegree(((float)this.getCompressedSize() / (float)this.getOriginalSize()) * 100);

        this.localStats.setCompressionTime((float)((endTime - startTime) / 1000000.0)); // miliseconds
        this.localStats.setCompressionSpeed(binaryFile.length / this.localStats.getCompressionTime());
        return ret;
    }


    /** Convert 8 bit to 12 bit */
    private String to12bit(int i) {
        StringBuilder temp = new StringBuilder(Integer.toBinaryString(i));
        while (temp.length() < 12) {
            temp.insert(0, "0");
        }
        return temp.toString();
    }

    private String round2byte(String s) {
        StringBuilder sBuilder = new StringBuilder(s);
        while (sBuilder.length()%8 != 0) {
            sBuilder.append("0");
        }
        s = sBuilder.toString();
        return s;
    }





    @Override
    public byte[] decompress(byte[] binaryFile) {

        if (binaryFile.length > 0) {
            String s = Utils.toString(binaryFile);
            List<Integer> l = new ArrayList<Integer>();


            for (int i = 0; i < s.length()-4; i = i + 12) {
                String e = "" + s.charAt(i) + s.charAt(i+1) + s.charAt(i+2) + s.charAt(i+3) + s.charAt(i+4) + s.charAt(i+5) + s.charAt(i+6) + s.charAt(i+7) + s.charAt(i+8) + s.charAt(i+9) + s.charAt(i+10) + s.charAt(i+11) ;l.add(Integer.parseInt(e, 2));

            }

            SortedMap< Integer, String> dict = new TreeMap<>();

            StringBuilder res = new StringBuilder();


            int dictSize = 256;
            for (int i = 0; i < 256; i++) {
                dict.put(i, "" + (char)i);
            }

            int cod_nou;
            String chain;
            int cod_vell = l.get(0);
            String caracter = dict.get(cod_vell);
            res.append(caracter);

            for (int i = 1; i < l.size(); ++i) {
                cod_nou = l.get(i);
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


