import React from "react";
import { Switch, Route, withRouter } from "react-router-dom";
import { ThemeProvider } from "@material-ui/styles";

import theme from "./theme";
import "./App.scss";

import { Standard as StandardLayout } from "./layouts";

import { connect } from "react-redux";
import { selectAvaibleRoutes } from "./redux/routes/routes.selectors"
import { getAuthentication } from "./auth";
import { setCurrentUser } from "./redux/user/user.actions";
import { selectLoggedIn } from "./redux/user/user.selectors";

class App extends React.Component {
  constructor(props) {
    super(props)
  }

  async componentDidMount() {
    const {setCurrentUser} = this.props;

    const authentication = await getAuthentication();
    setCurrentUser(authentication);
  }

  render() {
    const routes = this.props.routes
    return (
      <ThemeProvider theme={theme}>
        <StandardLayout>
          <Switch>
            {routes.map((route) => (
              <Route
                key={route.title}
                exact={route.exact}
                path={route.href}
                render={() => route.view}
              />
            ))}
          </Switch>
        </StandardLayout>
      </ThemeProvider>
    )
  }
};

const mapStateToProps = (state) => ({
  loggedIn: selectLoggedIn(state),
  routes: selectAvaibleRoutes(state),
});

const mapDispatchToProps = dispatch => ({
  setCurrentUser: user => dispatch(setCurrentUser(user))
})

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(App));
