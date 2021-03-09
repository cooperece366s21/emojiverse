package EmojiVerse.chatChannel;

import EmojiVerse.App;
import EmojiVerse.emoji.Emoji;
import EmojiVerse.emoji.EmojiMessage;
import EmojiVerse.user.UserDummy;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChannelStore{

    private static Map<String, Channel> channelMap;

    static{
        channelMap = List.of(
                new Channel("1", UserDummy.users, App.emojiMessageStore.getMessages()))
                .stream()
                .collect(Collectors.toMap(Channel::getId, Function.identity()));

    }

    public Channel get(String id) {
        return channelMap.get(id);
    }

    public void addChannel(Channel channel){
        channelMap.put(channel.id, channel);
    }

    public Map<String, Channel> getChannelMap() {
        return channelMap;
    }
}
