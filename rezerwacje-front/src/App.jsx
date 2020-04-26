import React from 'react';
import { Switch, Redirect, Route } from 'react-router-dom';
import { ThemeProvider } from '@material-ui/styles';

import theme from './theme';
import './App.scss';

import { Main as MainLayout } from './layouts';
import Routes from './common'

const App = () => {

  const MainRoutes = () => {
    return (
      <Switch>
        {
          Routes.map(route => 
            <Route 
              key={route.title} 
              exact 
              path={route.href} 
              render={() => route.view}
            />)
        }
      </Switch>
    );
  };

  return (
    <ThemeProvider theme={theme}>
      <Route 
        exact
        path={Routes.map(route => route.href)}
        render={() => <MainLayout children={<MainRoutes/>}/>} 
      />
    </ThemeProvider>
  );
}

export default App;
