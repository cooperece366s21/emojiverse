import logo from './logo.svg';
import './App.css';
import React, { useState } from 'react';
import {
  BrowserRouter as Router,
  Route,
  Switch,
} from 'react-router-dom';
import {Login} from './components/login';
import {chatChannel} from './components/chatChannel';

function App() {
  return (
  <Router>
    <div className="App">
      <header className="App-header">
        <Switch>
			<Route exact path = "/" component = {Login} />
		</Switch>
      </header>
    </div>
   </Router>
  );
}

export default App;
