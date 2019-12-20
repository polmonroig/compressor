
package data.controllers;

import java.io.*;

/**
 * This is the controller for the data layer that maneges all the reads and writes of the files
 * **/

public class DataCtrl {

    /**
     * <p>Given a File this function give an Array of bytes with the content of the file</p>
     * @param file were the file is save (Class File)
     * @return an Array of bytes with the content of the file
     * **/

    public byte[] read(File file) throws IOException{
        FileInputStream fs = null;
        fs = new FileInputStream(file);
        byte [] content = new byte[(int)file.length()];
        int out = fs.read(content);
        fs.close();
        return content;
    }

    /**
     * <p>Given a path and a text this function writes the content of the text to a path</p>
     * @param fileDir path of the directory to write the text
     * @param text Array of bytes given to write
     * **/

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
