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
import {ChatListPage} from './components/chats/chatListPage';
import {Profile} from './components/user_interactions/profile';
import {Store} from './components/user_interactions/store';

function App() {
  return (
  <Router>
    <div className="App">
      <header className="App-header">
        <Switch>
			<Route exact path = "/" component = {Login}/>
			<Route path = "/signup" component = {SignUp}/>
			<Route path = "/chatList" component = {ChatListPage}/>
			<Route path = "/profile" component = {Profile} />
			<Route path = "/store" component = {Store} />
		</Switch>
      </header>
    </div>
   </Router>
  );
}

export default App;
