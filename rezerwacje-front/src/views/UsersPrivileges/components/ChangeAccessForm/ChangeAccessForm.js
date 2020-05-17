import React, { useState, useEffect } from "react";
import {
  Card,
  CardHeader,
  CardContent,
  Divider,
  Grid,
  List,
  ListItem,
  ListItemText,
  ListItemSecondaryAction,
  IconButton
} from "@material-ui/core";
import "./ChangeAccessForm.scss";
import DeleteIcon from "@material-ui/icons/Delete";
import AddIcon from "@material-ui/icons/AddBox";

const ChangeAccessForm = props => {
  const user = props.user;
  const [courses, setCourses] = useState([]);

  useEffect(() => {
    fetch("/api/course-of-study")
      .then(res => res.json())
      .then(json => {
        console.log(json);
        setCourses(json["courseOfStudies"]);
      })
      .catch(e => console.log(e));
  }, []);

  const userNoAccess = id => {
    if (user.userCourses.find(course => course.id === id) != undefined)
      return false;
    else return true;
  };

  const addCourse = course => {
    console.log(course)
    fetch("/api/connection/connect", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        userId: user.id,
        courseOfStidyId: course.id
      })
    }).then(
      function(res) {
        if (res.ok) {
          alert("Dostęp został przyznany.");
        } else if (res.status === 400) {
          alert("Wystąpił błąd.");
        }
      },
      function(e) {
        alert("Wystąpił błąd.");
      }
    );
  };
  
  if (user != null) {
    const title = `Zmiana dostępu do kierunków dla użytkownika: ${user.firstName} ${user.lastName}`;
    return (
      <Grid container direction={"row"}>
        <Grid item xs={6}>
          <Card>
            <CardHeader title="Dostęp do kierunków" />
            <Divider />
            <CardContent>
              <List>
                {user.userCourses.map(course => (
                  <ListItem>
                    <ListItemText>{course.name}</ListItemText>
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
        </Grid>
        <br/>
        <Grid item xs={6}>
          <Card>
            <CardHeader title="Brak dostępu do kierunków" />
            <Divider />
            <CardContent>
              <List>
                {courses
                  .filter(course => userNoAccess(course))
                  .map(course => (
                    <ListItem value={course.id}>
                      <ListItemText>{course.name}</ListItemText>
                      <ListItemSecondaryAction value={course.id}>
                        <IconButton edge="end" aria-label="delete">
                          <AddIcon />
                        </IconButton>
                      </ListItemSecondaryAction>
                    </ListItem>
                  ))}
              </List>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    );
  } else return null;
};

export default ChangeAccessForm;
