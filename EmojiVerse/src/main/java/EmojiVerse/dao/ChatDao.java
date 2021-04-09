package EmojiVerse.dao;

import java.util.List;

import EmojiVerse.chatChannel.Channel;
import EmojiVerse.emoji.EmojiMessage;
import EmojiVerse.user.User;

public interface ChatDao {
	Channel getChannelByID(String id);
	Channel createChannel(List<User> users); //I assume that ID selection occurs automagically
	void addMessage(Channel channel, EmojiMessage message);
	void addUser(Channel channel, User requester, User target); //move authentication responsibility to DAO
	void removeUser(Channel channel, User requester, User target);
}