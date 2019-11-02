import domain.LZ78;
import domain.LZSS;
import persistencia.controllers.CtrlPersistencia;

public class Main {

    static public void main(String[] args){

       LZ78 compresor = new LZ78();
        String fileName = "empty.txt";
        byte[] s = CtrlPersistencia.ReadFileAsBytes("/home/pol/Documents/fib/PROP/compressor/src/test_files/original/" + fileName);
        byte[] code = compresor.comprimir(s);
        System.out.println("compressed done.");
        CtrlPersistencia.WriteBytesToFile("/home/pol/Documents/fib/PROP/compressor/src/test_files/compressed/" + fileName, code);
        byte[]s2  = CtrlPersistencia.ReadFileAsBytes("/home/pol/Documents/fib/PROP/compressor/src/test_files/compressed/" + fileName);
        byte[] code2 = compresor.descomprimir(s2);
        String c = new String(code2);

        System.out.println("decompressed done.");
        CtrlPersistencia.WriteBytesToFile("/home/pol/Documents/fib/PROP/compressor/src/test_files/decompressed/" + fileName, code2);
        String c2 = new String(s);
        if(c.equals(c2)){
            System.out.println("OUT == ORIGINAL");
        }
        System.out.println();
    }
}
