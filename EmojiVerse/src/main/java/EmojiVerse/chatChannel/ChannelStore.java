package EmojiVerse.chatChannel;

import EmojiVerse.user.UserDummy;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChannelStore{

    private static Map<String, Channel> channelMap;

    static{
        channelMap = List.of(
                new Channel("1", UserDummy.users, List.of("\uD83E\uDD2F \u0030\uFE0F\u20E3","\u0030\uFE0F\u20E3 \u0023\uFE0F\u20E3")))
                .stream()
                .collect(Collectors.toMap(Channel::getId, Function.identity()));

    }

    public Channel get(String id) {
        return channelMap.get(id);
    }

    public void addChannel(Channel channel){
        channelMap.put(channel.id, channel);
    }
}
