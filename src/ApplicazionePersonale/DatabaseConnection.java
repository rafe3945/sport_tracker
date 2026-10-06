package ApplicazionePersonale;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
		private static String username = System.getenv("DB_USERNAME");
		private static String password = System.getenv("DB_PASSWORD");
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(
		        "jdbc:mysql://localhost:3306/sporttracker",
		        username,
		        password
		);
	}

}
