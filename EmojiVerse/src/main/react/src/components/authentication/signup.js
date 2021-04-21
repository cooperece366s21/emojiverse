import React, { useState} from 'react';
import { Form, Input, Button } from 'semantic-ui-react';
import 'semantic-ui-css/semantic.min.css';
import {Redirect} from 'react-router-dom';

export const SignUp = ()  => {
  const [username, setUsername] = useState(''); //  Empty String
  const [user_password,setPassword] = useState('')
  const [title,setTitle] = useState('')
  const [email,setEmail] = useState('')
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
        <Input
        placeholder="Enter Email"
        value={email}
        onChange={event => setEmail(event.target.value)}
        />
      </Form.Field>
      <Form.Field>
	  
        
		<Button onClick= {async () => {
          const user = username;
		  const new_password = user_password;
		  const new_email = email;
          const response = await fetch("/signup", {
            method: "POST",
            headers: {
              "Content_Type": "application/json"
            },
            body:
              JSON.stringify({
			  username: user,user_password:new_password,email:new_email})
            })
          if (response.ok) {
            console.log("Response Worked! ");
			setVerified(true)
			window.location.replace("http://localhost:3000/")
           
            
          }
          else {
            console.log("not found")
            
          }
		  
		  
		  
        }}>
        Sign Up</Button>
		
		
      </Form.Field>
	  
	  <p className = 'default'>Please enter a username, password, and email. If you are a returning user go to the sign in page. 
	  Two usernames or emails cannot be used at the same time so both must be unique. It is preferable to label your username as your email.
	  You can later set your public name to something different.</p>
    </Form>
  );
};