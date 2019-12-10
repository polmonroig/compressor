package domain;

import java.io.File;

public class PhysicalFile {

    private String fileName;
    private File file;
    private Algorithm algorithm;
    private String fileDir;
    private String fileExtension;
    private String relativeDir;
    private int id;
    private byte[] content;

    public static final String LZ78_EXTENSION = "lz78";
    public static final String LZSS_EXTENSION = "lzss";
    public static final String LZW_EXTENSION = "lzw";
    public static final String JPEG_EXTENSION = "jpeg";
    public static final String AUTO_EXTENSION = "auto";
    private static final String TXT_EXTENSION = "txt";
    private static final String IMG_EXTENSION = "ppm";


    private static String[] algorithmsExtensions = {LZ78_EXTENSION, LZSS_EXTENSION,
                                             LZW_EXTENSION, JPEG_EXTENSION, AUTO_EXTENSION};

    public PhysicalFile(File virtualFile){
        file = virtualFile;
        setFileName();
        setFileDir();
        setFileExtension();
        algorithm = AlgorithmSet.getAlgorithm(AlgorithmSet.LZ78_ID);
    }

    public void compress(){
        content = algorithm.compress(content);
    }

    public void decompress(){
        content = algorithm.compress(content);
    }


    public void setContent(byte[] bytes){
        content = bytes;
    }

    public void setRelativeDir(String dir){
        relativeDir = dir;
    }


    public byte[] getContent(){
        return content;
    }

    public String getFileName(){
        return fileName;
    }

    public String getFileDir(){
        return fileDir;
    }

    public String getFileExtension(){
        return fileExtension;
    }

    public String getCompletePath(){
        return fileDir + fileName;
    }

    public String getRelativePath(){
        return relativeDir + fileName;
    }

    private  void setFileDir(){
        int indexLastSlash = file.getPath().lastIndexOf("/");
        fileDir = file.getPath().substring(0, indexLastSlash) + "/";
    }

    private  void setFileName(){
        int indexLastSlash = file.getPath().lastIndexOf("/") + 1;
        int indexLastDot = file.getPath().lastIndexOf(".");
        fileName = file.getPath().substring(indexLastSlash, indexLastDot);
    }

    private void setFileExtension() {
        int indexLastDot = file.getPath().lastIndexOf(".");
        int size = file.getPath().length();
        fileExtension = file.getPath().substring(indexLastDot + 1, size);
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
        return fileExtension.equals(IMG_EXTENSION);
    }

    public boolean isAuto(){
        return fileExtension.equals(AUTO_EXTENSION);
    }

    public int getSize() {
        return content.length;
    }


}
