import React, { useState} from 'react';
import { Form, Input, Button } from 'semantic-ui-react';
import 'semantic-ui-css/semantic.min.css';
import {Redirect} from 'react-router-dom';
import {NavBar} from '../navbar/navbar_implementation'
import api from '../../services/api'

export const ChatList = ()  => {
const[verified,setVerified] = useState(false)
const[chatName,setChatName] = useState('')
const[users,setUsers] = useState('')
const[chatNames,setChatNames] = useState('')
const username = localStorage.getItem("username")
const chat_names = localStorage.getItem("chat_names").split("$,")

console.log(chat_names)
// some default or previously selected element should be selected
let chat_list = chat_names.map((name) => 
	<a href="#" onClick={openChat} class="item">{name}</a>
);


function openChat() {
	console.log("pressed menu I guess")
	console.log(this)
}


return(



<Form>
<NavBar/>
	<Form.Field className = 'white-box'>
		<h1></h1>
		<h1>CHATS FOR {username}</h1>
		<h3>----------------------------------------------</h3>
		<Input
		placeholder="Enter Chat Name"
		value={chatName}
		onChange={event => setChatName(event.target.value)}
		/>
		<Input
		placeholder="Enter Users to Add"
		value={users}
		onChange={event => setUsers(event.target.value)}
		/>
		<h3>Note: you must separate each username with a comma</h3>
		
<Button basic color = 'blue' onClick = {async () => api.createNewChat(username, users,chatName)}> Create Chat</Button>



<div class="ui vertical menu">
	{chat_list}
</div>

		<div className= "chatListContainner">
			<h1>{chat_names.map(name=>
				<Form.Field>
					<Button onClick = {async () => api.getMessages(name,name.split(" participants: ")[0])}>{name.replace("$","")}</Button>
					<Button onClick={async () => api.removeChat(name.split(" participants: ")[1].split(",")[1].replace("$","").replace("]", "").trim(), name.split(" participants: ")[0])}>Remove</Button>
				</Form.Field>
			)}</h1>
		</div>
	</Form.Field>
</Form>
) //this is real fucking expensive isn't it? Fetching all the messages in when loading the chats list?

}
