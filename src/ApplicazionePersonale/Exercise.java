package ApplicazionePersonale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Exercise {

    private String nome;

    private List<SerieWorkout> serie;

    public Exercise(String nome) {

        this.nome = nome;

        this.serie = new ArrayList<>();
    }

    public void aggiungiSerie(SerieWorkout serie) {

        this.serie.add(serie);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<SerieWorkout> getSerie() {
        return Collections.unmodifiableList(serie);
    }

   
}
