package EmojiVerse.chatChannel;

import EmojiVerse.emoji.EmojiMessage;
import EmojiVerse.user.User;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

public class Channel {

    private String id;
    private String channelName;
	private List<User> userList; // This should be a unique collection 
    // TODO: this message should be combination of Emoji ids?
    List<EmojiMessage> messages;
    // we need to limit the number of messages fetched otherwise this gets expensive
    //    Use to track last updated message
    Time timestamp;
    
    
    public Channel(String id) {
    	this.id = id;
    }
    public Channel(String id, List<User> userList) {
    	this.id = id;
    	this.userList = userList;
    	// for e in userList, send message to user dao to add to list
    }
    

    public String getId() {
        return id;
    }
    public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
    // I think getting the messages should be done through the DAO
    public List<EmojiMessage> getMessages() {
        return messages;
    }
    public List<User> getUserList() {
    	return userList;
    }
    // again, should messages even be here?
    public void addMessage(EmojiMessage message) {
    	this.messages.add(message);
    	// also update time here
    }
    public void addUser(User user) {
    	userList.add(user);
    }
}
