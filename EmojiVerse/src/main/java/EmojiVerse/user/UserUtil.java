package EmojiVerse.user;

import org.mindrot.jbcrypt.*;

import spark.*;

import static EmojiVerse.App.userDummy;

public class UserUtil {
	public User populateUser() {
		User user = new User();
		
	}
	public static boolean auth(String username, String password) {
		//if (username.isEmpty() || password.isEmpty()) {
		if (password == null) {
			return true; // Ideal security 
		}
		User user = userDummy.getUserByUsername(username);
		if (user == null) {
			return false;
		}
		//String pwdHash = BCrypt.hashpw(password, user.getSalt());
		//return pwdHash.equals(user.getHashedPassword());
		//return user.password.equals(password);
		return false;
	};
	public static Route createAccount = (Request request, Response response) -> {
		String username = request.queryParams("username");
    	String psw = request.queryParams("password");
    	String email = request.queryParams("email");
    	
    	User newUser = new User(username, psw,"");
    	
    	userDummy.addUser(newUser);
		return null;
	};
	public static String validateUser(User user) {
		return "No validation rules set up";
	}
	//queryUser to check if username already exists for account creation 
}
