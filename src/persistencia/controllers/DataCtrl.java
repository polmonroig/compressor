package persistencia.controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class DataCtrl {
    static public String ReadFileAsString(String fileDir){
        byte [] content = DataCtrl.ReadFileAsBytes(fileDir);
        if(content != null){
            return new String(content);
        }
        return "";
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

    static public void WriteStringToFile(String fileDir, String text){
        DataCtrl.WriteBytesToFile(fileDir, text.getBytes());
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

    public static ArrayList<String> getFilesFromDir(String dir) {
        ArrayList<String> files = new ArrayList<>();
        File folder = new File(dir);
        try{
            for(final File file: Objects.requireNonNull(folder.listFiles())){
                files.add(dir + "/" + file.getName());
            }
        }
        catch (NullPointerException e){
            System.out.println("Directory not found" + e);
        }
        return files;
    }
}
