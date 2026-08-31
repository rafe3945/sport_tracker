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
	
	public int getCorseTotali() {
		int count=0;
		
		for(Workout workout:workouts) {
			if(workout instanceof RunningWorkout) {
				count++;
			}
		}
		return count;
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
	
	public int getTempoTotaleForza() {
		int tempo=0;
		for(Workout workout:workouts) {
			if(workout instanceof StrengtWorkout) {
				tempo+=workout.getDurata();
			}
		}
		return tempo;
	}
}
