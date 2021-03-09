package EmojiVerse.Handler;

import EmojiVerse.App;
import EmojiVerse.chatChannel.Channel;
import EmojiVerse.chatChannel.ChannelStore;
import EmojiVerse.emoji.EmojiMessage;
import EmojiVerse.user.User;
import spark.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Handler {

    ChannelStore channelStore;

//    TODO: Need to have more stores
    public Handler(ChannelStore channelStore){
        this.channelStore = channelStore;
    }
    public Channel getChannel(Request request) {
        String channelName = request.params(":id");
        return channelStore.get(channelName);
    }

    public Map<String, Channel> getAllChannels(){
        return  channelStore.getChannelMap();
    }

    public Channel createChannel(Request request){
//        TODO:Need to guranteer the user exists
        List<User> users = new ArrayList<>(Arrays.asList(
                App.userDummy.getUserByUsername(request.params(":user1")),
                App.userDummy.getUserByUsername(request.params(":user2"))
        ));

        Channel newChannel = new Channel(request.params(":id"), users, App.emojiMessageStore.getMessages());
        channelStore.addChannel(newChannel);
        return newChannel;
    }

    public Channel addMessage(Request request){
        Channel current = App.channelStore.get(request.params(":channelID"));
        String userId = request.params(":userID");

//        TODO:In this way only one emoji is supported
        EmojiMessage message = new EmojiMessage(App.emojiStore.get(request.params(":emoji")),userId);
        current.addMessage(userId,message);

        return current;
    }
}
