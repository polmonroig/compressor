package domain.controllers;

import domain.LZ78;
import domain.LZSS;
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

    public void comprimir(String archivo){

    }

    public void descomprimir(String archivo){

    }

    public void comprimirDirectorio(String dir){

    }

}
