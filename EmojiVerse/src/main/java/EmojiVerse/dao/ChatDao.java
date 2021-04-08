package EmojiVerse.dao;

import java.util.List;

import EmojiVerse.chatChannel.Channel;
import EmojiVerse.emoji.EmojiMessage;
import EmojiVerse.messaging.Chat;
import EmojiVerse.user.User;

public interface ChatDao {
	Channel getChannelByID(String id);
	void addMessage(Channel channel, EmojiMessage message);
	void addUser(Channel channel, User user);
	void removeUser(Channel channel, User user);
}