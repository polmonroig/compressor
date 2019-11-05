package domain.drivers;

import domain.LZ78;
import persistencia.controllers.CtrlPersistencia;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DriverLZ78 {


    public static void main(String[] args) {
        LZ78 encoder = DriverLZ78.TestConstructor(); // Test constructor
        DriverLZ78.TestCompression(encoder);
    }

    private static LZ78 TestConstructor(){
        System.out.println("Testing constructor...");
        LZ78 l = new LZ78();
        System.out.println("done.");
        return l;
    }

    private static void TestCompression(LZ78 encoder){
        System.out.println("Testing compression...");
        String test_dir = "/home/pol/Documents/fib/PROP/compressor/src/test_files/original";
        ArrayList<String> files = CtrlPersistencia.getFilesFromDir(test_dir);
        System.out.println("done.");
        int counter = 0;
        for(int i = 0; i < files.size(); ++i){
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
