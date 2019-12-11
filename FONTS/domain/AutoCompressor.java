package domain;

import domain.controllers.DomainCtrl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class AutoCompressor {

    private DomainCtrl domainCtrl;


    private int currentID;
    private static final int NO_ERROR = 0;
    public static final int UNSUPPORTED_FILE = 1;
    private static int flag = NO_ERROR;
    private static final byte END_LINE = '\n';

    public AutoCompressor(DomainCtrl controller){
        domainCtrl = controller;
        currentID = AlgorithmSet.LZ78_ID;

    }

    public static int getFlag (){return flag;}

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
                flag = UNSUPPORTED_FILE;
                throw new IOException();
            }
            file.compress();
            // write file size to a 4 byte array
            stream.write(BigInteger.valueOf(file.getSize()).toByteArray());
            stream.write(END_LINE);
            stream.write(file.getContent());
        }

        return stream.toByteArray();
    }

    private ByteArrayOutputStream readWhileNotEndLine(byte[] array, Integer i){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        while (array[i] != END_LINE){
            stream.write(array[i]);
            ++i;
        }
        ++i;
        return stream;
    }

    private PhysicalFile readFileContent(byte[] array, Integer i, int compressionSize, String fileName){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int size = i + compressionSize;
        while(i < size){
            stream.write(array[i]);
            ++i;
        }
        PhysicalFile file = new PhysicalFile(new File(fileName));
        file.setContent(stream.toByteArray());
        return file;
    }


    public ArrayList<PhysicalFile> decompressFile(PhysicalFile file){
        ArrayList<PhysicalFile> files = new ArrayList<>();
        Integer i = 0;
        String fileName = "";
        int compressionType = 0;
        int compressionSize = 0;
        byte[] content = file.getContent();
        while(i < content.length){

            fileName = readWhileNotEndLine(content, i).toString(); // read file name
            compressionType =  Integer.parseInt(readWhileNotEndLine(content, i).toString()); // read compression type
            compressionSize = Integer.parseInt(readWhileNotEndLine(content, i).toString()); // read size

            PhysicalFile newFile = readFileContent(content, i, compressionSize, fileName);
            file.selectAlgorithm(compressionType);
            files.add(newFile);

        }

        return files;
    }


    public void setAlgorithm(int id) {
        currentID = id;
    }


}
