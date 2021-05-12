

export async function addFriend(user : string , friend_user : string, callback)
{
	const response = await fetch("/addFriend", {
		method: "POST",
		headers: {
			"Content_Type": "application/json"
			},
			body:
              JSON.stringify({
			  username: user, friend_username : friend_user})
		  })
		  if (response.ok) {
			  console.log("Response Worked! ");
			  
			  response.json().then(data=>{
				  console.log(data)
				  if(data.verified!== false) {
					  localStorage.setItem("friends",data.friends);
				  // window.location.replace("http://localhost:3000/profile")
					  callback()
				  }else{
				  	alert("Warning: No registered user with given input name!")
				  }
				  
			});
		  }
		  else{
			  console.log("not found")
		  }
	}

export async function removeFriend(user : string , friend_user : string, callback)
{
	const response = await fetch("/removeFriend", {
		method: "POST",
		headers: {
			"Content_Type": "application/json"
			},
			body:
              JSON.stringify({
			  username: user, friend_username : friend_user})
		  })
		  if (response.ok) {
			  console.log("Response Worked! ");
			  
			  response.json().then(data=>{
				  console.log(data)
				  localStorage.setItem("friends",data.friends);
				  callback()
			});
		  }
		  else
		  {
			  console.log("not found")
			
		}
	}	
	

// we need a get messages after time function
export async function getMessages(chat : string , chat_name : string)
{
	
          
          const response = await fetch("/getMessages", {
            method: "POST",
            headers: {
              "Content_Type": "application/json"
            },
            body:
              JSON.stringify({
			  chatName: chat_name})
		  })
			if (response.ok) {
            console.log("Response Worked! ");
			
			response.json().then(data=>{
				console.log(data)
				localStorage.setItem("message_info",data.message_info);
				
				localStorage.setItem("chat",chat_name);
				
				window.location.replace("http://localhost:3000/chat")
				// view updating should NOT occur in the api at all
				// the API should be purely functional, just return requested data
				// no global state changes 
			});
				
            }
			else
			{
				console.log("not found")
			
		
		}
}
	
export async function createNewChat(user : string , user_list : string, chat_name : string)
{
	const response = await fetch("/new", {
            method: "POST",
            headers: {
              "Content_Type": "application/json"
            },
            body:
              JSON.stringify({
			  username: user,users:user_list, chatName: chat_name})
		  })
			if (response.ok) {
            console.log("Response Worked! ");
			
			
			response.json().then(data=>{
				console.log(data)
				if(data.verified !== false)
				{
					localStorage.setItem("chat_names",data.chat_names)

				// Direct to the chattting page after create a new chat
					getMessages(chat_name, chat_name.split(" participants: ")[0])
				}
				
			});
				
            }
			else
			{
				console.log("not found")
			

		}}
		
export async function populateEmojiStore(peopleEmojis : string , category : string, price : int, username : string)
{
		    const response = await fetch("/populateEmojiStore", {
            method: "POST",
            headers: {
              "Content_Type": "application/json"
            },
            body:
              JSON.stringify({
			  PEOPLE_EMOJIS: peopleEmojis, Category : category, Price : price, username : username})
		  })
			if (response.ok) {
            console.log("Response Worked! ");
			
			response.json().then(data=>{
				console.log(data)
				localStorage.setItem("emoji_list",data.emoji_list);
			
				
			});
			return 200;
			
            }
			else
			{
				console.log("not found")
				return 404;
				
			
}}

export async function getEmojiStore(user : string)
{
		    const response = await fetch("/getEmojisFromEmojiStore", {
            method: "POST",
            headers: {
              "Content_Type": "application/json"
            },
            body:
              JSON.stringify({
			  username: user,users:'user_list', chatName: 'chat_name'})
		  })
			if (response.ok) {
            console.log("Response Worked! ");
			
			response.json().then(data=>{
				console.log(data)
				localStorage.setItem("PEOPLE_EMOJIS",data.PEOPLE_EMOJIS)
				localStorage.setItem("ANIMALS_NATURE_EMOJIS", data.ANIMALS_NATURE_EMOJIS)
				localStorage.setItem("FOOD_SPORTS_EMOJIS", data.FOOD_SPORTS_EMOJIS)
				localStorage.setItem("OBJECTS_EMOJIS", data.OBJECTS_EMOJIS)
				localStorage.setItem("SYMBOLS_FLAGS_EMOJIS", data.SYMBOLS_FLAGS_EMOJIS)
				localStorage.setItem("TRAVEL_PLACES_EMOJIS", data.TRAVEL_PLACES_EMOJIS)
				return data.ANIMALS_NATURE_EMOJIS
			
				
			});
	
			
            }
			else
			{
				console.log("not found")
				
			
}}
		



export async function removeChat(user : string ,chat_name : string){
	const response = await fetch("/removeChat", {
		method: "POST",
		headers: {
			"Content_Type": "application/json"
		},
		body:
			JSON.stringify({
				username: user, chatName: chat_name})
	})
	if (response.ok) {
		console.log("Removed chat channel -"+ chat_name);

		response.json().then(data=>{
			console.log(data)
			localStorage.setItem("chat_names",data.chat_names);
			// window.location.replace("http://localhost:3000/chatList")
		});
	}
	else {
		console.log("Error removing" + chat_name)
	}
}

