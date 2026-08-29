package ApplicazionePersonale;

public class SerieWorkout {

    private int ripetizioniXSerie;

    private double kg;

    public SerieWorkout(int rep, double kg) {

        if (rep <= 0) {
            throw new IllegalArgumentException(
                    "Il numero di ripetizioni deve essere maggiore di zero");
        }

        this.ripetizioniXSerie = rep;

        if (kg < 0) {
            throw new IllegalArgumentException(
                    "I kg utilizzati devono essere almeno zero e cioè il peso corporeo");
        }

        this.kg = kg;
    }

    public int getRipetizioniXSerie() {
        return ripetizioniXSerie;
    }

    public void setRipetizioniXSerie(int ripetizioniXSerie) {

        this.ripetizioniXSerie = ripetizioniXSerie;
    }

    public double getKg() {
        return kg;
    }

    public void setKg(double kg) {

        this.kg = kg;
    }
}