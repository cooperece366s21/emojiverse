import React, { useState} from 'react';
import { Form, Input, Button } from 'semantic-ui-react';
import 'semantic-ui-css/semantic.min.css';
import {Redirect} from 'react-router-dom';

export const Login = ()  => {
  const [username, setUsername] = useState(''); //  Empty String
  const [user_password,setPassword] = useState('')
  const [title,setTitle] = useState('')
  const[verified,setVerified] = useState(false)
  
  return (
    <Form className = "login-container">
	  <h1>emojiverse</h1>
      <Form.Field>
        <Input
        placeholder="Enter Username"
		
        value={username}
        onChange={event => setUsername(event.target.value)}
        />
      </Form.Field>
	  <Form.Field>
        <Input
		type = "password"
        placeholder="Enter Password"
        value={user_password}
        onChange={event => setPassword(event.target.value)}
        />
      </Form.Field>
      <Form.Field>
	  
        
		<Button onClick = {async () => {
		const user = username;
		const new_password = user_password;
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
				setVerified(data.authorized)
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

        }}> Sign In </Button>
		
		<a href="http://localhost:3000/signup">Sign Up</a>
      </Form.Field>
	  
	  
	  <p className = 'default'>Please enter a username (an email) and a password. If you do not already have an account sign up but if you do have one sign in. Note that passwords are not stored and 
	  any and all actions made on this webapp will be anonymous. It is preferred to use an unidentifying email for username. </p>
    </Form>
  );
};
