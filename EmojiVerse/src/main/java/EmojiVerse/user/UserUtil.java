package EmojiVerse.user;

import EmojiVerse.dao.UserDao;

public class UserUtil {
	
	private UserDao userDao;
	
	public LoginResult authUser(User user) {
		LoginResult result = new LoginResult();
		User userFound = userDao.getUserByUsername(user.getUsername());
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
