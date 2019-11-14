package domain;

public abstract class Estadistica {
    private float tiempoCompresio;
    private float grauCompresio;
    private  float velocitatCompresio;


    public float getTiempoCompresio() {
        return tiempoCompresio;
    }

    public void setTiempoCompresio(float tiempoCompresio) {
        this.tiempoCompresio = tiempoCompresio;
    }

    public float getGrauCompresio() {
        return grauCompresio;
    }

    public void setGrauCompresio(float grauCompresio) {
        this.grauCompresio = grauCompresio;
    }

    public float getVelocitatCompresio() {
        return velocitatCompresio;
    }

    public void setVelocitatCompresio(float velocitatCompresio) {
        this.velocitatCompresio = velocitatCompresio;
    }




}
