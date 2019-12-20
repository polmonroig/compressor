package domain;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
 

/**
 * The AutoAlgorithm is responsible for compressing and decompressing multiple
 * files automatically given a specific algorithm and compression quality (JPEG)
 * It is also responsible for saving stats of all the compressions made
 * of all the files. It differs from other algorithms in that it does not know
 * anything about compression, and ir requires an Algorithm to compress the file
 * for it.
 *
 * The basic functionality in some way is to encode the compressions and decode
 * the decompressions
 * */
public class AutoAlgorithm {


    /**
     * This is the selected compression algorithm
     * */
    private int currentID;
    /**
     * This is just an end line character declared for readability
     * */
    private static final char END_LINE = '\n';
    /**
     * This string is null always except when an unsupported file
     * is being compressed in than case it saves the path to
     * that file
     * */
    private static String unsupportedFile;
    /**
     * This saves the average stats for all the file compressions
     * */
    private Stats stats;
    /**
     * This is an iterator, that points to the current
     * byte in the file
     * */
    private int iterator;

    /**
     * <p>Basic constructor</p>
     * */
    public AutoAlgorithm(){
        currentID = AlgorithmSet.LZ78_ID;
        stats = new GlobalStats();
    }

    /**
     * <p>Getter for the unsupported file, beware since
     * if no exception has been raise it returns null</p>
     * @return unsupported file path
     * */
    public static String getUnsupportedFile(){return unsupportedFile;}

    /**
     * <p>Compress an array of files</p>
     * @param files contains all the PhysicalFiles for compression
     * @return a byte array with the output file content
     * */
    public byte[] compressFiles(ArrayList<PhysicalFile> files) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        for(PhysicalFile file : files) {
            if (file.isImage()) {
                file.selectAlgorithm(AlgorithmSet.JPEG_ID);
                stream.write((file.getRelativePath() + END_LINE + AlgorithmSet.JPEG_ID + END_LINE).getBytes());
            } else if (file.isText()) {
                file.selectAlgorithm(currentID);
                stream.write((file.getRelativePath() + END_LINE + currentID + END_LINE).getBytes());
            }
            else {
                unsupportedFile = file.getCompletePath() + "." + file.getFileExtension();
                throw new IOException();
            }
            file.compress();
            ((GlobalStats)stats).setStats(file.getLocalStats());
            // write file size to a 4 byte array
            BigInteger bigInt = BigInteger.valueOf(file.getSize());
            byte[] array = bigInt.toByteArray();
            stream.write(array);
            stream.write(END_LINE);
            stream.write(file.getContent());
        }

        return stream.toByteArray();
    }

    /**
     * <p>Reads a given array until an end line is found</p>
     * @return the content until the end line
     * */
    private ByteArrayOutputStream readWhileNotEndLine(byte[] array){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        while (array[iterator] != END_LINE){
            stream.write(array[iterator]);
            ++iterator;
        }
        ++iterator;
        return stream;
    }

    /**
     * <p>Reads the content of a file and returns a Physical file</p>
     *
     * */
    private PhysicalFile readFileContent(byte[] array, int compressionType, int compressionSize, String fileName){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int size = iterator + compressionSize;
        while(iterator < size){
            stream.write(array[iterator]);
            ++iterator;
        }
        String extension = ".txt";
        if(compressionType == AlgorithmSet.JPEG_ID)extension = ".ppm";
        PhysicalFile file = new PhysicalFile(new File(fileName + extension));
        file.setContent(stream.toByteArray());
        return file;
    }

    /**
     * <p>Given a four byte array it returns it int representation</p>
     * @param bytes the byte array
     * @return the bytes as an integer
     * */
    private int byteToInt(byte[] bytes){
        int sum = 0;
        for(byte b : bytes){
            sum = sum * 256 + (b & 0xFF);
        }
        return sum;
    }

    /**
     * <p>Decompress file</p>
     * @param file to decompress
     * @return an array of files from the compression
     * */
    public ArrayList<PhysicalFile> decompressFile(PhysicalFile file){
        ArrayList<PhysicalFile> files = new ArrayList<>();
        iterator = 0;
        String fileName;
        int compressionType;
        int compressionSize;
        byte[] content = file.getContent();
        while(iterator < content.length){

            fileName = readWhileNotEndLine(content).toString(); // read file name
            compressionType =  Integer.parseInt(readWhileNotEndLine(content).toString()); // read compression type
            compressionSize = byteToInt(readWhileNotEndLine(content).toByteArray()); // read size

            PhysicalFile newFile = readFileContent(content, compressionType, compressionSize, fileName);
            newFile.selectAlgorithm(compressionType);
            newFile.decompress();
            files.add(newFile);

        }

        return files;
    }

    /**
     * <p>Sets the id of the default text compression algorithm</p>
     * @param id is the algorithm
     * */
    public void setAlgorithm(int id) {
        currentID = id;
    }

    /**<p>Getter for the compression stats</p>
     * @return the stats
     * */
    public Stats getLocalStats() {
        return stats;
    }
}
