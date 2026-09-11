package ApplicazionePersonale;
import java.time.LocalDate;

public class Workout {

    private String id;

    private LocalDate data;
   
    private int durata; // in minuti
    
    private String descrizione; 
    
    public Workout(String descrizione,String id, LocalDate data, int durata) {
    	
        this.id = id;
        this.data = data;
        
        if (durata <= 0) {
            throw new IllegalArgumentException("La durata deve essere maggiore di 0");
        }

        this.durata = durata;
        
        if(descrizione == null) {
            this.descrizione = "";
        } else {
            this.descrizione = descrizione;
        }
        
    }
    	

    public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		 if(descrizione == null) {
		        this.descrizione = "";
		    } else {
		        this.descrizione = descrizione;
		    }
	}


	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {

        if (durata <= 0) {
            throw new IllegalArgumentException("La durata deve essere maggiore di 0");
        }

        this.durata = durata;
    }
}
