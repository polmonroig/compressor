import domain.LZ78;
import persistencia.controllers.CtrlPersistencia;

public class Main {

    static public void main(String[] args){
        LZ78 compresor = new LZ78();
        byte[] s = CtrlPersistencia.ReadFileAsBytes("/home/pol/Documents/fib/PROP/compressor/src/file.txt");
        System.out.println("Original string: " + new String(s));
        byte[] code = compresor.comprimir(s);
        CtrlPersistencia.WriteBytesToFile("/home/pol/Documents/fib/PROP/compressor/src/out.txt", code);
        s = CtrlPersistencia.ReadFileAsBytes("/home/pol/Documents/fib/PROP/compressor/src/out.txt");
        System.out.println(new String(compresor.descomprimir(s)));

    }
}
