package EmojiVerse;
import static spark.Spark.*;

import EmojiVerse.chatChannel.Channel;
import EmojiVerse.chatChannel.chatChennelImp;
import EmojiVerse.login.LoginController;
import EmojiVerse.user.User;
import EmojiVerse.user.UserDummy;
import EmojiVerse.user.UserUtil;
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
    static Map<Session, String> userUsernameMap = new ConcurrentHashMap<>();
    static int nextUserNumber = 1; //Assign to username for next connecting user
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
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
        Channel test = new chatChennelImp().get("1");
        get("/channel1message", (req, res) -> test.getMessages());


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
