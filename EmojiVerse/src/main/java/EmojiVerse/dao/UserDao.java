package EmojiVerse.dao;

import java.util.List;

import EmojiVerse.chatChannel.Channel;
import EmojiVerse.user.LoginResult;
import EmojiVerse.user.User;

public interface UserDao {
	User getUserByUsername(String username);
	
	void addFriend(User user, User friend);
	
	void removeFriend(User user, User friend);
	
	boolean isFriendsWith(User source, User target);
	
	void addToBlockList(User source, User target);
	
	void removeFromBlockList(User source, User target);
	
	void registerUser(User user);
	
	void addChannel(Channel channel, String requester_username);
	
	void removeChannel(Channel channel);
	
	String getChannelList(String username);
	
	boolean authUser(String username); //should this be here
	// should authentication be done elsewhere?

	boolean isDuplicate(User user);
}
