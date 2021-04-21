import logo from './logo.svg';
import './App.css';
import React, { useState } from 'react';
import {
  BrowserRouter as Router,
  Route,
  Switch,
} from 'react-router-dom';
import {Login} from './components/authentication/login';
import {SignUp} from './components/authentication/signup';
import {Chat} from './components/chats/chat';
import {ChatList} from './components/chats/chatList';
import {Profile} from './components/user_interactions/profile';
import {Store} from './components/user_interactions/store';

function App() {
  return (
  <Router>
    <div className="App">
      <header className="App-header">
        <Switch>
			<Route path = "/chat" component = {Chat}/>
			<Route exact path = "/" component = {Login}/>
			<Route path = "/signup" component = {SignUp}/>
			<Route path = "/chatList" component = {ChatList}/>
			<Route path = "/profile" component = {Profile} />
			<Route path = "/store" component = {Store} />
		</Switch>
      </header>
    </div>
   </Router>
  );
}

export default App;
