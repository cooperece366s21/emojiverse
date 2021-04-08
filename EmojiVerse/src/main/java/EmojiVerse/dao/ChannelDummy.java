package EmojiVerse.dao;

import java.util.List;

import EmojiVerse.chatChannel.Channel;
import EmojiVerse.emoji.EmojiMessage;
import EmojiVerse.user.User;

public class ChannelDummy implements ChatDao{

	private List<Channel> channels;
	private int newChanID = 1;
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

	@Override
	public Channel createChannel(List<User> users) {
		Channel newChannel = new Channel(Integer.toString(newChanID), users);
		channels.add(newChannel);
		System.out.println("Added new channel with");
		System.out.println(users);
		newChanID++;
		return newChannel;
	}	
}