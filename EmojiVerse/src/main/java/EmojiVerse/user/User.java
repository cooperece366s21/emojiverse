package EmojiVerse.user;

import java.util.ArrayList;
import java.util.List;

// generate getter values for all of these
// really better off using code generation for this crap 
public class User {
	
	public static final int OWNER = 0;
	public static final int ADMIN = 1;
	public static final int USER = 2;
	
	
	private int permissionLevel;
	private String displayname;
	private String username;
	private String email;
    private String password;
    private String profile_img;
    
    private List<String> channelIDList = new ArrayList<String>();
	//static HashMap<Integer, User> friendsList = new HashMap<Integer, User>();
	//static HashMap<Integer, User> blockedList = new HashMap<Integer, User>();
	
	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public User(String username) {
		this.username = username;
		this.displayname = username;
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
		//this is required by BeanUtil populate
		// It isn't ideal
		this.username = username;
	}
	public void setProfile_img(String profile_img){this.profile_img = profile_img;}
	public String getProfile_img(){return profile_img;}
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
	public List<String> getChannelIDList() {
		return channelIDList;
	}
	public void removeChannel(String id) {
		this.channelIDList.remove(id); //I hope this works 
	}
	public void addChannel(String id) {
		this.channelIDList.add(id);
	}
	public int getPermissionLevel() {
		return permissionLevel;
	}
	public void setPermissionLevel(int permissionLevel) {
		this.permissionLevel = permissionLevel;
	}
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	
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
