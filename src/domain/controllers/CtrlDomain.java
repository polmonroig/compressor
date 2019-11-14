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
                CtrlPersistencia.WriteBytesToFile(archivo + ".lz78", decompressed);
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


    public void setCalidad(int parseInt) {
        jpeg.setCalidad(parseInt);
    }

    public int getMidaArxiuInicial(String algorithm) {
        switch (algorithm) {
            case "lz78": {
                return lz78.getOriginalSize();
            }
            case "lzss": {
                return lzss.getOriginalSize();
            }
            case "lzw": {
                return lzw.getOriginalSize();
            }
            case "jpeg": {
                return jpeg.getOriginalSize();
            }
        }
        return 0;
    }

    public int getMidaArxiuFinal(String algorithm) {
        switch (algorithm) {
            case "lz78": {
                return lz78.getCompressedSize();
            }
            case "lzss": {
                return lzss.getCompressedSize();
            }
            case "lzw": {
                return lzw.getCompressedSize();
            }
            case "jpeg": {
                return jpeg.getCompressedSize();
            }
        }
        return 0;
    }

    public float getGrauCompressio(String algorithm) {
        switch (algorithm) {
            case "lz78": {
                return lz78.getCompressionRatio();
            }
            case "lzss": {
                return lzss.getCompressionRatio();
            }
            case "lzw": {
                return lzw.getCompressionRatio();
            }
            case "jpeg": {
                return jpeg.getCompressionRatio();
            }
        }
        return 0;
    }

    public float getTempsCompressio(String algorithm) {
        switch (algorithm) {
            case "lz78": {
                return lz78.getTempsCompressio();
            }
            case "lzss": {
                return lzss.getTempsCompressio();
            }
            case "lzw": {
                return lzw.getTempsCompressio();
            }
            case "jpeg": {
                return jpeg.getTempsCompressio();
            }
        }
        return 0;
    }

    public float getVelocitatCompressio(String algorithm) {
        switch (algorithm) {
            case "lz78": {
                return lz78.getVelocitatCompressio();
            }
            case "lzss": {
                return lzss.getVelocitatCompressio();
            }
            case "lzw": {
                return lzw.getVelocitatCompressio();
            }
            case "jpeg": {
                return jpeg.getVelocitatCompressio();
            }
        }
        return 0;
    }
}
