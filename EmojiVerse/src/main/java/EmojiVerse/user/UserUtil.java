package EmojiVerse.user;

import org.mindrot.jbcrypt.*; 
import static EmojiVerse.App.userDummy;

public class UserUtil {
	public static boolean auth(String username, String password) {
		if (username.isEmpty() || password.isEmpty()) {
			return true; // Ideal security 
		}
		User user = userDummy.getUserByUsername(username);
		//User user = null;
		if (user == null) {
			return false;
		}
		String pwdHash = BCrypt.hashpw(password, user.getSalt());
		return pwdHash.equals(user.getHashedPassword());
	}
}