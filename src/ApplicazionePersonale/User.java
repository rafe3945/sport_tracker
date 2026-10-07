package ApplicazionePersonale;


public class User {
	
	private int id;
	private String passwordHash;
	private String username;
	
	User(String username, String passwordHash){
		
		if(username==null || username.isBlank()) {
			throw new IllegalArgumentException("L'username non può essere vuota");
		}
		this.username=username;
		
		if(passwordHash==null || passwordHash.isBlank()) {
			 throw new IllegalArgumentException("La password non può essere vuota");
		}
		this.passwordHash=passwordHash;
	}
	
	User(int id, String username, String passwordHash){
		this.id=id;
		if(username==null || username.isBlank()) {
			throw new IllegalArgumentException("L'username non può essere vuota");
		}
		this.username=username;
		
		if(passwordHash==null || passwordHash.isBlank()) {
			 throw new IllegalArgumentException("La password non può essere vuota");
		}
		this.passwordHash=passwordHash;
	}

	public int getId() {
		return id;
	}

	public String getPasswordHash() {
		return passwordHash;
	}
	
	public String getUsername() {
		return username;
	}
	
}
