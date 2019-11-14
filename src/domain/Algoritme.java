package domain;

public abstract class Algoritme {



    protected EstadisticaLocal estadisticaLocal = new EstadisticaLocal();

    public int getOriginalSize(){
        return this.estadisticaLocal.getMidaArxiuInicial();
    }

    public int getCompressedSize(){ return this.estadisticaLocal.getMidaArxiuFinal(); }

    public float getCompressionRatio(){
        return this.estadisticaLocal.getGrauCompresio();
    }

    public abstract byte[] comprimir(byte[] texto);
    public abstract byte[] descomprimir(byte[] texto);


    public float getTempsCompressio(){
        return estadisticaLocal.getTiempoCompresio();
    }

    public float getVelocitatCompressio(){
        return estadisticaLocal.getVelocitatCompresio();
    }
}
