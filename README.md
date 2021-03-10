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
  * Commands about friends <i>(assigning id to friend)</i>
    * curl -s localhost:4567/getFriendsList/:username
    * curl -s localhost:4567/addFriends/:id/:username/:friend_username
    * curl -s localhost:4567/getFriendsPhotos/:username
    * curl -s localhost:4567/blockFriends/:id/:username/:friend_username
    * curl -s localhost:4567/getBlockedList/:username



