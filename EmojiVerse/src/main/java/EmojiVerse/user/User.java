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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public static void setFriendsList(HashMap<Integer, User> friendsList) { this.friendsList = friendsList; }
	public HashMap<Integer, User> getFriendsList() { return friendsList; }
	public static void addFriendtoFriendsList(int id, User friend){ friendsList.put(id, friend);}


	public String getPicture_link() { return picture_link; }
	public void setPicture_link(String picture_link) { this.picture_link = picture_link; }

	public void setBlockedList(HashMap<Integer, User> blockedList) { this.blockedList = blockedList; }
	public HashMap<Integer, User> getBlockedList() { return blockedList; }
	public static void blockFriendtoBlockedList(int id, User friend){blockedList.put(id, friend);}

	public User(String username, String password, String picture_link) {
		super();
		this.username = username;
		this.password = password;
		this.picture_link = picture_link;
	}
	String username;
    String password;
	static HashMap<Integer, User> friendsList = new HashMap<Integer, User>();
	String picture_link;
	static HashMap<Integer, User> blockedList = new HashMap<Integer, User>();
    //friends list, lets make that a hash map 
    //block list?
    //wallet 
}
