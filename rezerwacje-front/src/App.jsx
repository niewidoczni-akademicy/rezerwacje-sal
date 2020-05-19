import React from "react";
import { Switch, Route } from "react-router-dom";
import { ThemeProvider } from "@material-ui/styles";

import theme from "./theme";
import "./App.scss";

import { Standard as StandardLayout } from "./layouts";

import { connect } from "react-redux";
import { selectAvaibleRoutes } from "./redux/routes/routes.selectors"

const App = ({ routes }) => (
  <ThemeProvider theme={theme}>
    <StandardLayout>
      <Switch>
        {routes.map((route) => (
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

const mapStateToProps = (state) => ({
  routes: selectAvaibleRoutes(state),
});

export default connect(mapStateToProps)(App);
