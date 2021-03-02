package EmojiVerse.user;

import java.util.List;

public class UserDummy {
	private final List<User> users = List.of(
			// need to generate legit hashed passwords 
		new User("nikita","password"),
		new User("dan","password"),
		new User("bonny","password")
	);
	public User getUserByUsername(String username) {
		// majik
		return users.stream().filter(b -> b.getUsername().equals(username)).findFirst().orElse(null);
	}
}