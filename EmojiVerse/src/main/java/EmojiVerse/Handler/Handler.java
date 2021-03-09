package EmojiVerse.Handler;

import EmojiVerse.App;
import EmojiVerse.chatChannel.Channel;
import EmojiVerse.chatChannel.ChannelStore;
import EmojiVerse.user.User;
import spark.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

//    public List<>

    public Channel createChannel(Request request){
//        TODO:Need to guranteer the user exists
        List<User> users = new ArrayList<>(Arrays.asList(
                App.userDummy.getUserByUsername(request.params(":user1")),
                App.userDummy.getUserByUsername(request.params(":user2"))
        ));

        Channel newChannel = new Channel(request.params(":id"), users,List.of("New Channel Created"));
        channelStore.addChannel(newChannel);
        return newChannel;
    }
}
