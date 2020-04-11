import React from "react";
import { BrowserRouter, Switch, Route } from "react-router-dom";

import Drawer from "@material-ui/core/Drawer";
import CssBaseline from "@material-ui/core/CssBaseline";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Divider from "@material-ui/core/Divider";

import { AppBarLabel, Users, RecruitmentPeriods } from "../../Const";
import DrawerItems from "../DrawerItems";
import Rooms from "../Rooms/Rooms";

class Home extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      sidebarItems: [Users, RecruitmentPeriods],
    };
  }

  render() {
    return (
      <div>
        <main className={this.props.classes.content}>
          <div className={this.props.classes.toolbar} />
        </main>
        <div className={this.props.classes.root}>
          <CssBaseline />
          <AppBar position="absolute" className={this.props.classes.appBar}>
            <Toolbar>
              <Typography variant="h6" noWrap>
                {AppBarLabel}
              </Typography>
            </Toolbar>
          </AppBar>

          <Drawer
            className={this.props.classes.drawer}
            variant="permanent"
            classes={{
              paper: this.props.classes.drawerPaper,
            }}
            anchor="left"
          >
            <div className={this.props.classes.toolbar} />
            <Divider />
            <DrawerItems items={[Users, RecruitmentPeriods]} />
          </Drawer>
          <BrowserRouter>
            <Switch>
              <Route exact path="/rooms" component={Rooms} />
            </Switch>
          </BrowserRouter>
        </div>
      </div>
    );
  }
}

export default Home;
