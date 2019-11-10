package domain.controllers;

import domain.LZ78;

import persistencia.controllers.CtrlPersistencia;

public class CtrlDomain {
    private CtrlPersistencia controladorPersistencia;
    private LZ78 lz78;


    public CtrlDomain(){
        init();
    }

    private void init() {
        controladorPersistencia = new CtrlPersistencia();
        lz78 = new LZ78();

    }

    public void comprimir(String algoritmo, String archivo){
        if(algoritmo == "lz78"){
            byte[] file = CtrlPersistencia.ReadFileAsBytes(archivo);
            byte[] compressed = lz78.comprimir(file);
            CtrlPersistencia.WriteBytesToFile(archivo + ".lz78", compressed);
        }

    }

    public void descomprimir(String algoritmo, String archivo){
        if(algoritmo == "lz78"){
            byte[] file = CtrlPersistencia.ReadFileAsBytes(archivo);
            byte[] decompressed = lz78.descomprimir(file);
            CtrlPersistencia.WriteBytesToFile(archivo , decompressed);
        }
    }

    public void comprimirDirectorio(String dir){

    }

}
