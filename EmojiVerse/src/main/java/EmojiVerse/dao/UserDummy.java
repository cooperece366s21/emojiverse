package EmojiVerse.dao;

import EmojiVerse.user.LoginResult;
import EmojiVerse.user.User;

public class UserDummy implements UserDao{
	
	private static User nikita = new User("nikita","password", "nikita@cooper.edu");
	//public final static List<User> users = new ArrayList<>(Arrays.asList());
	//can make a cutesy map thing 
	
	@Override
	public User getUserByUsername(String username) {
		if (username.equals("nikita")) {
			System.out.println("Call for nikita");
			return nikita;
		} 
		return null;
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
	
	
}
