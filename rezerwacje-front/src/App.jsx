import React from 'react';
import { Switch, Route } from 'react-router-dom';
import { ThemeProvider } from '@material-ui/styles';

import theme from './theme';
import './App.scss';

import { Standard as StandardLayout } from './layouts';
import { Routes } from './common';

const App = () => (
  <ThemeProvider theme={theme}>
    <StandardLayout>
      <Switch>
        {Routes.map((route) => (
          <Route
            key={route.title}
            exact
            path={route.href}
            render={() => route.view}
          />
        ))}
      </Switch>
    </StandardLayout>
  </ThemeProvider>
);

export default App;
