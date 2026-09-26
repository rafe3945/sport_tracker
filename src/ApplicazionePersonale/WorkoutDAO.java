package ApplicazionePersonale;
import java.sql.Connection; 
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WorkoutDAO {

	public void save(Workout workout) throws SQLException {
		
		if (workout == null) {
	        throw new IllegalArgumentException("Workout non può essere vuoto");
	    }
		//salvataggio corsa nel database
		 if (workout instanceof RunningWorkout) {
			 RunningWorkout corsa = (RunningWorkout) workout;

		        try(Connection connection= DatabaseConnection.getConnection()) {
		        	String sql= "INSERT INTO corse(id, data, descrizione, durata, distanza) VALUES(?, ?, ?, ?, ?)";
		        	try(PreparedStatement statement= connection.prepareStatement(sql)){
		        	
		        	statement.setString(1, corsa.getId());
		        	statement.setDate(2, java.sql.Date.valueOf(corsa.getData()));
		        	statement.setString(3, corsa.getDescrizione());
		        	statement.setInt(4, corsa.getDurata());
		        	statement.setDouble(5, corsa.getDistanza());
		        	
		        	statement.executeUpdate();
		        	}
		        }
		        
		        //salvataggio allenmaneto_forza nel database
		    } else if (workout instanceof StrengtWorkout) {
		    	
		     StrengtWorkout forza = (StrengtWorkout) workout;
		     	try(Connection connection =DatabaseConnection.getConnection()){
		     		try {
		     		connection.setAutoCommit(false);
		     		String sql="INSERT INTO allenamenti_forza(id, data, descrizione, durata) VALUES(?, ?, ?, ?)";
		     		try(PreparedStatement statement= connection.prepareStatement(sql)){
		     		
		     		statement.setString(1, forza.getId());
		     		statement.setDate(2, java.sql.Date.valueOf(forza.getData()));
		     		statement.setString(3, forza.getDescrizione());
		     		statement.setInt(4, forza.getDurata());
		     		
		     		statement.executeUpdate();
		     		}
		     		for (Exercise esercizio : forza.getEsercizi()) {
		     			String sqlEsercizio = "INSERT INTO esercizi_forza (nome, allenamento_id) VALUES (?, ?)";
		     			try(PreparedStatement statementEsercizio= connection.prepareStatement(sqlEsercizio, Statement.RETURN_GENERATED_KEYS)){
		     			
		     			statementEsercizio.setString(1, esercizio.getNome());
		     			statementEsercizio.setString(2, forza.getId());
		     			statementEsercizio.executeUpdate();
		     			
		     			try(ResultSet generatedKeys = statementEsercizio.getGeneratedKeys()){
		     			
		     			if(generatedKeys.next()) {
		     				int esercizioId = generatedKeys.getInt(1);
		     				
		     				for(SerieWorkout serie : esercizio.getSerie()) {
		     					String sqlSerie= "INSERT INTO serie_forza(ripetizioni, durata_secondi, kg, esercizio_id, tipo) VALUES(?, ?, ?, ?, ?)";
		     					
		     					try(PreparedStatement statementSerie = connection.prepareStatement(sqlSerie)){
		     					
		     					statementSerie.setInt(1, serie.getRipetizioni());
		     					statementSerie.setInt(2, serie.getDurataSecondi());
		     					statementSerie.setDouble(3, serie.getKg());
		     					statementSerie.setInt(4, esercizioId);
		     					statementSerie.setString(5, serie.getTipo().name());
		     					
		     					statementSerie.executeUpdate();
		     					}
		     				}
		     			}
		     		}
		     	}
		     			
		     		}
		     		connection.commit();
		     		
		     	}catch (SQLException e) {
		     	    connection.rollback();
		     	    System.out.println("Errore durante il salvataggio dell'allenamento.");
		     	    e.printStackTrace();
		     	}
		      }
		    } else {
		        throw new IllegalArgumentException("Tipo di workout non supportato");
		    }
	}
	
}
