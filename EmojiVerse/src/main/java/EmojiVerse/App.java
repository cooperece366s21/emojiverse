package EmojiVerse;
import static spark.Spark.*;

import EmojiVerse.chatChannel.Channel;
import EmojiVerse.chatChannel.chatChennelImp;
import EmojiVerse.login.LoginController;
import EmojiVerse.user.User;
import EmojiVerse.user.UserDummy;
import EmojiVerse.user.UserUtil;
import spark.*;

/**
 * Hello world!
 *
 */
public class App 
{
	public static UserDummy userDummy = new UserDummy();
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        port(4567);
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
