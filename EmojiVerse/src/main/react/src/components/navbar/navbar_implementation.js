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
		this.state = {navbarOpen: false, test : ''};
		this.handleNavbar = this.handleNavbar.bind(this);
		
	}
	
	
	handleNavbar = () => {
		this.setState({ navbarOpen: !this.state.navbarOpen });
	}
	render() {
		return (
		<>
			
			<Navbar 
			navbarState={this.state.navbarOpen} 
			handleNavbar={this.handleNavbar}
			
			
			/>
			<GlobalStyles />
		</>
		)
	}
  }


function getEmojiCoins(username){
    api.loadEmojiCoins(username)
}
