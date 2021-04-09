package EmojiVerse.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import EmojiVerse.chatChannel.Channel;
import EmojiVerse.user.LoginResult;
import EmojiVerse.user.User;

public class UserDummy implements UserDao{
	
	private static User nikita = new User("nikita","password", "nikita@cooper.edu");
	private static User bonny = new User("bonny","password", "bonny@cooper.edu");
	private static User dan = new User("dan","password", "dan@cooper.edu");
	private List<User> userList = Arrays.asList(nikita,bonny,dan);
	//can make a cutesy map thing 
	
	@Override
	public User getUserByUsername(String username) {
		// do I want username or User object?
		// want consistent behavior
		// see channel get user implementation 
		return userList.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
	}
	@Override
	public void addFriend(User user, User friend) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeFriend(User user, User friend) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isFriendsWith(User source, User target) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void addToBlockList(User source, User target) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeFromBlockList(User source, User target) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void registerUser(User user) {
		// TODO Auto-generated method stub
		// Add user to some kind of user map in this class
	}
	
	// I don't know if this is a good place to authenticate the user
	// this is clearly an incomplete implementation 
	@Override
	public LoginResult authUser(User user) {
		LoginResult result = new LoginResult();
		User userFound = getUserByUsername(user.getUsername());
		if(userFound == null) {
			result.setError("Invalid username");
		//} else if(!PasswordUtil.verifyPassword(user.getPassword(), userFound.getPassword())) {
			//result.setError("Invalid password");
		} else {
			result.setUser(userFound);
		}
		return result;
	}
	
	@Override
	public void addChannel(User user, Channel channel) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeChannel(Channel channel) {
		// TODO Auto-generated method stub
		
	}
	
	
}
