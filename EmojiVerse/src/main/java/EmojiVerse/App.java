package EmojiVerse;
import static spark.Spark.*;

import EmojiVerse.Handler.Handler;
import EmojiVerse.chatChannel.ChannelStore;
import EmojiVerse.emoji.Emoji;
import EmojiVerse.emoji.EmojiMessageStore;
import EmojiVerse.emoji.EmojiStore;
import EmojiVerse.login.LoginController;
import EmojiVerse.user.User;
import EmojiVerse.user.UserDummy;
import EmojiVerse.user.UserUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.norberg.automatter.gson.AutoMatterTypeAdapterFactory;
import spark.*;
import EmojiVerse.messaging.ChatWebSocketHandler;
import org.eclipse.jetty.websocket.api.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static UserDummy userDummy = new UserDummy();
    public static EmojiStore emojiStore = new EmojiStore();
    public static EmojiMessageStore emojiMessageStore = new EmojiMessageStore();
    public static ChannelStore channelStore= new ChannelStore();

    static Map<Session, String> userUsernameMap = new ConcurrentHashMap<>();
    static int nextUserNumber = 1; //Assign to username for next connecting user
    public static void main( String[] args )
    {

        Gson gson =
                new GsonBuilder().registerTypeAdapterFactory(new AutoMatterTypeAdapterFactory()).create();
        
        staticFiles.location("/public"); //index.html is served at localhost:4567 (default port)
        staticFiles.expireTime(600);
        webSocket("/chat", ChatWebSocketHandler.class);
        init();
	    
        get("/ping", (req, res) -> "OK");
        get("/hello", (req, res) -> "Hello World");
        get("/login", 		LoginController.serveLoginPage);
        post("/login",      LoginController.handleLoginPost);
        //post("/login",(req, res) -> "logined");
        post("/logout",     LoginController.handleLogoutPost);
        get("/signup", (req, res) -> "Hypothetical signup page");
        post("/signup", UserUtil.createAccount);
        
        get("/users", printAllUsers); //leet haX0r debug


//        Test for Messaging Channels


        Handler handler = new Handler(channelStore);

        get("/channel/:id", (req, res) ->  handler.getChannel(req));
        get("/createChannel/:id/:user1/:user2", (req, res) -> handler.createChannel(req));
        get("/channelList",(req, res) -> handler.getAllChannels());
        get("/sendMessage/:channelID/:userID/:emoji",(req, res) -> handler.addMessage(req));


    };
    public static Route printAllUsers = (Request request, Response response) -> {
    	String out = "";
		for (User u : userDummy.users) {
			out = out + u.getUsername() + ' ' + u.getPassword() + '\n';
		}
		return out;
	};
	//derby db
}
