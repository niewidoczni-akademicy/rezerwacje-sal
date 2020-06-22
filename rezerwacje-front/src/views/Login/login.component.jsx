import React from "react";
import {
  Button,
  Card,
  Grid,
  TextField,
  Typography,
  withStyles,
} from "@material-ui/core";
import { withRouter } from "react-router-dom";
import { connect } from "react-redux";
import { setCurrentUser } from "../../redux/user/user.actions";
import { getAuthentication } from "../../auth";

class Login extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  handleLoginError = () => {
    this.setState({ error: "Invalid username or password" });
  };

  handleSubmit = async (event) => {
    event.preventDefault();

    const setCurrentUser = this.props.setCurrentUser;

    const data = new FormData(event.target);

    try {
      const res = await fetch("/api/login", {
        method: "POST",
        // headers: { "Content-Type": "application/json" },
        body: data,
      });
      if (res.status === 200) {
        const authentication = await getAuthentication();
        setCurrentUser(authentication);
      } else {
        this.setState({ error: "Invalid username or password" });
      }
    } catch (error) {
      console.error(error);
    }
  };

  render() {
    const classes = this.props.classes;

    return (
      <Grid container justify="space-evenly">
        <Grid item xs={12} sm={6} md={4}>
          <Card className={classes.root}>
            <form onSubmit={this.handleSubmit}>
              <Grid
                container
                direction="column"
                justify="space-around"
                alignItems="center"
                spacing={2}
              >
                <Grid item xs={12} className={classes.item}>
                  <TextField
                    id="login-username"
                    name="username"
                    variant="outlined"
                    label="Username"
                    fullWidth
                  />
                </Grid>
                <Grid item xs={12} className={classes.item}>
                  <TextField
                    id="login-password"
                    name="password"
                    type="password"
                    variant="outlined"
                    label="Password"
                    fullWidth
                  />
                </Grid>
                <Grid item xs={12}>
                  <Button variant="contained" color="primary" type="submit">
                    Login
                  </Button>
                </Grid>
                <Grid item xs={12}>
                  {this.state.error ? (
                    <Typography color="error">{this.state.error}</Typography>
                  ) : (
                    ""
                  )}
                </Grid>
              </Grid>
            </form>
          </Card>
        </Grid>
      </Grid>
    );
  }
}

const mapDispatchToProps = (dispatch) => ({
  setCurrentUser: (user) => dispatch(setCurrentUser(user)),
});

const styles = (theme) => ({
  root: {
    margin: theme.spacing(4),
    padding: theme.spacing(4),
  },
  item: {
    width: "100%",
  },
});

export default withRouter(
  connect(null, mapDispatchToProps)(withStyles(styles)(Login))
);
