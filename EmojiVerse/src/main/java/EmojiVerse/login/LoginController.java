package EmojiVerse.login;

import spark.*;

public class LoginController {
    public static Route serveLoginPage = (Request request, Response response) -> {
    	return "This is a hypothetical login page";
    };
}