import React, { Component } from 'react'
import styled from 'styled-components'
import emojione from 'emojione'
import PropTypes from 'prop-types'
import { Form, Input, Button } from 'semantic-ui-react';
import api from '../../services/api';
//import {Message} from '../message/message';
import Picker from 'react-emojipicker'
// listing packages like this is a bit ridiculous

/* 
* A message object ought to contain the following information:
*	Originating user 
*	Emoji content
*	Timestamp
* our messaging list should consist of a list structure to which 
* new messages can be appended 
*/

const username = localStorage.getItem("username")

export default class Chat extends Component {
	
	constructor (props) {
		super(props) //wtf
		this.state = {
			isFetching: true, // when is fetching==true, grey out message screen
			messages: [],
			message: "" // rename to something less ambiguous 
		}
	}
	
	componentDidMount() {
		this.loadMessages()
		// set update timer here
		// this.timer = setInterval(() => this.loadMessages(), 5000)
	}
	
	componentWillUnmount() {
		// remove stuff after the component is unmounted 
		// clearInterval(this.timer)
		// this.timer = null
	}
	
	async loadMessages () {
		console.log("Fetching fresh messages from server")
		// I can't add my own function to the api for whatever reason
		// always complains that X is not a function 
		// had to write my own thing. This shouldn't really exist
		//console.log(api.getMessages(chat.split(" participants: ")[0]))
		//this.setState({ messages: api.getMessages(chat, chat.split(" participants: ")[0])})
		// gonna go crazy with these chat names lol
		const res = await fetch("/getMessages", {
			method: "POST",
			headers: {
			"Content_Type": "application/json"
			},
			body:
			JSON.stringify({
			chatName: this.props.chatName})
		});
		// console.log(res.json())
		res.json().then( data => {
			console.log(data)
			this.setState({ messages: data.message_info })
			console.log(this.state.messages)
			this.setState({ isFetching: false })
		})
	}
	
	logEmoji (emoji) {
		console.log(emoji)
		this.setState({ message: this.state.message += emoji.unicode })
		// temporary function that doesn't deal with image emojis yet
	}
	
	// this absurdness abounds
	async sendMessage () {
		// this ought to be mapped out in the API
		const myMessage = this.state.message
		console.log(myMessage)
		const res = await fetch("/addMessage", {
			method: "POST",
			headers: {
			"Content_Type": "application/json"
			},
			body:
			JSON.stringify({ chatName: this.props.chatName, username : username, message : myMessage })
		})
		if (res.ok) {
			console.log("Message sent successfully")
			this.loadMessages()
		} else {
			console.log("Message failed to send")
		}
		this.setState({ message: "" })
	}

	render () {
		return (
			<>
			<Form.Field className = "white-box">
			<h1>{this.props.chatName}</h1>
			<div className="chatWindow">
				// this ought to have a spinner while this.state.isFetching==true
				{this.state.messages.map((data) => <p>{data}</p>)}
			</div>
			
			<div>
				<Picker onEmojiSelected={this.logEmoji.bind(this)} />
			</div>
			<p>{this.state.message}</p>
			<Input type="text" placeholder="Enter Message" value={this.state.message}/>
			<Button onClick={() => {this.sendMessage()}} >Send</Button>
			</Form.Field>
			</>
		)
	}
}
/*
export const Chat = () =>{
	return <ChatClass />
}*/
