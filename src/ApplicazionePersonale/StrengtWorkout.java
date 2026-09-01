package ApplicazionePersonale;

	import java.time.LocalDate;
	import java.util.List;
	import java.util.Collections;
	import java.util.ArrayList;

	public class StrengtWorkout extends Workout{
		
		private	List<Exercise> esercizi;

		public StrengtWorkout(String id, LocalDate data, int durata) {
			super(id, data, durata);
			this.esercizi=new ArrayList<>();
			
		}
			
		public void aggiungiEsercizio(Exercise esercizio) {
			this.esercizi.add(esercizio);
		}
		
		
		public List<Exercise> getEsercizi(){
			return Collections.unmodifiableList(esercizi);
		}
}
