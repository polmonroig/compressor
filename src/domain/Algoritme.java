package domain;

public abstract class Algoritme {

    protected EstadisticaLocal estadisticaLocal;

    public int getOriginalSize(){
        return this.estadisticaLocal.getMidaArxiuInicial();
    }

    public int getCompressedSize(){ return this.estadisticaLocal.getMidaArxiuFinal(); }

    public float getCompression_ratio(){
        return this.estadisticaLocal.getGrauCompresio();
    }

    public abstract byte[] comprimir(byte[] texto);
    public abstract byte[] descomprimir(byte[] texto);

}
