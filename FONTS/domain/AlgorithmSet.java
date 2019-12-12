package domain;


import java.util.ArrayList;

/**
 * AlgorithmSet is a global point to access the different
 * algorithms available, by accessing the algorithms using
 * this reference the instances are initiated only once
 * and thus they become Singleton instances
 *
 * */
public class AlgorithmSet {
    /**
     * List of algorithms available for using
     * */
    private static ArrayList<Algorithm> algorithms;

    /**
     * ID of the LZ78 instance
     * */
    public static final int LZ78_ID = 0;
    /**
     * ID of the LZSS instance
     * */
    public static final int LZSS_ID = 1;
    /**
     * ID of the LZW instance
     * */
    public static final int LZW_ID = 2;
    /**
     * ID of the JPEG instance
     * */
    public static final int JPEG_ID = 3;

    /**
     * Statically instantiate the algorithms,
     * and assign them to their current IDs
     * */
    static{
        algorithms = new ArrayList<>();
        algorithms.add(new LZ78()); // id = 0
        algorithms.add(new LZSS()); // id = 1
        algorithms.add(new LZW()); // id = 2
        algorithms.add(new JPEG()); // id = 3
    }

    /**
     * <p>Entry point for the algorithm instances</p>
     * @param id is the respective algorithm ID
     * @return the requested algorithm
     * */
    public static Algorithm getAlgorithm(int id){
        return algorithms.get(id);
    }

    /**
     * <p>Set the quality of the JPEG algorithm </p>
     * @param quality is the quality of the compression
     * */
    public static void setQuality(int quality){
        ((JPEG)algorithms.get(3)).setQuality(quality);
    }



}
