package EmojiVerse.dao;

import java.util.List;

import EmojiVerse.messaging.Chat;
import EmojiVerse.user.User;

public interface ChatDao {
	List<Chat> getUserChats(User user);
	// getChatMessages ? 
}