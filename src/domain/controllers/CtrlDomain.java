package domain.controllers;

import domain.*;
import persistencia.controllers.CtrlPersistencia;

public class CtrlDomain {
    private CtrlPersistencia controladorPersistencia;
    private LZ78 lz78;
    private LZSS lzss;


    public CtrlDomain(){
        init();
    }

    private void init() {
        controladorPersistencia = new CtrlPersistencia();
        lz78 = new LZ78();
        lzss = new LZSS();
    }

    public void comprimir(String algoritmo, String archivo){
        if(algoritmo == "lz78"){
            byte[] file = CtrlPersistencia.ReadFileAsBytes(archivo);
            byte[] compressed = lz78.comprimir(file);
            CtrlPersistencia.WriteBytesToFile(archivo + ".lz78", compressed);
        }
        else if(algoritmo == "lzss"){
            byte[] file = CtrlPersistencia.ReadFileAsBytes(archivo);
            byte[] compressed = lzss.comprimir(file);
            CtrlPersistencia.WriteBytesToFile(archivo + ".lzss", compressed);
        }
    }

    public void descomprimir(String algoritmo, String archivo){
        if(algoritmo == "lz78"){
            byte[] file = CtrlPersistencia.ReadFileAsBytes(archivo);
            byte[] decompressed = lz78.descomprimir(file);
            CtrlPersistencia.WriteBytesToFile(archivo , decompressed);
        }
        else if(algoritmo == "lzss"){
            byte[] file = CtrlPersistencia.ReadFileAsBytes(archivo);
            byte[] compressed = lzss.comprimir(file);
            CtrlPersistencia.WriteBytesToFile(archivo + ".lzss", compressed);
        }
    }

    public void comprimirDirectorio(String dir){

    }

}
