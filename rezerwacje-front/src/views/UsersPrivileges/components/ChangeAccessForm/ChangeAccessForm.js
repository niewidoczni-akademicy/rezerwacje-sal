import React, { useState, useEffect } from "react";
import {
  Card,
  CardHeader,
  CardContent,
  Avatar,
  List,
  ListItem,
  ListItemText,
  ListItemAvatar,
  ListItemSecondaryAction,
  IconButton
} from "@material-ui/core";
import "./ChangeAccessForm.scss";
import DeleteIcon from "@material-ui/icons/Delete";

const ChangeAccessForm = props => {
  const user = props.user;


  if (user != null) {
    const title = `Zmiana dostępu do kierunków dla użytkownika: ${user.firstName} ${user.lastName}`;
    return (
      <React.Fragment>
        <Card>
          <CardHeader title={title} />
          <CardContent>
            <List>
              {user.userCourses.map(course => (
                <ListItem>
                  <ListItemSecondaryAction>
                    <IconButton edge="end" aria-label="delete">
                      <DeleteIcon />
                    </IconButton>
                  </ListItemSecondaryAction>
                </ListItem>
              ))}
            </List>
          </CardContent>
        </Card>
      </React.Fragment>
    );
  } else return null;
};

export default ChangeAccessForm;
