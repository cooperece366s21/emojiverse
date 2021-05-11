import React, { useState} from 'react';
import { Form, Input, Button } from 'semantic-ui-react';
import 'semantic-ui-css/semantic.min.css';
import {Redirect} from 'react-router-dom';
import {NavBar} from '../navbar/navbar_implementation'
import api from '../../services/api'
import {func} from "prop-types";
import {ListContainer} from "./ListContainer";

export const ChatListPage = ()  => {
    const[verified,setVerified] = useState(false)
    const[chatName,setChatName] = useState('')
    const[users,setUsers] = useState('')
    const[chatNames,setChatNames] = useState('')
    const username = localStorage.getItem("username")
    const chat_names = localStorage.getItem("chat_names").split("$,")



    return(

      <Form>
      <NavBar/>
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

            <Button basic color = 'blue' onClick = {async () => newChat(username, users, chatName)}> Create Chat</Button>
            <div className= "chatListContainner">
                <ListContainer chatnames = {chat_names}/>
            </div>
        </Form.Field>
       </Form>
      )

  }
  
  function newChat(username, users, chatName)
  {
	api.createNewChat(username, users,chatName)
	api.getUserEmojis(username)
  }
