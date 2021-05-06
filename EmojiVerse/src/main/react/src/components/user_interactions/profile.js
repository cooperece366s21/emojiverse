import React, { useState} from 'react';
import {Form, Input, Button, FormField} from 'semantic-ui-react';
import 'semantic-ui-css/semantic.min.css';
import {Redirect} from 'react-router-dom';
import {NavBar} from '../navbar/navbar_implementation'
import api from '../../services/api'

export const Profile = ()  => {
  const username = localStorage.getItem("username")

  const[verified,setVerified] = useState(false)
  const[friend_username,setFriendUsername] = useState('')
    const[friends, setFriends] = useState(localStorage.getItem("friends").split(","))

  return (
  
    <Form className = "login-container">
	<NavBar/>
        <div className="profContainer">
            <div className="userInfo">
                <h1>{username}</h1>
            </div>

            <div className="friends">
                <h3>My Friends:</h3>
                <div className="friendsList"> {friends == ""? console.log(friends): friends.map((name,key)=>{
                    return<div>
                            <h3>{name}</h3>
                            <Button onClick = {async() => api.removeFriend(username,name, function () {

                                setFriends(localStorage.getItem("friends").split(","))
                            })} >Remove</Button>
                    </div>
                })}
                </div>
                <Form.Field>
                    <Input

                        placeholder="Enter Friend's Username"
                        value={friend_username}
                        onChange={event => setFriendUsername(event.target.value)}
                    />
                </Form.Field>
                <Form.Field>
                    <Button onClick = {async() => {
                        api.addFriend(username,friend_username,function(){
                            setFriends(localStorage.getItem("friends").split(","))
                            setFriendUsername("");
                        })
                    }}>Add Friend</Button>
                </Form.Field>
            </div>
        </div>

	 </Form>
	  )
}