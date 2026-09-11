package ApplicazionePersonale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Exercise {

    private String nome;

    private List<SerieWorkout> serie;

    public Exercise(String nome) {
    	
    	if(nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome dell'esercizio non può essere vuoto");
        }
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
    	
    	if(nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome dell'esercizio non può essere vuoto");
        }
        this.nome = nome;
    }

    public List<SerieWorkout> getSerie() {
        return Collections.unmodifiableList(serie);
    }

   
}
