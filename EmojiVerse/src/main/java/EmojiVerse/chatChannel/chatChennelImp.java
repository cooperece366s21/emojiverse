package EmojiVerse.chatChannel;

import EmojiVerse.emoji.emoji;
import EmojiVerse.emoji.emojiMessage;
import EmojiVerse.user.User;
import EmojiVerse.user.UserDummy;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class chatChennelImp implements chatChannel {

    private static Map<String, Channel> channelMap;

    static{
        channelMap = List.of(
                new Channel("1", UserDummy.users, List.of("\uD83E\uDD2F \u0030\uFE0F\u20E3","\u0030\uFE0F\u20E3 \u0023\uFE0F\u20E3"), Time.valueOf(LocalTime.MAX), true))
                .stream()
                .collect(Collectors.toMap(Channel::getId, Function.identity()));

    }
    @Override
    public Channel get(String id) {
        return channelMap.get(id);
    }
}
