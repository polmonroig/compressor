import domain.LZ78;
import persistencia.controllers.CtrlPersistencia;

public class Main {

    static public void main(String[] args){
        LZ78 compresor = new LZ78();
        byte[] s = CtrlPersistencia.ReadFileAsBytes("/home/pol/Documents/fib/PROP/compressor/src/test_files/original/quijote_largo.txt");
        byte[] code = compresor.comprimir(s);
        System.out.println("compressed done.");
        CtrlPersistencia.WriteBytesToFile("/home/pol/Documents/fib/PROP/compressor/src/test_files/compressed/quijote_largo.txt", code);
        byte[]s2  = CtrlPersistencia.ReadFileAsBytes("/home/pol/Documents/fib/PROP/compressor/src/test_files/compressed/quijote_largo.txt");
        byte[] code2 = compresor.descomprimir(s2);
        String c = new String(code2);

        System.out.println("decompressed done.");
        CtrlPersistencia.WriteBytesToFile("/home/pol/Documents/fib/PROP/compressor/src/test_files/decompressed/quijote_largo.txt", code2);
        String c2 = new String(s);
        if(c.equals(c2)){
            System.out.println("OUT == ORIGINAL");
        }
        System.out.println();
    }
}
