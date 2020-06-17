import React from "react";
import { Button } from "@material-ui/core";
import { connect } from "react-redux";
import { setCurrentUser } from "../../redux/user/user.actions";
import { getAuthentication } from "../../auth";

const Logout = (props) => {
  const handleLogout = async (event) => {
    const res = await fetch("/api/logout");
    console.log("Logout", res);

    const authentication = await getAuthentication();
    props.setCurrentUser(authentication);
  };

  return <Button variant="contained" onClick={handleLogout}>Logout</Button>;
};

const mapDispatchToProps = (dispatch) => ({
  setCurrentUser: (user) => dispatch(setCurrentUser(user)),
});

export default connect(null, mapDispatchToProps)(Logout);