import React, { useState, Component} from 'react';
import { Form, Input, Button } from 'semantic-ui-react';
import 'semantic-ui-css/semantic.min.css';
import {Redirect} from 'react-router-dom';
import {NavBar} from '../navbar/navbar_implementation'
import api from '../../services/api';

/*import { Box, Image, Text, SimpleGrid } from "@chakra-ui/core";*/


const username = localStorage.getItem("username")


export class Store extends React.Component{
  state=
  {
	  emojis : ''
  }
  
componentDidMount()
  {
	  api.getEmojiStore(username)
  }	  
  
  render(){
  return (
    <Form className = "login-container">
	<NavBar/>
	  <h1>emojiverse store</h1>
	  <h2 className = "emoji-store">People Emojis</h2>
	  <h1>{this.state.emojis}</h1>
	  <h1>{localStorage.getItem("PEOPLE_EMOJIS").split(",").map(emoji=> 
	  <Button>{emoji.split(" = ")[0]}
		  <h3>{emoji.split(" = ")[1]}<i class="bitcoin icon"></i></h3></Button>)}</h1>
		
		
	  <h2 className = "emoji-store">Animals and Nature Emojis</h2>
	  
	   <h2>{localStorage.getItem("ANIMALS_NATURE_EMOJIS").split(",").map(emoji=> 
	  <Button>{emoji.split(" = ")[0]}
		  <h3>{emoji.split(" = ")[1]}<i class="bitcoin icon"></i></h3></Button>)}</h2>
		  
	   <h2 className = "emoji-store">Food and Sports Emojis</h2>
	  
	   <h2>{localStorage.getItem("FOOD_SPORTS_EMOJIS").split(",").map(emoji=> 
	  <Button>{emoji.split(" = ")[0]}
		  <h3>{emoji.split(" = ")[1]}<i class="bitcoin icon"></i></h3></Button>)}</h2>
		  
		  <h2 className = "emoji-store">Objects Emojis</h2>
	  
	   <h2>{localStorage.getItem("OBJECTS_EMOJIS").split(",").map(emoji=> 
	  <Button>{emoji.split(" = ")[0]}
		  <h3>{emoji.split(" = ")[1]}<i class="bitcoin icon"></i></h3></Button>)}</h2>
		  
		    <h2 className = "emoji-store">Symbols and Flags Emojis</h2>
	  
	   <h2>{localStorage.getItem("SYMBOLS_FLAGS_EMOJIS").split(",").map(emoji=> 
	  <Button>{emoji.split(" = ")[0]}
		  <h3>{emoji.split(" = ")[1]}<i class="bitcoin icon"></i></h3></Button>)}</h2>
		  
		    <h2 className = "emoji-store">Travel Emojis</h2>
	  
	   <h2>{localStorage.getItem("TRAVEL_PLACES_EMOJIS").split(",").map(emoji=> 
	  <Button>{emoji.split(" = ")[0]}
		  <h3>{emoji.split(" = ")[1]}<i class="bitcoin icon"></i></h3></Button>)}</h2>
	</Form>
	  )
  }
}
