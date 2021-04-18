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

function App() {
  return (
  <Router>
    <div className="App">
      <header className="App-header">
        <Switch>
			<Route exact path = "/" component = {Chat}/>
			<Route path = "/login" component = {Login} />
			<Route path ="/signup" component = {SignUp} />
		</Switch>
      </header>
    </div>
   </Router>
  );
}

export default App;

