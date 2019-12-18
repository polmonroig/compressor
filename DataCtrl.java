package data.controllers;

import java.io.*;


public class DataCtrl {


    public byte[] read(File file) throws IOException{
        FileInputStream fs = null;
        fs = new FileInputStream(file);
        byte [] content = new byte[(int)file.length()];
        int out = fs.read(content);
        fs.close();
        return content;
    }
    public void write(String fileDir, byte [] text) throws IOException{
        File file = new File(fileDir);

        FileOutputStream fs = null;
        File parent = file.getParentFile();
        if(!parent.exists() && !parent.mkdirs()){
            throw new IllegalStateException("Exception when creating dir");
        }
        boolean b = file.createNewFile();
        fs = new FileOutputStream(file);
        fs.write(text);

    }
    
}
