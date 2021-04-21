import React, { useState} from 'react';
import { Form, Input, Button } from 'semantic-ui-react';
import 'semantic-ui-css/semantic.min.css';
import {Redirect} from 'react-router-dom';
import {NavBar} from '../navbar/navbar_implementation'

export const Store = ()  => {
  const [username, setUsername] = useState(''); //  Empty String
  const [user_password,setPassword] = useState('')
  const [title,setTitle] = useState('')
  const[verified,setVerified] = useState(false)
  
  return (
    <Form className = "login-container">
	<NavBar/>
	  <h1>emojiverse store</h1>
	</Form>
	  )
}