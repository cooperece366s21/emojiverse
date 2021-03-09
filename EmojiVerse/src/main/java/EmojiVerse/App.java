package EmojiVerse;
import static spark.Spark.*;

import EmojiVerse.Handler.handler;
import EmojiVerse.chatChannel.Channel;
import EmojiVerse.chatChannel.chatChennelImp;
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
import java.util.logging.Handler;

/**
 * Hello world!
 *
 */
public class App 
{
    public static UserDummy userDummy = new UserDummy();
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


//        Dump temp test for chat channel, need to restructure a lot latter

        chatChennelImp chatChennelImp= new chatChennelImp();
        handler handler = new handler(chatChennelImp);
        get("/channel/:name", (req, res) ->  handler.getChannel(req));

        get("/createChannel/:id/:user1/:user2", (req, res) -> handler.createChannel(req));


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
