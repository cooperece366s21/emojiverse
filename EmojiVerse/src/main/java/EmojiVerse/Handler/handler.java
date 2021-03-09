package EmojiVerse.Handler;

import EmojiVerse.App;
import EmojiVerse.chatChannel.Channel;
import EmojiVerse.chatChannel.chatChennelImp;
import EmojiVerse.emoji.emojiStore;
import EmojiVerse.user.User;
import EmojiVerse.user.UserDummy;
import spark.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class handler {
//    final emojiStore memojiStore;
    chatChennelImp channelStore;

    public handler(chatChennelImp channelStore){
        this.channelStore = channelStore;
    }
    public Channel getChannel(Request request) {
        String channelName = request.params(":name");
        return channelStore.get(channelName);
    }

    public Channel createChannel(Request request){
        List<User> users = new ArrayList<>(Arrays.asList(
                App.userDummy.getUserByUsername(request.params(":user1")),
                App.userDummy.getUserByUsername(request.params(":user2"))
        ));

        Channel newChannel = new Channel(request.params(":id"), users,List.of("New Channel Created"));
        channelStore.addChannel(newChannel);
        return newChannel;
    }
}
