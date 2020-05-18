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
  const [userCourses, setUserCourses] = useState([]);

  const getUserCourses = user => courses.filter(course => user.userCourses.find(idOb => idOb.id === course.id) != undefined);

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
    console.log(course.id)
    console.log(user.id)
    fetch(`/api/connection/connect?userId=${user.id}&courseOfStudyId=${course.id}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" }
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
        console.log(e);
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
                {courses.map(course => (
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
                      <ListItemSecondaryAction value={course.id} key={course.id}>
                        <IconButton edge="end" aria-label="delete" onClick={() => {addCourse(course);}}>
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
