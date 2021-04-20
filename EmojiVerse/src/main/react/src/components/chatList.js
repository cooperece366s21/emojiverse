import React, { useState} from 'react';
import { Form, Input, Button } from 'semantic-ui-react';
import 'semantic-ui-css/semantic.min.css';
import {Redirect} from 'react-router-dom';

export const ChatList = ()  => {
  const[verified,setVerified] = useState(false)
  const[chatName,setChatName] = useState('')
  const[users,setUsers] = useState('')
  const[chatNames,setChatNames] = useState('')
  const username = localStorage.getItem("username")
  const chat_names = localStorage.getItem("chat_names").split("$")
  return(
  <Form>
	<Form.Field className = 'white-box'>
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
		
		<Button basic color = 'blue' onClick = {async () => {
          const user = username;
		  const user_list = users;
		  const chat_name = chatName
		
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
			setVerified(true)
			chat_names.push(chat_name)
			response.json().then(data=>{
				console.log(data)
				localStorage.setItem("chat_names",data.chat_names);
				window.location.replace("http://localhost:3000/chatList")
			});
				
            }
			else
			{
				console.log("not found")
			
		}}}> Create Chat</Button>

		<h1>{chat_names.map(name=>
			<Form.Field>
			<Button onClick = {async () => {
          const chat = name
		  const chat_name = name.split(" participants: ")[0]
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
			setVerified(true)
			response.json().then(data=>{
				console.log(data)
				localStorage.setItem("messages",data.messages);
				localStorage.setItem("usernames",data.usernames);
				localStorage.setItem("chat",chat);
				window.location.replace("http://localhost:3000/chat")
			});
				
            }
			else
			{
				console.log("not found")
			
		}}}>{name}</Button>
			<Button>Remove</Button>
			</Form.Field>
		)}</h1>
	</Form.Field>
   </Form>
  )
  
  }
