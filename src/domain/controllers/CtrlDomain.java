package domain.controllers;

import domain.*;
import persistencia.controllers.CtrlPersistencia;

public class CtrlDomain {
    private CtrlPersistencia controladorPersistencia;
    private LZ78 lz78;
    private LZSS lzss;
    private LZW lzw;
    private JPEG jpeg;
    private EstadisticaGlobal estadisticaGlobal;


    public CtrlDomain(){
        init();
    }

    private void init() {
        controladorPersistencia = new CtrlPersistencia();
        lz78 = new LZ78();
        lzss = new LZSS();
        lzw = new LZW();
        jpeg = new JPEG();
    }

    public void comprimir(String algoritmo, String archivo){
        byte[] file = CtrlPersistencia.ReadFileAsBytes(archivo);
        int lastPeriodPos = archivo.lastIndexOf('.');
        archivo = archivo.substring(0,lastPeriodPos);
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
            case "lzw": {
                byte[] compressed = lzw.comprimir(file);
                CtrlPersistencia.WriteBytesToFile(archivo + ".lzw", compressed);
                break;
            }
            case "jpeg": {
                byte[] compressed = jpeg.comprimir(file);
                CtrlPersistencia.WriteBytesToFile(archivo + ".jpeg", compressed);
                break;
            }
        }
    }

    public void descomprimir(String algoritmo, String archivo){
        byte[] file = CtrlPersistencia.ReadFileAsBytes(archivo);
        int lastPeriodPos = archivo.lastIndexOf('.');
        archivo = archivo.substring(0,lastPeriodPos);
        switch (algoritmo) {
            case "lz78": {
                byte[] decompressed = lz78.descomprimir(file);
                CtrlPersistencia.WriteBytesToFile(archivo + ".txt", decompressed);
                break;
            }
            case "lzss": {
                byte[] decompressed = lzss.descomprimir(file);
                CtrlPersistencia.WriteBytesToFile(archivo + ".txt", decompressed);
                break;
            }
            case "lzw": {
                byte[] decompressed = lzw.descomprimir(file);
                CtrlPersistencia.WriteBytesToFile(archivo + ".txt", decompressed);
                break;
            }
            case "jpeg": {
                byte[] decompressed = jpeg.descomprimir(file);
                CtrlPersistencia.WriteBytesToFile(archivo + ".ppm", decompressed);
                break;
            }
        }
    }

    public void comprimirDirectorio(String dir){

    }

    public void setCalidad(int parseInt) {
        jpeg.setCalidad(parseInt);
    }
}
