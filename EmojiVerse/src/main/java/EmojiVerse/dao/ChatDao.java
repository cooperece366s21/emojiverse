package EmojiVerse.dao;

import java.util.List;

import EmojiVerse.chatChannel.Channel;
import EmojiVerse.emoji.EmojiMessage;
import EmojiVerse.user.User;

public interface ChatDao {
	
	/*basic funcs*/
	
	String getChannelByID(int id);
	int getNextChatId();
	int getChatIdFromChatName(String chat_name);
	
	/*message funcs*/
	
	void addMessage(String chat_name, String message, String username, String datetime);
	String getMessages(String chat_name);
	
	/*participant funcs*/
	
	void addUser(Channel channel, String target_username); //move authentication responsibility to DAO
	void removeUser(Channel channel, String target_username);
	
	/* channel funcs*/
	
	void addChannel(Channel channel, String requester_username);
	String getChannelList(String username);
	public void removeChannel(String chat_name);

}
