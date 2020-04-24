import React, { Component } from 'react';
import { Switch, Redirect, Route } from 'react-router-dom';
import { ThemeProvider } from '@material-ui/styles';
import PropTypes from 'prop-types';

import theme from './theme';
import './App.scss';

import { Main as MainLayout } from './layouts';
import {
  Home as HomeView,
  Rooms as RoomsView
} from './views';

const Routes = () => {
  return (
    <Switch>
      <Redirect
        exact
        from="/"
        to="/home"
      />
      <Route 
        exact
        path={["/home", "/rooms"]}
        render={() => <MainLayout/>} 
      />
    </Switch>
  );
};

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <Routes/>
    </ThemeProvider>
  );
}

export default App;
