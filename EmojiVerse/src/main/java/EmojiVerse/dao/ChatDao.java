package EmojiVerse.dao;

import java.util.List;

import EmojiVerse.chatChannel.Channel;
import EmojiVerse.emoji.EmojiMessage;
import EmojiVerse.user.User;

public interface ChatDao {
	String getChannelByID(int id);
	void addMessage(Channel channel, String message, String username);
	void addUser(Channel channel, String target_username); //move authentication responsibility to DAO
	void removeUser(Channel channel, String target_username);
	int getNextChatId();
	String getMessages(String chat_name);
	int getChatIdFromChatName(String chat_name);
}
