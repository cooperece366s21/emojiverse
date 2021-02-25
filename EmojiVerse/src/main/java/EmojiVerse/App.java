package EmojiVerse;
import static spark.Spark.*;

import EmojiVerse.login.LoginController;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        port(4567);
        get("/ping", (req, res) -> "OK");
        get("/hello", (req, res) -> "Hello World Test");
        get("/login", LoginController.serveLoginPage);
    }
}
