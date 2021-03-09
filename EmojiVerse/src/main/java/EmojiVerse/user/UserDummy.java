package EmojiVerse.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import spark.Route;

public class UserDummy {
	public final static List<User> users = new ArrayList<>(Arrays.asList(
			// need to generate legit hashed passwords 
		new User("nikita","password", "https://emojiverse.s3.us-east-2.amazonaws.com/boy-2.png"),
		new User("dan","password", "https://emojiverse.s3.us-east-2.amazonaws.com/boy-1.png"),
		new User("bonny","password","https://emojiverse.s3.us-east-2.amazonaws.com/girl-1.png")
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
