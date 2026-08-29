package ApplicazionePersonale;

public class RunningWorkout extends Workout {

    private double distanza;

    public RunningWorkout(String id, String data, int durata, double distanza) {

        super(id, data,durata);

        if (distanza <= 0) {
            throw new IllegalArgumentException("La distanza deve essere maggiore di 0");
        }

        this.distanza = distanza;
    }

    public double getPassoMedio() {

        double passo = (double) getDurata() / distanza; // min/km

        return passo;
    }

    public double getDistanza() {
        return distanza;
    }

    public void setDistanza(double distanza) {

        if (distanza <= 0) {
            throw new IllegalArgumentException("La distanza deve essere maggiore di 0");
        }

        this.distanza = distanza;
    }
}
