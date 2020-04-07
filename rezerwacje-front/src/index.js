import React from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import { Switch, Route, Link, BrowserRouter as Router } from 'react-router-dom'
import App from './App'
import UserForm from './users/UserForm'
import Success from './users/Success'

const routing = (
  <Router>
    <div>
      <Switch>
        <Route exact path="/" component={App} />
        <Route path="/users" component={UserForm} />
      </Switch>
      </div>
  </Router>
)

ReactDOM.render(routing, document.getElementById('root'));
