package ApplicazionePersonale;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	
	public void salva(User user) throws SQLException {
		
		try(Connection connection= DatabaseConnection.getConnection()){
			
			String sql="INSERT INTO utenti (username, password_hash) "
				      + "Values (?,?) ";
			try(PreparedStatement statement= connection.prepareStatement(sql)){
				statement.setString(1, user.getUsername());
				statement.setString(2, user.getPasswordHash());
				
				statement.executeUpdate();
			}
		}
	}
	
	public User trovaUtentePerUsername(String usernameCerca) throws SQLException {
		
		try(Connection connection= DatabaseConnection.getConnection()){
			String sql= "SELECT id, username, password_hash "
					  + "FROM utenti "
					  + "WHERE username = ? ";
			
			try(PreparedStatement statement= connection.prepareStatement(sql)){
				statement.setString(1, usernameCerca);
				try(ResultSet result= statement.executeQuery()){
					if(result.next()) {
						int id= result.getInt("id");
						String username=result.getString("username");
						String password=result.getString("password_hash");
						User user= new User(id, username, password);
						return user;
					}else {
						return null;
					}
				}
			}
		}	
	}

}
