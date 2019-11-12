package domain.controllers;

import domain.*;
import persistencia.controllers.CtrlPersistencia;

public class CtrlDomain {
    private CtrlPersistencia controladorPersistencia;
    private LZ78 lz78;
    private LZSS lzss;
    private LZW lzw;
    private EstadisticaGlobal estadisticaGlobal;


    public CtrlDomain(){
        init();
    }

    private void init() {
        controladorPersistencia = new CtrlPersistencia();
        lz78 = new LZ78();
        lzss = new LZSS();
        lzw = new LZW();
    }

    public void comprimir(String algoritmo, String archivo){
        byte[] file = CtrlPersistencia.ReadFileAsBytes(archivo);
        switch (algoritmo) {
            case "lz78": {
                byte[] decompressed = lz78.comprimir(file);
                CtrlPersistencia.WriteBytesToFile(archivo, decompressed);
                break;
            }
            case "lzss": {
                byte[] compressed = lzss.comprimir(file);
                CtrlPersistencia.WriteBytesToFile(archivo + ".lzss", compressed);
                break;
            }
            case "lzw":
                byte[] compressed = lzw.comprimir(file);
                CtrlPersistencia.WriteBytesToFile(archivo + ".lzw", compressed);
                break;
        }
    }

    public void descomprimir(String algoritmo, String archivo){
        byte[] file = CtrlPersistencia.ReadFileAsBytes(archivo);
        switch (algoritmo) {
            case "lz78": {
                byte[] decompressed = lz78.descomprimir(file);
                CtrlPersistencia.WriteBytesToFile(archivo, decompressed);
                break;
            }
            case "lzss": {
                byte[] compressed = lzss.descomprimir(file);
                CtrlPersistencia.WriteBytesToFile(archivo + ".lzss", compressed);
                break;
            }
            case "lzw":
                byte[] compressed = lzw.descomprimir(file);
                CtrlPersistencia.WriteBytesToFile(archivo + ".lzw", compressed);
                break;
        }
    }

    public void comprimirDirectorio(String dir){

    }

}
