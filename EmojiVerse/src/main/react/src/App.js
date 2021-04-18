import logo from './logo.svg';
import './App.css';
import React, { useState } from 'react';
import {
  Router,
  Route,
  Switch,
} from 'react-router';
import {Login} from './components/login';
import {SignUp} from './components/signup';

function App() {
  return (
  <Router>
    <div className="App">
      <header className="App-header">
        <Switch>
			<Route exact path = "/" component = {Login} />
			<Route path = "/signup" component = {SignUp} />
		</Switch>
      </header>
    </div>
   </Router>
  );
}

export default App;
