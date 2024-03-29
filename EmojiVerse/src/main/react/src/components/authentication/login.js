import React, { useState} from 'react';
import { Form, Input, Button } from 'semantic-ui-react';
import 'semantic-ui-css/semantic.min.css';
import {Redirect} from 'react-router-dom';
import api from '../../services/api'

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
	  
        
		<Button onClick = {async () => Authenticate(username, user_password)}> Sign In </Button>
		
		<a href="http://localhost:3000/signup">Sign Up</a>
      </Form.Field>
	  
	  
	  <p className = 'default'>Please enter a username (an email) and a password. If you do not already have an account sign up but if you do have one sign in. Note that passwords are not stored and 
	  any and all actions made on this webapp will be anonymous. It is preferred to use an unidentifying email for username. </p>
    </Form>
  );
};

function Authenticate(username,user_password){
    api.loadChatNames(username, user_password)
	api.loadFriends(username, user_password)
	api.authenticateCredentials(username, user_password)
	api.loadEmojiCoins(username)
}
