import React from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import { Switch, Route, Link, BrowserRouter as Router } from 'react-router-dom'
import UserForm from './components/Users/UserForm'
import Home from "./components/Home/Home";
import Rooms from "./components/Rooms/Rooms"

function App() {
  return (
    <div>
      <Router>
        <Switch>
          <Route exact path="/" component={Home} />
          <Route exact path="/rooms" component={Rooms} />
          <Route path="/users/add" component={UserForm} />
        </Switch>
      </Router>
    </div>
  );
}

export default App;
