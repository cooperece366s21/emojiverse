package EmojiVerse.emoji;

import java.time.LocalDateTime;
import java.util.List;

import EmojiVerse.user.User;

public class EmojiMessage {
	private User user; // there is no real use in using the full user object but whatever right?
	private LocalDateTime time; // timestamp the message 
	private String message; // lets hope java handles unicode fine
	// should messages consist of individual emoji character objects or 
	// should they be character sequences from which we can parse out our special emoji 
	// using regex or something 
    List<Emoji> Emojis;
    String UserID;
    
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		//this is dangerous for time zones, needs to be taken care of somewhere
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public EmojiMessage(User user, LocalDateTime time, String message) {
		this.user = user;
		this.time = time;
		this.message = message;
	}

	public EmojiMessage(Emoji emoji1, String userId) {
        Emojis = List.of(emoji1);
        this.UserID = userId;
    }

    public List<Emoji> getEmojis() {
        return Emojis;
    }

    public EmojiMessage add(Emoji emoji){
        Emojis.add(emoji);
        return this;
    }
}
