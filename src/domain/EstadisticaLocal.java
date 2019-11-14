package domain;

public class EstadisticaLocal extends  Estadistica {
    private int midaArxiuInicial;
    private int midaArxiuFinal;


    public void reset(){
        midaArxiuFinal = 0;
        midaArxiuFinal = 0;
        setVelocitatCompresio(0);
        setTiempoCompresio(0);
        setGrauCompresio(0);
    };

    public int getMidaArxiuInicial() {
        return midaArxiuInicial;
    }

    public void setMidaArxiuInicial(int midaArxiuInicial) {
        this.midaArxiuInicial = midaArxiuInicial;
    }

    public int getMidaArxiuFinal() {
        return midaArxiuFinal;
    }

    public void setMidaArxiuFinal(int midaArxiuFinal) {
        this.midaArxiuFinal = midaArxiuFinal;
    }


}
