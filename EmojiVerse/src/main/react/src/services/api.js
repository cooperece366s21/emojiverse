
          
export async function addFriend(user : string , friend_user : string)
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
				  window.location.replace("http://localhost:3000/profile")
			});
		  }
		  else
		  {
			  console.log("not found")
			
		}
	}

export async function removeFriend(user : string , friend_user : string)
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
				  window.location.replace("http://localhost:3000/profile")
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
				
				localStorage.setItem("chat",chat);
				
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
				localStorage.setItem("chat_names",data.chat_names);
				window.location.replace("http://localhost:3000/chatList")
			});
				
            }
			else
			{
				console.log("not found")
			
		}
}

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
			window.location.replace("http://localhost:3000/chatList")
		});
	}
	else {
		console.log("Error removing" + chat_name)
	}
}
	
let exports = {
    addFriend,
	removeFriend,
	getMessages,
	createNewChat,
	removeChat
}

export default exports