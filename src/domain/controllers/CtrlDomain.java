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

    public void comprimir(String archivo){

    }

    public void descomprimir(String archivo){

    }

    public void comprimirDirectorio(String dir){

    }

}
