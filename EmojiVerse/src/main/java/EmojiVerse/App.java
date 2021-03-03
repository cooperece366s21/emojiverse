package EmojiVerse;
import static spark.Spark.*;

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
