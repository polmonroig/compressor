package domain;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
 
public class AutoAlgorithm {



    private int currentID;
    private static final char END_LINE = '\n';
    private static String unsupportedFile;
    private Stats stats;
    private int iterator;

    public AutoAlgorithm(){
        currentID = AlgorithmSet.LZ78_ID;
        stats = new GlobalStats();
    }

    public static String getUnsupportedFile(){return unsupportedFile;}

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


    private ByteArrayOutputStream readWhileNotEndLine(byte[] array){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        while (array[iterator] != END_LINE){
            stream.write(array[iterator]);
            ++iterator;
        }
        ++iterator;
        return stream;
    }

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

    private int byteToInt(byte[] bytes){
        int sum = 0;
        for(byte b : bytes){
            sum = sum * 256 + (b & 0xFF);
        }
        return sum;
    }


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


    public void setAlgorithm(int id) {
        currentID = id;
    }


    public Stats getLocalStats() {
        return stats;
    }
}
