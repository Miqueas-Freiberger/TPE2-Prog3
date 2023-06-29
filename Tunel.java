public class Tunel {
    private String estacionOrigen;
    private String estacionDestino;
    private int distancia;

    public Tunel(String estacionOrigen, String estacionDestino, int distancia) {
        this.estacionOrigen = estacionOrigen;
        this.estacionDestino = estacionDestino;
        this.distancia = distancia;
    }

    public String getEstacionOrigen() {
        return estacionOrigen;
    }

    public String getEstacionDestino() {
        return estacionDestino;
    }

    public int getDistancia() {
        return distancia;
    }
}