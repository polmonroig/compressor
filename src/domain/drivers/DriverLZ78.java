package domain.drivers;

import domain.LZ78;
import persistencia.controllers.CtrlPersistencia;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class DriverLZ78 {


    public static void main(String[] args) {
        Scanner Input = new Scanner(System.in);


        LZ78 encoder = new LZ78();
        boolean input = false;

        while(!input){
            System.out.println("What do you want to do?");
            String userInput = Input.nextLine();
            switch (userInput.toLowerCase().trim()) {
                case "compress":
                    TestCompression(encoder);
                    break;
                case "decompress":
                    TestDecompression(encoder);
                    break;
                case "nothing":
                    input = true;
                    break;
                default:
                    System.out.println("Please retry and make sure spelling is correct");
                    break;
            }
        }
    }

    private static void TestDecompression(LZ78 encoder){
        System.out.println("Testing decompression...");
        String test_dir = System.getProperty("user.dir") + "/test_files/compressed";
        String decompression_dir =  System.getProperty("user.dir") + "/test_files/decompressed/";
        ArrayList<String> files = CtrlPersistencia.getFilesFromDir(test_dir);
        for(int i = 0; i < files.size(); ++i){
            System.out.println("testing file " + i);
            String[] tmp = files.get(i).split("/");
            String filename = tmp[tmp.length - 1];
            System.out.println("filename: " + filename);
            byte[] current_file = CtrlPersistencia.ReadFileAsBytes(files.get(i));
            byte[] compression = encoder.descomprimir(current_file);
            CtrlPersistencia.WriteBytesToFile(decompression_dir + filename, compression);
        }
    }

    private static void TestCompression(LZ78 encoder){
        System.out.println("Testing compression...");
        String test_dir = System.getProperty("user.dir") + "/test_files/original";
        String compression_dir =  System.getProperty("user.dir") + "/test_files/compressed/";
        ArrayList<String> files = CtrlPersistencia.getFilesFromDir(test_dir);
        for(int i = 0; i < files.size(); ++i){
            System.out.println("testing file " + i);
            String[] tmp = files.get(i).split("/");
            String filename = tmp[tmp.length - 1];
            System.out.println("filename: " + filename);
            byte[] current_file = CtrlPersistencia.ReadFileAsBytes(files.get(i));
            byte[] compression = encoder.comprimir(current_file);
            CtrlPersistencia.WriteBytesToFile(compression_dir +  filename, compression);
        }
    }
}
