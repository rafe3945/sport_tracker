package ApplicazionePersonale;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class WorkoutManager {
	
	private List<Workout> workouts;

	public WorkoutManager() {
		this.workouts=new ArrayList<>();
	}
	
	public void aggiungiWorkout(Workout workout) {
		this.workouts.add(workout);
	}

	public List<Workout> getWorkouts() {
		return Collections.unmodifiableList(workouts);
	}
	
	public int getAllenamentiTotali() {
		return workouts.size();
	}
	
	
	public double getKmTotali() {
		double kmCorsi=0;
		
		for(Workout workout:workouts) {
			if(workout instanceof RunningWorkout) {
				RunningWorkout corsa= (RunningWorkout)workout;
				kmCorsi+=corsa.getDistanza();
			}
		}
		return kmCorsi;
	}
	
	public int getTempoTotaleCorsa() {
		int tempo=0;
		for(Workout workout:workouts) {
			if(workout instanceof RunningWorkout) {
				tempo+=workout.getDurata();
			}
		}
		return tempo;
	}
	
	public int getNumeroCorse() {
		int numCorse=0;
		for(Workout workout:workouts) {
			if(workout instanceof RunningWorkout) {
				numCorse++;
			}
		}
		return numCorse;
	}
	
	public double getPassoMedioTotale() {
		double distanza=0;
		int durata=0;
		for(Workout workout: workouts) {
			if(workout instanceof RunningWorkout) {
				distanza+=((RunningWorkout)workout).getDistanza();
				durata+=workout.getDurata();
			}
		}
		if(distanza == 0) {
		    return 0;
		}

		double passo=durata/distanza; // min/km
		return passo;
	}
	
	public int getTempoTotaleForza() {
		int tempo=0;
		for(Workout workout:workouts) {
			if(workout instanceof StrengtWorkout) {
				tempo+=workout.getDurata();
			}
		}
		return tempo;
	}
	
	public int getNumeroAllenamentiForza() {
		int allenamenti=0;
		for(Workout workout:workouts) {
			if(workout instanceof StrengtWorkout) {
				allenamenti++;
			}
		}
		return allenamenti;
	}
	
	public int getNumeroEserciziForza() {
		int numEsercizi=0;
		
		for(Workout workout:workouts) {
			if(workout instanceof StrengtWorkout) {
				numEsercizi+=((StrengtWorkout)workout).getEsercizi().size();
			}
		}
		return numEsercizi;
	}
	
	public int getNumeroSerieEsercizio(String nomeEsercizio) {
	    int numSerieEsercizio=0;
	    
	    for(Workout workout:workouts) {
	    	if(workout instanceof StrengtWorkout){
	    		for(Exercise exercise:((StrengtWorkout)workout).getEsercizi()) {
	    			if(nomeEsercizio.equalsIgnoreCase(exercise.getNome())) {
	    				numSerieEsercizio+=exercise.getSerie().size();
	    			}
	    		}
	    	}
	    }
	    return numSerieEsercizio;  
	}
	
	public String getEsercizioPiuRep() {
		
		int ripetizioniEsercizio=0;
		int maxRipetizioni=0;
		String nomeEsPiuRep = null;
		for(Workout workout:workouts) {
			if(workout instanceof StrengtWorkout) {
				
				for(Exercise es:((StrengtWorkout) workout).getEsercizi()) {
					ripetizioniEsercizio=0;
					for(SerieWorkout serie: es.getSerie()) {
						ripetizioniEsercizio+=serie.getRipetizioniXSerie();
					}
					if(ripetizioniEsercizio>maxRipetizioni) {
						maxRipetizioni=ripetizioniEsercizio;
						nomeEsPiuRep=es.getNome();
					}
				}
			}
		}
		return nomeEsPiuRep;
	}
}
