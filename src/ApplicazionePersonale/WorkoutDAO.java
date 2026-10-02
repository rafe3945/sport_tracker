package ApplicazionePersonale;
import java.sql.Connection; 
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

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
	
	public int getNumeroCorse() throws SQLException{
		String sql= "SELECT COUNT(*) FROM corse ";
		try(Connection connection=DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				try(ResultSet result= statement.executeQuery()){
					result.next();
					return result.getInt(1);
				}	
			}
		}
	}
	
	public double getKmTotali() throws SQLException {
		String sql ="SELECT SUM(distanza) FROM corse";
		try(Connection connection = DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				try(ResultSet result = statement.executeQuery()){
					result.next();
					return result.getDouble(1);
				}
			}
		}
	}
	
	public int getTempoTotaleCorsa() throws SQLException {
		String sql="SELECT SUM(durata) FROM corse";
		
		try(Connection connection = DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				try(ResultSet result = statement.executeQuery()){
					result.next();
					return result.getInt(1);
				}
			}
		}
	}
	
	public double getPassoMedioTotale() throws SQLException{
		String sql= "SELECT SUM(durata) / SUM(distanza) FROM corse";
		
		try(Connection connection = DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				try(ResultSet result = statement.executeQuery()){
					result.next();
					return result.getDouble(1);
				}
			}
		}
	}
	
	public int getNumeroAllenamentiForza() throws SQLException {
		String sql="SELECT COUNT(*) FROM allenamenti_forza";
		
		try(Connection connection = DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				try(ResultSet result = statement.executeQuery()){
					result.next();
					return result.getInt(1);
				}
			}
		}
	}
	
	public int getTempoTotaleForza() throws SQLException{
		String sql="SELECT SUM(durata) FROM allenamenti_forza";
		
		try(Connection connection = DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				try(ResultSet result = statement.executeQuery()){
					result.next();
					return result.getInt(1);
				}
			}
		}
	}
	
	public int getNumeroEserciziForza() throws SQLException{
		
		String sql="SELECT COUNT(*) FROM esercizi_forza";
		
		try(Connection connection = DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				try(ResultSet result = statement.executeQuery()){
					result.next();
					return result.getInt(1);
				}
			}
		}
	}
	
	public String getEsercizioPiuRep() throws SQLException{
		
		String sql= "SELECT esercizi_forza.nome, SUM(serie_forza.ripetizioni) "
				+ "FROM esercizi_forza "
				+ "JOIN serie_forza "
				+ "ON esercizi_forza.id=serie_forza.esercizio_id "
				+ "GROUP BY esercizi_forza.nome "
				+ "ORDER BY SUM(serie_forza.ripetizioni) DESC "
				+ "LIMIT 1 ";
			
			try(Connection connection= DatabaseConnection.getConnection()){
				try(PreparedStatement statement=connection.prepareStatement(sql)){
					try(ResultSet result= statement.executeQuery()){
						if(result.next()) {
							return result.getString(1);
						}else {
							return null;
						}
					}
				}
			}	
	    }
	
	
	public int getMaxRipetizioni() throws SQLException {
		String sql= "SELECT esercizi_forza.nome, SUM(serie_forza.ripetizioni) "
				+ "FROM esercizi_forza "
				+ "JOIN serie_forza "
				+ "ON esercizi_forza.id=serie_forza.esercizio_id "
				+ "GROUP BY esercizi_forza.nome "
				+ "ORDER BY SUM(serie_forza.ripetizioni) DESC "
				+ "LIMIT 1 ";
		try(Connection connection= DatabaseConnection.getConnection()){
			try(PreparedStatement statement=connection.prepareStatement(sql)){
				try(ResultSet result= statement.executeQuery()){
					 if(result.next()) {
						return result.getInt(2);
					 }else {
						 return 0;
					 }
				}
			}
		}	
	}
	
	public int getNumeroSerieEsercizio(String nomeEs) throws SQLException {
		
		String sql= "SELECT COUNT(*) "
				+   "FROM esercizi_forza "
				+   "JOIN serie_forza "
				+   "ON esercizi_forza.id=serie_forza.esercizio_id "
				+   "WHERE esercizi_forza.nome = ? ";
		
		try(Connection connection= DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				statement.setString(1, nomeEs);
				try(ResultSet result= statement.executeQuery()){
					if(result.next()) {
					return result.getInt(1);
					}else {
						return 0;
					}
				}	
			}
		}	
	}
	
	public int getNumeroRipetizioniEsercizio(String nomeEs) throws SQLException {
		
		if(nomeEs == null || nomeEs.isBlank()) {
		    throw new IllegalArgumentException("Il nome dell'esercizio non può essere vuoto");
		}
		
		String sql= "SELECT SUM(serie_forza.ripetizioni) "
				+   "FROM esercizi_forza "
				+   "JOIN serie_forza "
				+   "ON esercizi_forza.id= serie_forza.esercizio_id "
				+   "WHERE esercizi_forza.nome= ? "
				+   "AND serie_forza.tipo IN ('RIPETIZIONI', 'RIPETIZIONI_CON_TEMPO') ";
		
		try(Connection connection = DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				statement.setString(1, nomeEs);
				try(ResultSet result = statement.executeQuery()){
					result.next(); 
					return result.getInt(1);
				}
			}
		}	
	}
	
	public int getTempoTotaleEsercizio(String nomeEs) throws SQLException {
		
		if(nomeEs == null || nomeEs.isBlank()) {
		    throw new IllegalArgumentException("Il nome dell'esercizio non può essere vuoto");
		}
		
		String sql= "SELECT SUM(serie_forza.durata_secondi) "
				+   "FROM esercizi_forza "
				+   "JOIN serie_forza "
				+   "ON esercizi_forza.id= serie_forza.esercizio_id "
				+   "WHERE esercizi_forza.nome= ? "
				+   "AND serie_forza.tipo= 'TEMPO' ";
		
		try(Connection connection = DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				statement.setString(1, nomeEs);
				try(ResultSet result = statement.executeQuery()){
					result.next(); 
					return result.getInt(1);
				}
			}
		}	
	}
	
	public List<Workout> getAllenamenti() throws SQLException {
		
		List<Workout> workouts= new ArrayList<>();
		
		String sqlCorse=  "SELECT id, data, descrizione, durata, distanza "
				       +  "FROM corse ";
		
		try(Connection connection = DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sqlCorse)){
				try(ResultSet result = statement.executeQuery()){
					while(result.next()) {
						String id=result.getString(1);
						LocalDate data=result.getDate("data").toLocalDate();
						String descrizione= result.getString(3);
						int durata= result.getInt(4);
						double distanza=result.getDouble(5);
						
						RunningWorkout corsa = new RunningWorkout(descrizione, id, data, durata, distanza);
						workouts.add(corsa);
					} 
				}
			}
		}	
		return workouts;
	}
	
}
