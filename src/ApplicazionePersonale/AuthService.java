package ApplicazionePersonale;

import java.sql.SQLException;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class AuthService {
	
	private final UserDAO userDAO;
	private final Argon2 argon2;
	
	public AuthService(UserDAO userDAO){
		this.userDAO=userDAO;
		this.argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
	}
	
	public boolean registra(String username, String password) throws SQLException{
	
		if (username == null || username.isBlank() || password == null || password.isBlank()) {
		    throw new IllegalArgumentException("Username e password non validi");
		}
		
		if(userDAO.trovaUtentePerUsername(username)!=null) {
			return false;
		}else {
			String passwordHash = argon2.hash(3, 65536, 1, password.toCharArray());
			User user = new User(username,passwordHash);
			userDAO.salva(user);
			return true;
		}
	}
	
	public boolean login(String username, String password) throws SQLException{
		
		if (username == null || username.isBlank() || password == null || password.isBlank()) {
			return false;
		}
		User user = userDAO.trovaUtentePerUsername(username);
		
		if(user==null) {
			return false;
		}
		return argon2.verify(user.getPasswordHash(), password.toCharArray());
	}

}
