package EmojiVerse.dao;

import EmojiVerse.chatChannel.Channel;
import EmojiVerse.emoji.EmojiMessage;
import EmojiVerse.user.User;

public class ChannelDummy implements ChatDao{

	Channel dummyChannel = new Channel("0");
	
	
	@Override
	public Channel getChannelByID(String id) {
		if (id.equals("0")) { // we need to standardize on a chat id format
			return dummyChannel;
		}
		return null;
	}

	@Override
	public void addMessage(Channel channel, EmojiMessage message) {
		// TODO Auto-generated method stub
		channel.addMessage(message);
	}

	@Override
	public void addUser(Channel channel, User user) {
		// TODO Auto-generated method stub
		// There need to be permissions for this kind of thing
		channel.addUser(user);
	}

	@Override
	public void removeUser(Channel channel, User user) {
		// TODO Auto-generated method stub
		
	}	
}