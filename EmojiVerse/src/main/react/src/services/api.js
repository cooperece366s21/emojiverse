

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
				  localStorage.setItem("friends",data.friends);
				  // window.location.replace("http://localhost:3000/profile")
				  callback()
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
				localStorage.setItem("chat_names",data.chat_names)

				// Direct to the chattting page after create a new chat
				getMessages(chat_name, chat_name.split(" participants: ")[0])
			});
				
            }
			else
			{
				console.log("not found")
			

		}}
		
export async function populateEmojiStore(peopleEmojis : string , category : string, price : int)
{
		    const response = await fetch("/populateEmojiStore", {
            method: "POST",
            headers: {
              "Content_Type": "application/json"
            },
            body:
              JSON.stringify({
			  PEOPLE_EMOJIS: peopleEmojis, Category : category, Price : price})
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

export async function getEmojiStore(username : string)
{
		    const response = await fetch("/getEmojisFromEmojiStore", {
            method: "POST",
            headers: {
              "Content_Type": "application/json"
            },
            body:
              JSON.stringify({
			  username : username})
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

	
let exports = {
    addFriend,
	removeFriend,
	getMessages,
	createNewChat,
	populateEmojiStore,
	removeChat,
	sendMessage,
	getEmojiStore,

}

export default exports