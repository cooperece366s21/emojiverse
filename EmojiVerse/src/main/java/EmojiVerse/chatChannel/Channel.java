package EmojiVerse.chatChannel;

import EmojiVerse.emoji.Emoji;
import EmojiVerse.emoji.EmojiMessage;
import EmojiVerse.emoji.EmojiMessageStore;
import EmojiVerse.user.User;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

public class Channel {

    private String id;
    private List<User> userList;

    // TODO: this message should be combination of Emoji ids?
    List<EmojiMessage> messages;
    // we need to limit the number of messages otherwise this gets expensive
    
    //    Use to track last updated message
    Time timestamp;
    boolean active;

    public String getId() {
        return id;
    }

    public List<EmojiMessage> getMessages() {
        return messages;
    }

    public List<User> getUserList() {
    	return userList;
    }
    
    public Channel(String id) {
    	this.id = id;
    }
    
    public Channel(String id, List<User> userList) {
    	this.id = id;
    	this.userList = userList;
    }
    
    public Channel(String id, List<User> userList, List<EmojiMessage> messages) {
        this.id = id;
        this.userList = userList;
        this.messages = messages;
        timestamp = Time.valueOf(LocalTime.now());
        active = true;
    }

    public void addMessage(EmojiMessage message) {
    	this.messages.add(message);
    }
    
    public void addMessage(String userID, EmojiMessage message){ //a message object ought to contain username and timestamp 
        messages.add(message);
    }
    
    public void addUser(User user) {
    	userList.add(user);
    }
    
    // The user object I defined earlier is not appropriate for this case
    // It does not contain information relevant to chats like screenname or permissions or status
    // But it does contain stuff like password and email
    // Need to create new user object that is specific to what gets stored in chats 
    // for now stay with user object
    public User getUser(User user) {
    	String username = user.getUsername();
    	return userList.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    }
    
    public boolean isUserMember(User user) {
    	// check if user is allowed to access this chat or not
    	return false;
    }
}
