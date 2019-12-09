package domain;

import domain.controllers.DomainCtrl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class AutoCompressor {

    private DomainCtrl domainCtrl;
    private Algorithm algorithm;


    private int currentID;

    public AutoCompressor(DomainCtrl controller){
        domainCtrl = controller;
        currentID = AlgorithmSet.LZ78_ID;
        algorithm = AlgorithmSet.getAlgorithm(currentID);

    }


    private ByteArrayOutputStream recursiveCompressFile(File[] files, String prefix) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        for (File file : files) {
            if(file.isDirectory()){
                stream.writeBytes((prefix + file.getName() + "/" + "\n").getBytes());
                ByteArrayOutputStream recursiveStream = recursiveCompressFile(Objects.requireNonNull(file.listFiles()), prefix + file.getName() + "/");
                recursiveStream.writeTo(stream);
            }
            else{
                byte[] fileBytes = domainCtrl.readFile(file.getPath());

                int lastDotPos = file.getName().lastIndexOf('.');

                String name = file.getName().substring(0, lastDotPos);
                String type = file.getName().substring(lastDotPos);
                if (type.equals(".txt")){
                    stream.write((prefix + "/" + name + "\n" + currentID + "\n").getBytes());
                    algorithm = AlgorithmSet.getAlgorithm(currentID);
                    byte[] compression = algorithm.compress(fileBytes);
                    stream.write((compression.length + "\n").getBytes());
                    stream.write(compression);
                }
                else if(type.equals(".ppm")){
                    stream.write((prefix + "/" + name + "\n" + AlgorithmSet.JPEG_ID + "\n").getBytes());
                    algorithm = AlgorithmSet.getAlgorithm(AlgorithmSet.JPEG_ID);
                    byte[] compression = algorithm.compress(fileBytes);
                    stream.write((compression.length + "\n").getBytes());
                    stream.write(compression);
                }

            }
        }
        return stream;
    }


    public byte[] compressFiles(File[] files) throws IOException {
        ByteArrayOutputStream stream = recursiveCompressFile(files, "");
        return stream.toByteArray();
    }

    public void decompressFile(File file) {
        byte[] fileBytes = domainCtrl.readFile(file.getPath());
        int endLineCounter = 0;
        int i = 0;
        String fileName = "";
        String compressionType = "";
        int compressionSize = 0;
        int indexLastSlash = file.getPath().lastIndexOf("/");
        String prefix = file.getPath().substring(0, indexLastSlash);
        while(i < fileBytes.length){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if(endLineCounter == 0){ // read file name
                boolean isDir = false;
                while(fileBytes[i] != '\n'){
                    isDir = false;
                    stream.write(fileBytes[i]);
                    if(fileBytes[i] == '/'){ // if file is dir, create dir
                        File dir = new File(prefix + "/" + stream.toString());
                        if(!dir.exists())dir.mkdir();
                        isDir = true;
                    }
                    ++i;
                }
                if(!isDir){
                    endLineCounter += 1;
                }
                ++i;
                fileName = stream.toString();
                stream.reset();

            }
            else if(endLineCounter == 1){ // read compression type
                while(fileBytes[i] != '\n'){
                    stream.write(fileBytes[i]);
                    ++i;
                }
                endLineCounter += 1;
                ++i;
                compressionType = stream.toString();
                stream.reset();
            }
            else if(endLineCounter == 2){ // read compression size
                while(fileBytes[i] != '\n'){
                    stream.write(fileBytes[i]);
                    ++i;
                }
                ++i;
                endLineCounter += 1;
                compressionSize = Integer.parseInt(stream.toString());
                stream.reset();
            }
            else if(endLineCounter == 3){ // read compression
                int size = i + compressionSize;
                while(i < size){
                    stream.write(fileBytes[i]);
                    ++i;
                }
                endLineCounter = 0;
                byte[] compressedFile = stream.toByteArray();
                algorithm = AlgorithmSet.getAlgorithm(Integer.parseInt(compressionType));
                byte[] decompressedFile = algorithm.decompress(compressedFile);
                if(compressionType.equals("jpeg"))fileName += ".ppm";
                else fileName += ".txt";
                fileName = prefix  + "/" + fileName;
                domainCtrl.WriteFile(fileName, decompressedFile);
            }
        }
    }


    public String getFileDir(File file){
        int indexLastSlash = file.getPath().lastIndexOf("/");
        return file.getPath().substring(0, indexLastSlash) + "/";
    }

    public String getFileName(File file){
        int indexLastSlash = file.getPath().lastIndexOf("/");
        int indexLastDot = file.getPath().lastIndexOf(".");
        return file.getPath().substring(indexLastSlash, indexLastDot);
    }

    public void setAlgorithm(int id) {
        algorithm = AlgorithmSet.getAlgorithm(id);
        currentID = id;
    }
}
