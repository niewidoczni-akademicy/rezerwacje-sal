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
  IconButton,
  Typography
} from "@material-ui/core";
import "./ChangeCourseAccess.scss";
import RemoveIcon from "@material-ui/icons/RemoveCircle";
import AddIcon from "@material-ui/icons/AddCircle";

const ChangeCourseAccess = props => {
  const user = props.user;
  const [courses, setCourses] = useState([]);
  const [userCourses, setUserCourses] = useState([]);
  const [currentCourse, setCurrentCourse] = useState(-1);

  const getUserCourses = ids =>
    courses.filter(course => ids.find(id => id === course.id) != undefined);

  useEffect(() => {
    fetch("/api/course-of-study")
      .then(res => res.json())
      .then(json => {
        console.log(json);
        setCourses(json["courseOfStudies"]);
      })
      .catch(e => console.log(e));
  }, [props.user]);

  useEffect(() => {
    if (props.user != undefined)
      fetch(`/api/course-of-study/courses?userId=${props.user.id}`)
        .then(res => res.json())
        .then(json => {
          setUserCourses(getUserCourses(json["coursesOfStudiesIdsForUser"]));
        })
        .catch(e => console.log(e));
  }, [props.user, currentCourse]);

  const checkCourseAccess = id => {
    if (userCourses.find(course => course.id === id) != undefined) return false;
    else return true;
  };

  const translateType = type => {
    if (type == "FULL_TIME") return "Stacjonarny";
    else return "Zaoczny";
  };

  const getCourseString = course =>
    course.name +
    ", " +
    course.faculty.name +
    ", " +
    translateType(course.courseType);

  const handleAddCourse = course => {
    fetch(
      `/api/connection/connect?userId=${user.id}&courseOfStudyId=${course.id}`,
      {
        method: "POST",
        headers: { "Content-Type": "application/json" }
      }
    ).then(
      function(res) {
        if (res.ok) {
          setCurrentCourse(course.id);
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

  return (
    <React.Fragment>
      <Card style={{ marginTop: 3, marginBottom: 3, padding: 10 }}>
        <Typography variant="h6">
          Wybierz użytkownika, aby zmienić mu dostęp do kierunków
        </Typography>
      </Card>
      <Grid container direction={"row"}>
        <Grid item xs={6}>
          <Card>
            <CardHeader title="Dostęp do kierunków" />
            <Divider />
            <CardContent>
              {props.user != null && (
                <List>
                  {userCourses.map(course => (
                    <ListItem>
                      <ListItemText>{getCourseString(course)}</ListItemText>
                      <ListItemSecondaryAction>
                        <IconButton edge="end" aria-label="delete">
                          <RemoveIcon />
                        </IconButton>
                      </ListItemSecondaryAction>
                    </ListItem>
                  ))}
                </List>
              )}
            </CardContent>
          </Card>
        </Grid>
        <br />
        <Grid item xs={6}>
          <Card>
            <CardHeader title="Brak dostępu do kierunków" />
            <Divider />
            <CardContent>
              {props.user != null && (
                <List>
                  {courses
                    .filter(course => checkCourseAccess(course.id))
                    .map(course => (
                      <ListItem value={course.id}>
                        <ListItemText>{getCourseString(course)}</ListItemText>
                        <ListItemSecondaryAction
                          value={course.id}
                          key={course.id}
                        >
                          <IconButton
                            edge="end"
                            aria-label="delete"
                            onClick={() => {
                              handleAddCourse(course);
                            }}
                          >
                            <AddIcon />
                          </IconButton>
                        </ListItemSecondaryAction>
                      </ListItem>
                    ))}
                </List>
              )}
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </React.Fragment>
  );
};

export default ChangeCourseAccess;
