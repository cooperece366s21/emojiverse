package EmojiVerse.user;

import java.util.HashMap;

// generate getter values for all of these
// really better off using code generation for this crap 
public class User {
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	private String username;
	private String email;
    private String password;
    
	static HashMap<Integer, User> friendsList = new HashMap<Integer, User>();
	String picture_link;
	static HashMap<Integer, User> blockedList = new HashMap<Integer, User>();
}
