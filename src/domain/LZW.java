package domain;
import java.util.*;

public class LZW extends Algoritme{

    @Override
    public byte[] comprimir(byte[] texto) {
        long startTime = System.nanoTime(); // empezar contador de tiempo
        SortedMap< String, Integer> dict = new TreeMap<>(); //map per guardar el diccionari
        String cadenachars = new String(texto); // convertir l'array de bytes en un String
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

        byte[] ret = Utils.toByteArray(s);

        //System.out.println(retsize);
        for (int i = 0; i < retsize; i++ ){
            int j = i*8;
            String e = "" + s.charAt(j) + s.charAt(i*8+1) + s.charAt(j+2) + s.charAt(j+3) + s.charAt(j+4) + s.charAt(j+5) + s.charAt(j+6) + s.charAt(j+7);
            //System.out.println(e + " " + ret[i] + " " );

        }
        // Calculo estadisticas
        long endTime = System.nanoTime();
        this.estadisticaLocal.setMidaArxiuInicial(texto.length);
        this.estadisticaLocal.setMidaArxiuFinal(ret.length);
        this.estadisticaLocal.setGrauCompresio(((float)this.getCompressedSize() / (float)this.getOriginalSize()) * 100);

        this.estadisticaLocal.setTiempoCompresio((float)((endTime - startTime) / 1000000.0)); // miliseconds
        this.estadisticaLocal.setVelocitatCompresio(texto.length / this.estadisticaLocal.getTiempoCompresio());
        return ret;
    }


    /** Convert 8 bit to 12 bit */
    private String to12bit(int i) {
        String temp = Integer.toBinaryString(i);
        while (temp.length() < 12) {
            temp = "0" + temp;
        }
        return temp;
    }

    private String to8bit(int i) {
        String temp = Integer.toBinaryString(i);
        while (temp.length() < 8) {
            temp = "0" + temp;
        }
        return temp;
    }

    private String round2byte(String s) {
        while (s.length()%8 != 0) {
            s = s + "0";
        }
        return s;
    }





    @Override
    public byte[] descomprimir(byte[] text) {

        if (text.length > 0) {
            String s = Utils.toString(text);
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

        return text;

    }


}


