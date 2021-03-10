package EmojiVerse.Handler;

import EmojiVerse.App;
import EmojiVerse.chatChannel.Channel;
import EmojiVerse.chatChannel.ChannelStore;
import EmojiVerse.emoji.EmojiMessage;
import EmojiVerse.emoji.EmojiStore;
import EmojiVerse.user.User;
import com.google.gson.Gson;
import spark.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Handler {

    ChannelStore channelStore;
    EmojiStore emojiStore;
    Gson gson;

//    TODO: Need to have more stores
    public Handler(ChannelStore channelStore, EmojiStore emojiStore){

        this.channelStore = channelStore;
        this.emojiStore = emojiStore;
        gson = new Gson();
    }
    public String getChannel(Request request) {
        String channelName = request.params(":id");
        return gson.toJson(channelStore.get(channelName));
    }

    public String getAllChannels(){
        return  gson.toJson(channelStore.getChannelMap());
    }

    public String createChannel(Request request){
//        TODO:Need to guranteer the user exists
        List<User> users = new ArrayList<>(Arrays.asList(
                App.userDummy.getUserByUsername(request.params(":user1")),
                App.userDummy.getUserByUsername(request.params(":user2"))
        ));

        Channel newChannel = new Channel(request.params(":id"), users, App.emojiMessageStore.getMessages());
        channelStore.addChannel(newChannel);
        return "Channel "+ request.params(":id")+" created";
    }

    public String addMessage(Request request){
        Channel current = App.channelStore.get(request.params(":channelID"));
        String userId = request.params(":userID");

//        TODO:In this way only one emoji is supported
        EmojiMessage message = new EmojiMessage(App.emojiStore.get(request.params(":emoji")),userId);
        current.addMessage(userId,message);

        return gson.toJson(current);
    }


//    Handlers for Emojis
    public String showEmojis(){
        return gson.toJson(emojiStore.getEmojiMap());
    }
}
