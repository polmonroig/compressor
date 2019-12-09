package domain;


import java.util.ArrayList;

public class AlgorithmSet {
    private static ArrayList<Algorithm> algorithms;

    public static final int LZ78_ID = 0;
    public static final int LZSS_ID = 1;
    public static final int LZW_ID = 2;
    public static final int JPEG_ID = 3;
    public static final int AUTO_ID = 4;


    static{
        algorithms = new ArrayList<>();
        algorithms.add(new LZ78()); // id = 0
        algorithms.add(new LZSS()); // id = 1
        algorithms.add(new LZW()); // id = 2
        algorithms.add(new JPEG()); // id = 3
    }

    public static Algorithm getAlgorithm(int id){
        return algorithms.get(id);
    }


    public static void setQuality(int quality){
        ((JPEG)algorithms.get(3)).setQuality(quality);
    }



}
