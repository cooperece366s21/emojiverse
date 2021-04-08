package EmojiVerse.user;

import java.util.HashMap;

// generate getter values for all of these
// really better off using code generation for this crap 
public class User {
	
	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public User() {
		this.username = "";
		this.password = "";
		this.email = ""; 
		//unclear if this is the correct method
	}

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
	
	public String validate() {
		String error = "";
		
		if (username.isEmpty()) {
			error = "You must enter a username";
		} else if (password.isEmpty()) {
			error = "You must enter a password";
		} else if (email.isEmpty()) {
			error = "You must enter an email";
		}
		return error;
	}
}
