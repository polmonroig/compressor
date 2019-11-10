/*package domain.drivers;

import domain.LZSS;
import persistencia.controllers.CtrlPersistencia;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DriverLZSS {

    public static void main(String[] args){
        LZSS encoder = DriverLZSS.TestConstructor();
        DriverLZSS.TestCompression(encoder);
    }

    private static LZSS TestConstructor(){
        System.out.println("Testing constructor...");
        LZSS l = new LZSS();
        System.out.println("done.");
        return l;
    }

    private static void TestCompression(LZSS encoder){
        System.out.println("Testing compression...");
        String test_dir = "";
        ArrayList<String> files = CtrlPersistencia.getFilesFromDir(test_dir);
        int counter = 0;
        for (int i = 0; i < files.size(); ++i){
            System.out.println("testing file " + i);
            byte[] current_file = CtrlPersistencia.ReadFileAsBytes(files.get(i));
            byte[] compression = encoder.comprimir(current_file);
            byte[] decompression = encoder.descomprimir(compression);
            String a = new String(current_file);
            String b = new String(decompression);
            if(a.equals(b)){
                counter += 1;
                System.out.println("done.");
            }
            else {
                System.out.println("Error: incorrect compression!");
            }
        }
        System.out.println("Compression accuracy: " + counter + "/" + files.size());
    }
}
*/