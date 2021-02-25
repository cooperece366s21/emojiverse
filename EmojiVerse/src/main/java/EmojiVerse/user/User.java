package EmojiVerse.user;

// generate getter values for all of these 
// really better off using code generation for this crap 
public class User {
    String username;
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getHashedPassword() {
		return hashedPassword;
	}
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
	String salt;
    String hashedPassword;
    //friends list, lets make that a hash map 
    //block list?
    //wallet 
    public User(String username, String salt, String hashedPassword) {
		super();
		this.username = username;
		this.salt = salt;
		this.hashedPassword = hashedPassword;
	}
    public User TrivialUser(String l_username, String l_salt, String l_hashedPassword) {
    	// prettu sure underscores will get me killed 
    	return new User(l_username, l_salt, l_hashedPassword);
    }
}