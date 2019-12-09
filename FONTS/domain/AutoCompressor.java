package domain;

import domain.controllers.DomainCtrl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class AutoCompressor {

    private DomainCtrl domainCtrl;


    private int currentID;

    public AutoCompressor(DomainCtrl controller){
        domainCtrl = controller;
        currentID = AlgorithmSet.LZ78_ID;

    }

    public byte[] compressFiles(ArrayList<PhysicalFile> files) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        for(PhysicalFile file : files) {
            if (file.isImage()) {
                file.selectAlgorithm(AlgorithmSet.JPEG_ID);
                stream.write((file.getRelativePath() + "\n" + AlgorithmSet.JPEG_ID + "\n").getBytes());

                file.compress();
                stream.write(Integer.parseInt((ByteBuffer.allocate(4).putInt(file.getSize()) + "\n")));
                stream.write(file.getContent());
            } else if (file.isText()) {
                file.selectAlgorithm(currentID);
                stream.write((file.getRelativePath() + "\n" + currentID + "\n").getBytes());
                file.compress();
                // write file size to a 4 byte array
                stream.write(Integer.parseInt((ByteBuffer.allocate(4).putInt(file.getSize()) + "\n")));
                stream.write(file.getContent());
            }
        }

        return stream.toByteArray();
    }

    private ByteArrayOutputStream readWhileNotEndLine(byte[] array, Integer i){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        while (array[i] != '\n'){
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
