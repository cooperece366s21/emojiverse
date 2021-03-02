package EmojiVerse.user;

// generate getter values for all of these 
// really better off using code generation for this crap 
public class User {
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	String username;
    String password;

    //friends list, lets make that a hash map 
    //block list?
    //wallet 
}