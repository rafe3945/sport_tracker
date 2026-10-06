package ApplicazionePersonale;
import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class TestDatabase {
	
	public static void main(String[] args) throws SQLException {

		Connection connection = DatabaseConnection.getConnection();
		System.out.println("Connessione riuscita!");
		
		
		
		
	}

}
