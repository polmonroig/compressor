package data.controllers;

import domain.controllers.DomainCtrl;

import java.io.*;


public class DataCtrl {


    public byte[] ReadFile(File file){
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
    public void WriteFile(String fileDir, byte [] text){
        File file = new File(fileDir);

        FileOutputStream fs = null;
        try {
            File parent = file.getParentFile();
            if(!parent.exists() && !parent.mkdirs()){
                throw new IllegalStateException("Exception when creating dir");
            }
            boolean b = file.createNewFile();
            fs = new FileOutputStream(file);
            fs.write(text);
        }
        catch (IOException e){
            System.out.println("Exception while writing file " + e);
        }
    }

    public void makeDir(File file){
        file.mkdir();
    }


}
