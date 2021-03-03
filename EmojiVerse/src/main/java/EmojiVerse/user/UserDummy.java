package EmojiVerse.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import spark.Route;

public class UserDummy {
	public final List<User> users = new ArrayList<>(Arrays.asList(
			// need to generate legit hashed passwords 
		new User("nikita","password"),
		new User("dan","password"),
		new User("bonny","password")
	));
	public User getUserByUsername(String username) {
		//return null;
		// majik
		return users.stream().filter(b -> b.getUsername().equals(username)).findFirst().orElse(null);
	}
	public void addUser(User user) {
		this.users.add(user);
	}
}