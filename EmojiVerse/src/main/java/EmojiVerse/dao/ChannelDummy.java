package EmojiVerse.dao;

import java.util.ArrayList;
import java.util.List;

import EmojiVerse.chatChannel.Channel;
import EmojiVerse.emoji.EmojiMessage;
import EmojiVerse.user.User;

public class ChannelDummy implements ChatDao{

	private List<Channel> channels = new ArrayList<Channel>();
	private int newChanID = 1;
	
	@Override
	public Channel createChannel(List<User> users) {
		Channel newChannel = new Channel(Integer.toString(newChanID), users);
		// users should be added in to both DAOs in external code
		channels.add(newChannel);
		newChanID++;
		return newChannel;
	}
	
	@Override
	public Channel getChannelByID(String id) {
		return channels.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
	}

	@Override
	public void addMessage(Channel channel, EmojiMessage message) {
		// TODO Auto-generated method stub
		channel.addMessage(message);
	}

	@Override
	public void addUser(Channel channel, User requester, User target) {
		// Can regular users add other users? Should that be configurable?
		channel.addUser(target);
	}

	@Override
	public void removeUser(Channel channel, User requester, User target) {
		// TODO Auto-generated method stub
		// not gonna implement rn
	}	
}