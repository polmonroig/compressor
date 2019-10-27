import persistencia.controllers.CtrlPersistencia;

public class Main {

    static public void main(String[] args){
        LZ78 compresor = new LZ78();
        String s = CtrlPersistencia.ReadFileAsString("/home/pol/Documents/fib/PROP/compressor/src/file.txt");
        System.out.println("Original string: " + s);
        System.out.println(compresor.comprimir(s));

    }
}