export async function sendMessage(chat_name: string, userName: string, Message: string,callback, component, doc){

		const response = await fetch("/addMessage", {
			method: "POST",
			headers: {
				"Content_Type": "application/json"
			},
			body:
				JSON.stringify({
					chatName: chat_name, username : userName, message : Message})
		})

		if (response.ok) {
			console.log("Response Worked! ");

			response.json().then(data=>{
				console.log(data)
				localStorage.setItem("message_info",data.message_info);
				callback(arguments[4], arguments[5]);
			});
		}
		else{
			console.log("not found")

		}
}

export async function updateMessages(chat_name : string)
{
	const response = await fetch("/getMessages", {
		method: "POST",
		headers: {
			"Content_Type": "application/json"
		},
		body:
			JSON.stringify({
				chatName: chat_name})
	})
	if (response.ok) {
		console.log("Response Worked! ");

		response.json().then(data=>{
			console.log(data)
			localStorage.setItem("message_info",data.message_info);

			localStorage.setItem("chat",chat_name);

		});

	}
	else {
		console.log("not found")


	}
}


	
export async function getUserEmojis(user : string)
{
		const response = await fetch("/getUserEmojis", {
		method: "POST",
		headers: {
			"Content_Type": "application/json"
		},
		body:
			JSON.stringify({
				username: user, chatName: 'chat_name'})
	})
			if (response.ok) {
            console.log("Response Worked! ");
			
			response.json().then(data=>{
				console.log(data)
				localStorage.setItem("USER_PEOPLE_EMOJIS",data.PEOPLE_EMOJIS)
				localStorage.setItem("USER_ANIMALS_NATURE_EMOJIS", data.ANIMALS_NATURE_EMOJIS)
				localStorage.setItem("USER_FOOD_SPORTS_EMOJIS", data.FOOD_SPORTS_EMOJIS)
				localStorage.setItem("USER_OBJECTS_EMOJIS", data.OBJECTS_EMOJIS)
				localStorage.setItem("USER_SYMBOLS_FLAGS_EMOJIS", data.SYMBOLS_FLAGS_EMOJIS)
				localStorage.setItem("USER_TRAVEL_PLACES_EMOJIS", data.TRAVEL_PLACES_EMOJIS)
				
			
				
			});
	
			
            }
			else
			{
				console.log("not found")
}}

export async function loadChatNames(user : string, new_password : string){
	const get_response_chats = await fetch("/chats", {
            method: "POST",
            headers: {
              "Content_Type": "application/json"
            },
            body:
              JSON.stringify({
			  username: user,user_password:new_password})
            })
			if (get_response_chats.ok) {
            console.log("Response Worked! ");
			get_response_chats.json().then(data=>{
				console.log(data)
				
				
				
					
				localStorage.setItem('chat_names', data.chat_names);
				
				});
          }
}

export async function loadFriends(user : string, new_password : string){
	const get_response_friends = await fetch("/getFriend", {
            method: "POST",
            headers: {
              "Content_Type": "application/json"
            },
            body:
              JSON.stringify({
			  username: user,user_password:new_password})
            })
			if (get_response_friends.ok) {
            console.log("Response Worked! ");
			get_response_friends.json().then(data=>{
				console.log(data)
				
					
				localStorage.setItem('friends', data.friends);
				
				});
          }
}

export async function authenticateCredentials(user : string, new_password : string){
	const post_response = await fetch("/login", {
            method: "POST",
            headers: {
              "Content_Type": "application/json"
            },
            body:
              JSON.stringify({
			  username: user,user_password:new_password})
            })
		 
		 
		  
          if (post_response.ok) {
            console.log("Response Worked! ");
			post_response.json().then(data=>{
				
				console.log(data)
				if(data.authorized===true)
				{
					
					localStorage.setItem('access_token', data.access_token);
					localStorage.setItem('username', data.username);
					window.location.replace("http://localhost:3000/chatList")
				}
				});
          }
          else {
            console.log("not found") 
          }
}

export async function loadEmojiCoins(user : string){
	const get_response = await fetch("/getEmojiCoins", {
            method: "POST",
            headers: {
              "Content_Type": "application/json"
            },
            body:
              JSON.stringify({
			  username: user})
            })
			if (get_response.ok) {
            console.log("Response Worked! ");
			get_response.json().then(data=>{
				console.log(data)
				
					
				localStorage.setItem('emoji_coins', data.emoji_coins);
				
				});
          }
		  else
		  {
			  console.log('not found');
		  }
}

export async function buyEmoji(user : string, emoji : string){
	const get_response = await fetch("/buyEmoji", {
            method: "POST",
            headers: {
              "Content_Type": "application/json"
            },
            body:
              JSON.stringify({
			  username: user, emoji : emoji})
            })
			if (get_response.ok) {
            console.log("Response Worked! ");
			get_response.json().then(data=>{
				console.log(data)
				if(data.verified===true)
				{
					localStorage.setItem('emoji_coins', data.emoji_coins);
					window.location.replace("http://localhost:3000/store");
				}
			});
          }
		  else
		  {
			  console.log('not found');
		  }
}


let exports = {
     addFriend,
	removeFriend,
	getMessages,
	createNewChat,
	populateEmojiStore,
	removeChat,
	sendMessage,
	getEmojiStore,
	getUserEmojis,
	loadChatNames,
	loadFriends,
	authenticateCredentials,
	loadEmojiCoins,
	buyEmoji,
	updateMessages

}

export default exports
