package ApplicazionePersonale;

public class Sessione {

	private final User utente;
	
	public Sessione(User utente) {
		 if (utente == null) {
		        throw new IllegalArgumentException("L'utente non può essere null");
		    }
	    this.utente = utente;
	}
	
	public User getUtente() {
	    return utente;
	}
}
