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

	public void save(Workout workout, int utenteId) throws SQLException {
		
		if (workout == null) {
	        throw new IllegalArgumentException("Workout non può essere vuoto");
	    }
		//salvataggio corsa nel database
		 if (workout instanceof RunningWorkout) {
			 RunningWorkout corsa = (RunningWorkout) workout;

		        try(Connection connection= DatabaseConnection.getConnection()) {
		        	String sql= "INSERT INTO corse(id, data, descrizione, durata, distanza, utente_id) VALUES(?, ?, ?, ?, ?, ?)";
		        	try(PreparedStatement statement= connection.prepareStatement(sql)){
		        	
		        	statement.setString(1, corsa.getId());
		        	statement.setDate(2, java.sql.Date.valueOf(corsa.getData()));
		        	statement.setString(3, corsa.getDescrizione());
		        	statement.setInt(4, corsa.getDurata());
		        	statement.setDouble(5, corsa.getDistanza());
		        	statement.setInt(6, utenteId);
		        	
		        	statement.executeUpdate();
		        	}
		        }
		        
		        //salvataggio allenmaneto_forza nel database
		    } else if (workout instanceof StrengtWorkout) {
		    	
		     StrengtWorkout forza = (StrengtWorkout) workout;
		     	try(Connection connection =DatabaseConnection.getConnection()){
		     		try {
		     		connection.setAutoCommit(false);
		     		String sql="INSERT INTO allenamenti_forza(id, data, descrizione, durata, utente_id) VALUES(?, ?, ?, ?,?)";
		     		try(PreparedStatement statement= connection.prepareStatement(sql)){
		     		
		     		statement.setString(1, forza.getId());
		     		statement.setDate(2, java.sql.Date.valueOf(forza.getData()));
		     		statement.setString(3, forza.getDescrizione());
		     		statement.setInt(4, forza.getDurata());
		     		statement.setInt(5, utenteId);
		     		
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
	
	public int getNumeroCorse(int utenteId) throws SQLException{
		String sql= "SELECT COUNT(*) FROM corse WHERE utente_id=? ";
		try(Connection connection=DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				statement.setInt(1, utenteId);
				try(ResultSet result= statement.executeQuery()){
					result.next();
					return result.getInt(1);
				}	
			}
		}
	}
	
	public double getKmTotali(int utenteId) throws SQLException {
		String sql ="SELECT SUM(distanza) FROM corse WHERE utente_id=? ";
		try(Connection connection = DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				statement.setInt(1, utenteId);
				try(ResultSet result = statement.executeQuery()){
					result.next();
					return result.getDouble(1);
				}
			}
		}
	}
	
	public int getTempoTotaleCorsa(int utenteId) throws SQLException {
		String sql="SELECT SUM(durata) FROM corse WHERE utente_id=? ";
		
		try(Connection connection = DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				statement.setInt(1,utenteId);
				try(ResultSet result = statement.executeQuery()){
					result.next();
					return result.getInt(1);
				}
			}
		}
	}
	
	public double getPassoMedioTotale(int utenteId) throws SQLException{
		String sql = "SELECT SUM(durata) / SUM(distanza) "
		           + "FROM corse "
		           + "WHERE utente_id = ?";
		
		try(Connection connection = DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				statement.setInt(1, utenteId);
				try(ResultSet result = statement.executeQuery()){
					result.next();
					return result.getDouble(1);
				}
			}
		}
	}
	
	public int getNumeroAllenamentiForza(int utenteId) throws SQLException {
		String sql="SELECT COUNT(*) FROM allenamenti_forza WHERE utente_id=? ";
		
		try(Connection connection = DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				statement.setInt(1, utenteId);
				try(ResultSet result = statement.executeQuery()){
					result.next();
					return result.getInt(1);
				}
			}
		}
	}
	
	public int getTempoTotaleForza(int utenteId) throws SQLException{
		String sql="SELECT SUM(durata) FROM allenamenti_forza WHERE utente_id=? ";
		
		try(Connection connection = DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				statement.setInt(1, utenteId);
				try(ResultSet result = statement.executeQuery()){
					result.next();
					return result.getInt(1);
				}
			}
		}
	}
	
	public int getNumeroEserciziForza(int utenteId) throws SQLException{
		
		String sql=  "SELECT COUNT(*) "
		           + "FROM esercizi_forza esFor "
		           + "JOIN allenamenti_forza allFor ON esFor.allenamento_id = allFor.id "
		           + "WHERE allFor.utente_id = ?";
		
		try(Connection connection = DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				statement.setInt(1, utenteId);
				try(ResultSet result = statement.executeQuery()){
					result.next();
					return result.getInt(1);
				}
			}
		}
	}
	
	public String getEsercizioPiuRep(int utenteId) throws SQLException{
		
		String sql= "SELECT esFor.nome, SUM(serFor.ripetizioni) "
				+ "FROM esercizi_forza esFor "
				+ "JOIN serie_forza serFor ON esFor.id= serFor.esercizio_id "
				+ "JOIN allenamenti_forza allFor ON esFor.allenamento_id = allFor.id "
				+ "WHERE allFor.utente_id = ? "
				+ "GROUP BY esFor.nome "
				+ "ORDER BY SUM(serFor.ripetizioni) DESC "
				+ "LIMIT 1 ";
			
			try(Connection connection= DatabaseConnection.getConnection()){
				try(PreparedStatement statement=connection.prepareStatement(sql)){
					statement.setInt(1, utenteId);
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
	
	
	public int getMaxRipetizioni(int utenteId) throws SQLException {
		String sql= "SELECT esFor.nome, SUM(serFor.ripetizioni) "
				+ "FROM esercizi_forza esFor "
				+ "JOIN serie_forza serFor ON esFor.id= serFor.esercizio_id "
				+ "JOIN allenamenti_forza allFor ON esFor.allenamento_id = allFor.id "
				+ "WHERE allFor.utente_id = ? "
				+ "GROUP BY esFor.nome "
				+ "ORDER BY SUM(serFor.ripetizioni) DESC "
				+ "LIMIT 1 ";
		try(Connection connection= DatabaseConnection.getConnection()){
			try(PreparedStatement statement=connection.prepareStatement(sql)){
				statement.setInt(1, utenteId);
				
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
	
	public int getNumeroSerieEsercizio(String nomeEs, int utenteId) throws SQLException {
		
		String sql= "SELECT COUNT(*) "
		           + "FROM esercizi_forza esFor "
		           + "JOIN serie_forza serFor ON esFor.id = serFor.esercizio_id "
		           + "JOIN allenamenti_forza allFor ON esFor.allenamento_id = allFor.id "
		           + "WHERE esFor.nome = ? AND allFor.utente_id = ?";
		
		try(Connection connection= DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				statement.setString(1, nomeEs);
				statement.setInt(2, utenteId);
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
	
	public int getNumeroRipetizioniEsercizio(String nomeEs, int utenteId) throws SQLException {
		
		if(nomeEs == null || nomeEs.isBlank()) {
		    throw new IllegalArgumentException("Il nome dell'esercizio non può essere vuoto");
		}
		
		String sql = "SELECT SUM(serFor.ripetizioni) "
		           + "FROM esercizi_forza esFor "
		           + "JOIN serie_forza serFor "
		           + "ON esFor.id = serFor.esercizio_id "
		           + "JOIN allenamenti_forza allFor "
		           + "ON esFor.allenamento_id = allFor.id "
		           + "WHERE esFor.nome = ? "
		           + "AND allFor.utente_id = ? "
		           + "AND serFor.tipo IN ('RIPETIZIONI', 'RIPETIZIONI_CON_TEMPO') ";
		
		try(Connection connection = DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				statement.setString(1, nomeEs);
				statement.setInt(2, utenteId);
				try(ResultSet result = statement.executeQuery()){
					result.next(); 
					return result.getInt(1);
				}
			}
		}	
	}
	
	public int getTempoTotaleEsercizio(String nomeEs,int utenteId) throws SQLException {
		
		if(nomeEs == null || nomeEs.isBlank()) {
		    throw new IllegalArgumentException("Il nome dell'esercizio non può essere vuoto");
		}
		
		String sql= "SELECT SUM(serFor.durata_secondi) "
				+   "FROM esercizi_forza esFor "
				+   "JOIN serie_forza serFor "
				+   "ON esFor.id= serFor.esercizio_id "
				+   "JOIN allenamenti_forza allFor "
		        +   "ON esFor.allenamento_id = allFor.id "
				+   "WHERE esFor.nome= ? "
				+   "AND allFor.utente_id=? "
				+   "AND serFor.tipo= 'TEMPO' ";
		
		try(Connection connection = DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sql)){
				statement.setString(1, nomeEs);
				statement.setInt(2, utenteId);
				try(ResultSet result = statement.executeQuery()){
					result.next(); 
					return result.getInt(1);
				}
			}
		}	
	}
	
	public List<Workout> getAllenamenti(int utenteId) throws SQLException {
		
		List<Workout> workouts= new ArrayList<>();
		
		String sqlCorse=  "SELECT id, data, descrizione, durata, distanza "
				       +  "FROM corse "
				       +  "WHERE utente_id= ? ";
		
			try(Connection connection = DatabaseConnection.getConnection()){
			try(PreparedStatement statement = connection.prepareStatement(sqlCorse)){
				statement.setInt(1, utenteId);
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
		
		
		String sqlForza = "SELECT id, data, descrizione, durata "
				        + "FROM allenamenti_forza "
				        + "WHERE utente_id= ? ";
		
			try(PreparedStatement statement1 = connection.prepareStatement(sqlForza)){
				statement1.setInt(1, utenteId);
				try(ResultSet result1 = statement1.executeQuery()){
					while(result1.next()) {
						String id=result1.getString(1);
						LocalDate data=result1.getDate("data").toLocalDate();
						String descrizione= result1.getString(3);
						int durata= result1.getInt(4);
						
						StrengtWorkout forza= new StrengtWorkout(descrizione, id, data, durata);
						
						
						String sqlEsercizi= "SELECT id, nome "
						          + "FROM esercizi_forza "
						          + "WHERE allenamento_id =? ";
						
					try(PreparedStatement statement2 = connection.prepareStatement(sqlEsercizi)){
						statement2.setString(1, id);
						try(ResultSet result2= statement2.executeQuery()){
							while(result2.next()) {
								int esercizioId= result2.getInt("id");
								String nome= result2.getString("nome");
								
								Exercise esercizio =new Exercise(nome);
								forza.aggiungiEsercizio(esercizio);
								
								String sqlSerie =
								        "SELECT ripetizioni, durata_secondi, kg, tipo "
								      + "FROM serie_forza "
								      + "WHERE esercizio_id = ?";
								
								try(PreparedStatement statement3= connection.prepareStatement(sqlSerie)) {
								    statement3.setInt(1, esercizioId);
								    try(ResultSet result3 = statement3.executeQuery()) {
								        while(result3.next()) {

								        	int ripetizioni = result3.getInt("ripetizioni");
								        	int durataSecondi = result3.getInt("durata_secondi");
								        	double kg = result3.getDouble("kg");
								        	String tipoString = result3.getString("tipo");
								        	
								        	TipoSerie tipo = TipoSerie.valueOf(tipoString);
								        	
								       SerieWorkout serie= new SerieWorkout(ripetizioni, durataSecondi, kg, tipo);
								       esercizio.aggiungiSerie(serie);
								       
								        }
								    }
								}
							}
						}
					  }
					workouts.add(forza);
					}
				}
			}
		}
			
		return workouts;
	}
	
}
