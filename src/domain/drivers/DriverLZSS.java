package domain.drivers;

import domain.LZSS;
import persistencia.controllers.DataCtrl;

import java.util.ArrayList;
import java.util.Scanner;

public class DriverLZSS {


    public static void main(String[] args) {
        Scanner Input = new Scanner(System.in);


        LZSS encoder = new LZSS();
        boolean input = false;

        while(!input){
            System.out.println("Que desea hacer?");
            System.out.println("1- comprimir\n2- descomprimir\n3- nada");
            String userInput = Input.nextLine();
            switch (userInput) {
                case "1":
                    TestCompression(encoder);
                    break;
                case "2":
                    TestDecompression(encoder);
                    break;
                case "3":
                    input = true;
                    break;
                default:
                    System.out.println("La opcion introducida no es valida, por favor introduce un numero:");
                    break;
            }
        }
    }

    private static void TestDecompression(LZSS encoder){
        System.out.println("Testing decompression...");
        String test_dir = System.getProperty("user.dir") + "/test_files/compressed";
        String decompression_dir =  System.getProperty("user.dir") + "/test_files/decompressed/";
        ArrayList<String> files = DataCtrl.getFilesFromDir(test_dir);
        for(int i = 0; i < files.size(); ++i){
            System.out.println("testing file " + i);
            String[] tmp = files.get(i).split("/");
            String filename = tmp[tmp.length - 1];
            System.out.println("filename: " + filename);
            byte[] current_file = DataCtrl.ReadFileAsBytes(files.get(i));
            byte[] compression = encoder.decompress(current_file);
            DataCtrl.WriteBytesToFile(decompression_dir + filename, compression);
        }
    }

    private static void TestCompression(LZSS encoder){
        System.out.println("Testing compression...");
        String test_dir = System.getProperty("user.dir") + "/test_files/original";
        String compression_dir =  System.getProperty("user.dir") + "/test_files/compressed/";
        ArrayList<String> files = DataCtrl.getFilesFromDir(test_dir);
        for(int i = 0; i < files.size(); ++i){
            System.out.println("testing file " + i);
            String[] tmp = files.get(i).split("/");
            String filename = tmp[tmp.length - 1];
            System.out.println("filename: " + filename);
            byte[] current_file = DataCtrl.ReadFileAsBytes(files.get(i));
            byte[] compression = encoder.compress(current_file);
            DataCtrl.WriteBytesToFile(compression_dir +  filename, compression);
        }
    }
}
