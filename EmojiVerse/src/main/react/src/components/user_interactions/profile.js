import React, { useState} from 'react';
import { Form, Input, Button } from 'semantic-ui-react';
import 'semantic-ui-css/semantic.min.css';
import {Redirect} from 'react-router-dom';
import {NavBar} from '../navbar/navbar_implementation'
import api from '../../services/api'

export const Profile = ()  => {
  const username = localStorage.getItem("username")
  var friends = localStorage.getItem("friends")
  if(friends!=null)
  {
	  friends = localStorage.getItem("friends").split(",")
  }
  const[verified,setVerified] = useState(false)
  const[friend_username,setFriendUsername] = useState('')
  
  return (
  
    <Form className = "login-container">
	<NavBar/>
	
	  <h1>{username}</h1>
	  
	  <h3> Friend List : {friends.map(name=>
	  <li>{name}</li>)} </h3>
	  <Form.Field>
        <Input
		
        placeholder="Enter Friend's Username"
        value={friend_username}
        onChange={event => setFriendUsername(event.target.value)}
        />
      </Form.Field>
	  <Form.Field>
	  <Button onClick = {async() => api.addFriend(username,friend_username)}>Add Friend</Button>
		<Button onClick = {async() => api.removeFriend(username,friend_username)}> Remove Friend </Button>
	  </Form.Field>
	 </Form>
	  )
}