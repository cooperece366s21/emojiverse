package EmojiVerse.dao;

import java.util.List;

import EmojiVerse.chatChannel.Channel;
import EmojiVerse.user.LoginResult;
import EmojiVerse.user.User;

public interface UserDao {
	
	/*basic funcs*/
	
	User getUserByUsername(String username);
	int getUserIdFromUserName(String username);
	
	/*friend funcs*/
	
	void addFriend(String username, String friend_username);
	void removeFriend(String username, String friend_username);
	boolean isFriendsWith(User source, User target);
	String getFriendList(String username);
	
	/*blocked funcs*/
	
	void addToBlockList(User source, User target);
	void removeFromBlockList(User source, User target);
	
	/*authentication funcs*/
	
	void registerUser(User user);
	boolean authUser(String username, String password); //should this be here
	// should authentication be done elsewhere?
	boolean isDuplicate(User user);
}
