import React, { Component } from 'react'
import Navbar from "./Navbar";
import GlobalStyles from './Global';
import api from '../../services/api'


const username = localStorage.getItem("username")
let timer = null;

export class NavBar extends React.Component {
	constructor(props)
	{
		super(props);
		this.state = {navbarOpen: false, emojiCoins : localStorage.getItem("emoji_coins"), test : ''};
		this.handleNavbar = this.handleNavbar.bind(this);
	}
	
	
	componentWillUpdate(nextProps, nextState) {
		if (nextState.status != this.state.status) {
			this.setState({emojiCoins : localStorage.getItem("emoji_coins")})
		}
	}
	componentDidMount() {
		
		this.setState({emojiCoins : localStorage.getItem("emoji_coins")})
  }
	handleNavbar = () => {
		this.setState({ navbarOpen: !this.state.navbarOpen });
	}
	render() {
		return (
		<>
		<h1>localStorage.getItem("emoji_coins")</h1>
			<Navbar 
			navbarState={this.state.navbarOpen} 
			handleNavbar={this.handleNavbar}
			emojiCoins = {this.state.emojiCoins}
			/>
			<GlobalStyles />
		</>
		)
	}
  }


function getEmojiCoins(username){
    api.loadEmojiCoins(username)
}
