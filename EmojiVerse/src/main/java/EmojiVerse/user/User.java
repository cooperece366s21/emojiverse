package EmojiVerse.user;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

// generate getter values for all of these
// really better off using code generation for this crap 
public class User {
	
	public static final int OWNER = 0;
	public static final int ADMIN = 1;
	public static final int USER = 2;
	
	
	@Expose private int permissionLevel;
	@Expose private String displayname;
	@Expose private String username;
	@Expose private String email;
    @Expose private String password;
    @Expose private String profile_img;
    @Expose private int emoji_coins;
    
    private List<String> channelIDList = new ArrayList<String>();
	//static HashMap<Integer, User> friendsList = new HashMap<Integer, User>();
	//static HashMap<Integer, User> blockedList = new HashMap<Integer, User>();
	
	public User(String username, String password, String email,int permissionLevel, String displayname, String profile_img, int emoji_coins) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.permissionLevel = permissionLevel;
		this.displayname = displayname;
		this.profile_img = profile_img;
		this.emoji_coins = emoji_coins;
	}
	
	public User(String username) {
		this.username = username;
		this.displayname = username;
	}

	public User(String username, String password) {
		this.username = username;
		this.displayname = username;
	}
	
	public User() {
		this.username = "";
		this.password = "";
		this.email = ""; 
		//unclear if this is the correct method
	}

	public User(String username, String password, String email)
	{
		this.username = username;
		this.password = password;
		this.email = email;
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
	public int getEmoji_coins() { return emoji_coins; }
	public void setEmoji_coins(int emoji_coins) { this.emoji_coins = emoji_coins; }

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
