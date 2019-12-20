package domain;

import java.io.File;


/**
 * PhysicalFile is a representation of a File
 * and the different qualities it has, such as the
 * name, the path, a relative path, the extension.
 * It also contains the content of that file and
 * that content can be compressed and decompressed.
 *
 * Every time a compress or decompression is made,
 * the content of the PhysicalFile changes.
 * */
public class PhysicalFile {
 
    /**
     * This is the name of the file
     * */
    private String fileName;
    /**
     * This is the algorithm for the compression
     * and decompression of the file, a specific
     * algorithm is assigned to each File altough only
     * one instance of each algorithm exists because we
     * make use of AlgorithmSet.
     * */
    private Algorithm algorithm;
    /**
     * This is the absolute file directory
     * */
    private String fileDir;
    /**
     * This is the file extension, it can
     * only be one of the extensions predefined,
     * otherwise if the compression is made it
     * creates undefined behaviour or an error
     * */
    private String fileExtension;
    /**
     * Relative path of the file, specified by the programmer
     * */
    private String relativeDir;
    /**
     * The id of the selected algorithm
     * */
    private int id;
    /**
     * This contains the content of the file
     * */
    private byte[] content;

    /**
     * This is the extension of a compression made by
     * the LZ78 algorithm
     * */
    public static final String LZ78_EXTENSION = "lz78";
    /**
     * This is the extension of a compression made by
     * the LZSS algorithm
     * */
    public static final String LZSS_EXTENSION = "lzss";
    /**
     * This is the extension of a compression made by
     * the LZW algorithm
     * */
    public static final String LZW_EXTENSION = "lzw";
    /**
     * This is the extension of a compression made by
     * the JPEG algorithm
     * */
    public static final String JPEG_EXTENSION = "jpeg";
    /**
     * This is the extension of a compression made by
     * the AUTO algorithm
     * */
    public static final String AUTO_EXTENSION = "auto";
    /**
     * This is the extension of a text file
     * */
    private static final String TXT_EXTENSION = "txt";
    /**
     * This is the extension of a ppm image file
     * */
    private static final String PPM_EXTENSION = "ppm";

    /**
     * List of the possible extensions for compression files
     * */
    private final static String[] algorithmsExtensions = {LZ78_EXTENSION, LZSS_EXTENSION,
                                             LZW_EXTENSION, JPEG_EXTENSION, AUTO_EXTENSION};

    /**
     * This are the stats that are saved when a compression is made
     * if no compression is made it contains empty stats
     * */
    private Stats localStats;

    /**
     * <p>Base constructor</p>
     * @param virtualFile the virtualFile that needs to be casted
     *                    into a physical file
     * */
    public PhysicalFile(File virtualFile){
        setFileName(virtualFile);
        setFileDir(virtualFile);
        setFileExtension(virtualFile);
        algorithm = AlgorithmSet.getAlgorithm(AlgorithmSet.LZ78_ID);
        id = AlgorithmSet.LZ78_ID;
        localStats = new Stats();
    }

    /**
     * <p>Compress the file with the pre-selected algorithm
     *    and recalculate the extension</p>
     * */
    public void compress(){

        long timeInit = System.nanoTime();
        localStats.setOriginalFileSize(content.length);
        content = algorithm.compress(content);
        long timeEnd = System.nanoTime();
        localStats.setCompressionTime((float)((timeEnd - timeInit) / 1000000.0));
        localStats.setCompressedFileSize(content.length);
        localStats.setCompressionSpeed(localStats.getOriginalFileSize() / localStats.getCompressionTime());
        localStats.setCompressionDegree((localStats.getCompressedFileSize() / (float)localStats.getOriginalFileSize()) * 100);
        // recalculate extension
        fileExtension = algorithmsExtensions[id];
    }

    /**
     * <p>Decompress the file with the pre-selected algorithm
     *    and recalculate the extension</p>
     * */
    public void decompress(){
        content = algorithm.decompress(content);
        // recalculate extension
        if(id == AlgorithmSet.JPEG_ID){
            fileExtension = PPM_EXTENSION;
        }
        else {
            fileExtension = TXT_EXTENSION;
        }
    }

    /**
     * <p>Set the of the file</p>
     * @param bytes content of the file to save
     * */
    public void setContent(byte[] bytes){
        content = bytes;
    }

    /**
     * <p>Set relative directory</p>
     * @param dir specified directory
     * */
    public void setRelativeDir(String dir){
        relativeDir = dir;
    }

    /**
     * <p>Gets the content of the PhysicalFile</p>
     * @return the content of the file
     * */
    public byte[] getContent(){
        return content;
    }

    /**
     * <p>Gets the name of the file without extension</p>
     * @return the name of the file
     * */
    public String getFileName(){
        return fileName;
    }

    /**
     * <p>Gets the absolute path to the dir without the file name</p>
     * @return absolute path 
     * */
    public String getFileDir(){
        return fileDir;
    }

    /**
     * <p>Gets the file extension</p>
     * @return string with the extension
     * */
    public String getFileExtension(){
        return fileExtension;
    }

    /**
     * <p>Gets the complete path of the file and its name</p>
     * @return string with path
     * */
    public String getCompletePath(){
        return fileDir + fileName;
    }

    public String getRelativePath(){
        return relativeDir + fileName;
    }

    private  void setFileDir(File file){
        int indexLastSlash = file.getPath().lastIndexOf(File.separator);
        fileDir = file.getPath().substring(0, indexLastSlash) + File.separator;
    }

    private  void setFileName(File file){
        int indexLastSlash = file.getPath().lastIndexOf(File.separator) + 1;
        int indexLastDot = file.getPath().lastIndexOf(".");
        if(indexLastDot == -1)indexLastDot = file.getPath().length() - 1;
        fileName = file.getPath().substring(indexLastSlash, indexLastDot);
    }

    private void setFileExtension(File file) {
        int indexLastDot = file.getPath().lastIndexOf(".");
        if(indexLastDot == -1){
            fileExtension = "";
        }
        else{
            int size = file.getPath().length();
            fileExtension = file.getPath().substring(indexLastDot + 1, size);
        }

    }


    public void calculateId(){
        for(int i = 0; i < algorithmsExtensions.length; ++i){
            if(fileExtension.equals(algorithmsExtensions[i])){
                id = i;
                break;
            }
        }
    }

    public String getIdName(){
        return algorithmsExtensions[id];
    }

    public String getOriginalIdName(){
        if(isImage() || fileExtension.equals(JPEG_EXTENSION)){
            return PPM_EXTENSION;
        }
        else{
            return TXT_EXTENSION;
        }

    }

    public int getId() {
        return id;
    }

    public void selectAlgorithm(int i){
        id = i;
        fileExtension = algorithmsExtensions[id];
    }

    public boolean isText(){
        return fileExtension.equals(TXT_EXTENSION);
    }

    public boolean isImage(){
        return fileExtension.equals(PPM_EXTENSION);
    }

    public boolean isAuto(){
        return fileExtension.equals(AUTO_EXTENSION);
    }

    public int getSize() {
        return content.length;
    }

    public Stats getLocalStats(){return localStats;}
}
