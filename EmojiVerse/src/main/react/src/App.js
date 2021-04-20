import logo from './logo.svg';
import './App.css';
import React, { useState } from 'react';
import {
  BrowserRouter as Router,
  Route,
  Switch,
} from 'react-router-dom';
import {Login} from './components/login';
import {SignUp} from './components/signup';
import {Chat} from './chat';
import {ChatList} from './components/chatList';

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

		</Switch>
      </header>
    </div>
   </Router>
  );
}

export default App;
