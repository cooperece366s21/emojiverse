# EmojiVerse

EmojiVerse is an exciting new social media network that allows you to connect
with your friend using the power of emoji. By using these fun and friendly 
characters you can ensure to have the most fun. 


## Prototype instructions
  * Commands about users
    * curl -d "username=test" -d "password=password" -X POST http://localhost:4567/login
    * curl -s localhost:4567/users
  * Commands about chatting channels
    * curl -s localhost:4567/channelList
    * curl -s localhost:4567/createChannel/:id/:user1/:user2
    * curl -s localhost:4567/channel/:id
    * curl -s localhost:4567/sendMessage/:channelID/:userID/:emoji
  * Commands about emojis
    * curl -s localhost:4567/emojis
    * curl -s localhost:4567/emoji/:id
  * Commands about friends <i>(assigning id to friend)</i>
    * curl -s localhost:4567/getFriendsList/:username
    * curl -s localhost:4567/addFriends/:id/:username/:friend_username
    * curl -s localhost:4567/getFriendsPhotos/:username
    * curl -s localhost:4567/blockFriends/:id/:username/:friend_username
    * curl -s localhost:4567/getBlockedList/:username

## Initial Implementation instructions

To start up your commands run App.java in https://github.com/cooperece366s21/emojiverse/blob/main/EmojiVerse/src/main/java/EmojiVerse/App.java
and yarn start at the react drectory concurrently here : https://github.com/cooperece366s21/emojiverse/tree/main/EmojiVerse/src/main/react.
Make sure to install all of the dependencies before running react. The dependencies can be found here : https://github.com/cooperece366s21/emojiverse/blob/main/EmojiVerse/src/main/react/README.md.

Additionally you should create a mysql database and put in JDBC_URL, USERNAME, and PASSWORD for your database wherever you find these words.
Afterwards you should run schema.sql to create the database structure.

The implementation should go straight to a login page but there is a hyperlink to sign up. Click that.
Once you sign up, given that you don't have the same username and email as another user, you will head straight to the login page and have a chance to login. Once you pass you can go to your profile to add friends or go to a different page to chat with a friend on the app. There is only one rule ..... EMOJIS ONLY.



