package data.controllers;

import domain.controllers.DomainCtrl;

import java.io.*;


public class DataCtrl {
    private DomainCtrl domainCtrl;

    public DataCtrl(DomainCtrl controller){
        domainCtrl = controller;
    }

    static public byte[] ReadFileAsBytes(String fileDir){
        File file = new File(fileDir);
        FileInputStream fs = null;
        try{
            fs = new FileInputStream(file);
            byte [] content = new byte[(int)file.length()];
            int out = fs.read(content);
            fs.close();
            return content;
        }
        catch (FileNotFoundException e){
            System.out.println("File not found" + e);
        }
        catch (IOException e) {
            System.out.println("Exception while reading file " + e);
        }
        finally {
            try {
                if(fs != null){
                    fs.close();
                }
            }
            catch (IOException e){
                System.out.println("Error while closing stream: " + e);
            }
        }
        return null;
    }
    static public void WriteBytesToFile(String fileDir, byte [] text){
        File file = new File(fileDir);

        FileOutputStream fs = null;
        try {
            boolean b = file.createNewFile();
            fs = new FileOutputStream(file);
            fs.write(text);
        }
        catch (IOException e){
            System.out.println("Exception while writing file " + e);
        }
    }

    private ByteArrayOutputStream recursiveCompressFile(File[] files, String prefix) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        for (File file : files) {
            if(file.isDirectory()){
                stream.writeBytes((file.getName() + "/" + "\n").getBytes());
                ByteArrayOutputStream recursiveStream = recursiveCompressFile(file.listFiles(), file.getName());
                recursiveStream.writeTo(stream);
            }
            else{
                byte[] fileBytes = ReadFileAsBytes(file.getPath());

                int lastDotPos = file.getName().lastIndexOf('.');

                String name = file.getName().substring(0, lastDotPos);
                String type = file.getName().substring(lastDotPos);
                if (type.equals(".txt")){
                    stream.write((prefix + "/" + name + "\n" + "lzss" + "\n").getBytes());
                    byte[] compression = domainCtrl.compress("lzss", fileBytes);
                    stream.write((compression.length + "\n").getBytes());
                    stream.write(compression);
                }
                else if(type.equals(".ppm")){
                    stream.write((prefix + "/" + name + "\n" + "jpeg" + "\n").getBytes());
                    byte[] compression = domainCtrl.compress("jpeg", fileBytes);
                    stream.write((compression.length + "\n").getBytes());
                    stream.write(compression);
                }

            }
        }
        return stream;
    }


    public void compressFiles(File[] files) throws IOException {
        ByteArrayOutputStream stream = recursiveCompressFile(files, "");

        int indexLastSlash = files[0].getPath().lastIndexOf("/");

        WriteBytesToFile(files[0].getPath().substring(0, indexLastSlash) + "/" + "compression.mc", stream.toByteArray());

        System.out.println("Compression Done.!");
    }

    public void decompressFile(File file) {
        byte[] fileBytes = ReadFileAsBytes(file.getPath());

        String[] names = file.getPath().split("/");
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
                byte[] decompressedFile = domainCtrl.decompress(compressionType, compressedFile);
                if(compressionType.equals("jpeg"))fileName += ".ppm";
                else fileName += ".txt";
                fileName = prefix  + "/" + fileName;
                WriteBytesToFile(fileName, decompressedFile);
            }
        }
    }
}
