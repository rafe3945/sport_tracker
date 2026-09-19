package ApplicazionePersonale;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class TestDatabase {
	
	public static void main(String[] args) throws SQLException {

		Connection connection = DriverManager.getConnection(
		        "jdbc:mysql://localhost:3306/sporttracker",
		        "root",
		        "Raffaele05@"
		);
		System.out.println("Connessione riuscita!");
		
		Statement statement = connection.createStatement();
		String sql = "SELECT * FROM allenamenti";
		ResultSet result = statement.executeQuery(sql);
		
		while (result.next()) {
			System.out.println("=====ALLENAMENTO=====");
		    System.out.println("id: "+result.getInt("id"));
		    System.out.println("data: "+result.getDate("data"));
		    System.out.println("descrizione: "+result.getString("descrizione"));
		    System.out.println("durata: "+ result.getInt("durata"));
		    System.out.println("tipo allenmaneto: "+ result.getString("tipo"));
		}
	}

}
