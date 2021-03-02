package EmojiVerse;
import static spark.Spark.*;

import EmojiVerse.login.LoginController;
import EmojiVerse.user.UserDummy;

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
    }
}
