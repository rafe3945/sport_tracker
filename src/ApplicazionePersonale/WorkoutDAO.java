package ApplicazionePersonale;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class WorkoutDAO {

	public void save(Workout workout) throws SQLException {
		
		if (workout == null) {
	        throw new IllegalArgumentException("Workout non può essere vuoto");
	    }
	
		 if (workout instanceof RunningWorkout) {
			 RunningWorkout corsa = (RunningWorkout) workout;

		        try(Connection connection= DatabaseConnection.getConnection()) {
		        	String sql= "INSERT INTO corse(id, data, descrizione, durata, distanza) VALUES(?, ?, ?, ?, ?)";
		        	PreparedStatement statement= connection.prepareStatement(sql);
		        	
		        	statement.setString(1, corsa.getId());
		        	statement.setDate(2, java.sql.Date.valueOf(corsa.getData()));
		        	statement.setString(3, corsa.getDescrizione());
		        	statement.setInt(4, corsa.getDurata());
		        	statement.setDouble(5, corsa.getDistanza());
		        	
		        	statement.executeUpdate();
		        }

		    } else if (workout instanceof StrengtWorkout) {
		     StrengtWorkout forza = (StrengtWorkout) workout;

		        // salvataggio ALLENAMENTI_FORZA
		        //              ↓
		        //       ESERCIZI_FORZA
		        //              ↓
		        //         SERIE_FORZA

		    } else {
		        throw new IllegalArgumentException("Tipo di workout non supportato");
		    }
	}
	
}
