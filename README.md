# EmojiVerse

EmojiVerse is an exciting new social media network that allows you to connect
with your friend using the power of emoji. By using these fun and friendly 
characters you can ensure to have the most fun. 

## Final Implementation instructions

To start up the server run App.java in EmojiVerse/src/main/java/EmojiVerse/App.java
The front end can be initialized by running **yarn start** at the react directory EmojiVerse/src/main/react.
Make sure to install all of the dependencies before running react using **yarn install**. The dependencies are managed automatically by yarn.lock

You should also create a mysql database and user of your choice. The database name, user, and password must be saved in envrioment variables to allow the server to connect to the database. Sample variables are shown. 
    EVERSEDB_NAME="emojiverse"
    EVERSEDB_USER="root"
    EVERSEDB_PASS=""
Afterwards you should run schema.sql to create the database structure.

The implementation should go straight to a login page but there is a hyperlink to sign up. Click that.
Once you sign up, given that you don't have the same username and email as another user, you will head straight to the login page and have a chance to login. Once you pass you can go to your profile to add friends or go to a different page to chat with a friend on the app. There is only one rule ..... EMOJIS ONLY.

In addition we have also added an emoji store in our final implementation and some more interesting features. The emoji store allows users to buy emojis that they don't have but be wary that the emojis are sorted in ascending order. You may find a cheap emoji at the top of the screen but not at the bottom. Additionally, we have categorized the emojis so they are easier to visualize on your emoji keyboard. The categories are as follows : people emojis, animals and nature emojis, food and sports emojis, objects emojis, symbols and flags emojis, and travel emojis. When you register you will start out with a set amount of emojis for each category and earn emoji coins to win your sought after emojis. The way you earn the emoji coins is one coin per message you send to a friend. This is not an inviation to spam .... or is it? ;). Please note that in order to load the emojis to your store you must uncomment the code under componentDidMount in chat.js. We apologize if there is any inconvenience.



