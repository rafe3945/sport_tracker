package ApplicazionePersonale;

public class SerieWorkout {

	private int ripetizioni;
	private int durataSecondi;
    private double kg;
    private TipoSerie tipo;

    public SerieWorkout(int ripetizioni,int durata,  double kg, TipoSerie tipo) {

        if (ripetizioni <= 0) {
            throw new IllegalArgumentException(
                    "Il numero di ripetizioni deve essere maggiore di zero");
        }

        this.ripetizioni=ripetizioni;
        
        if(durata<0) {
        	throw new IllegalArgumentException("La durata della serie non può essere negativa");
        }
        this.durataSecondi=durata;
        
        if(tipo == null) {
            throw new IllegalArgumentException("Il tipo della serie non può essere nullo");
        }
        this.tipo=tipo;
        
        if (kg < 0) {
            throw new IllegalArgumentException(
                    "I kg utilizzati devono essere almeno zero e cioè il peso corporeo");
        }

        this.kg = kg;
    }    
    
    public double getKg() {
        return kg;
    }

    public void setKg(double kg) {
    	
    	 if (kg < 0) {
             throw new IllegalArgumentException(
                     "I kg utilizzati devono essere almeno zero e cioè il peso corporeo");
         }
        this.kg = kg;
    }
    
    public int getRipetizioni() {
        return ripetizioni;
    }

    public void setRipetizioni(int ripetizioni) {

        if(ripetizioni <= 0) {
            throw new IllegalArgumentException("Le ripetizioni devono essere maggiori di zero");
        }

        this.ripetizioni = ripetizioni;
    }

    public int getDurataSecondi() {
        return durataSecondi;
    }

    public void setDurataSecondi(int durataSecondi) {

        if(durataSecondi < 0) {
            throw new IllegalArgumentException("La durata non può essere negativa");
        }

        this.durataSecondi = durataSecondi;
    }
    public TipoSerie getTipo() {
    	return this.tipo;
    }
    
    public void setTipo(TipoSerie tipo) {

        if(tipo == null) {
            throw new IllegalArgumentException("Il tipo della serie non può essere nullo");
        }

        this.tipo = tipo;
    }
   
    @Override
    public String toString() {

        if(tipo == TipoSerie.RIPETIZIONI) {

            return "Ripetizioni: " + ripetizioni + ", Kg: " + kg;

        } else if(tipo == TipoSerie.TEMPO) {

            return "Durata: " + durataSecondi + " secondi, Kg: " + kg;
        } else {
            return "Ripetizioni: " + ripetizioni + ", durata per ripetizione: " + durataSecondi + " secondi, Kg: " + kg;
        }
    }
}