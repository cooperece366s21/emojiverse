import React, { useState, Component} from 'react';
import { Form, Input, Button } from 'semantic-ui-react';
import 'semantic-ui-css/semantic.min.css';
import {Redirect} from 'react-router-dom';
import {NavBar} from '../navbar/navbar_implementation'
import api from '../../services/api';
import EmojiWrapper from '../chats/chat'
import { Box, Image, Text, SimpleGrid } from "@chakra-ui/core";

const username = localStorage.getItem("username")

export default class EmojiStore extends React.Component {
  constructor (props) {
    super(props)
    this.state = {
      
	  message: "",
	  username: username
	  
    }
  }

  componentDidMount()
  {
	  {api.getEmojiStore(this.state.username)}

  }
  render()
  {
	  return(
		<h1></h1>
	  )
  }
}
export const Store = ()  => {
  const [username, setUsername] = useState(''); //  Empty String
  const [user_password,setPassword] = useState('')
  const [title,setTitle] = useState('')
  const[verified,setVerified] = useState(false)
  const emojis = localStorage.getItem("emoji_list").split(",")
  
  return (
    <Form className = "login-container">
	<NavBar/>
	  <h1>emojiverse store</h1>
	  <EmojiStore/>
	  <SimpleGrid>{emojis.map(emoji=> <Box><Button>{emoji}</Button></Box>)}</SimpleGrid>
	</Form>
	  )
}