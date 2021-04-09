package EmojiVerse.emoji;

import java.time.LocalDateTime;
import EmojiVerse.user.User;


public class EmojiMessage {
	private User user; // there is no real use in using the full user object but whatever right?
	private LocalDateTime time; // timestamp the message 
	private String message; // lets hope java handles unicode fine
	// should messages consist of individual emoji character objects or 
	// should they be character sequences from which we can parse out our special emoji 
	// using regex or something 
    
    public User getUser() {
		return user;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public String getMessage() {
		return message;
	}

	public EmojiMessage(User user, LocalDateTime time, String message) {
		this.user = user;
		this.time = time;
		this.message = message;
	}
}
