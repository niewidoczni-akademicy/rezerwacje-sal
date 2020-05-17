import React from "react";
import { useState, useEffect } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";

const useStyles = makeStyles({
  table: {
    minWidth: 650,
  },
});

function CoursesTable() {
  const classes = useStyles();

  const [courses, setCourses] = useState([]);

  useEffect(() => {
    fetch("/api/course-of-study")
      .then((res) => res.json())
      .then((json) => {
        console.log(json);
        setCourses(json["courseOfStudies"]);
      })
      .catch((e) => console.log(e));
  }, []);

  return (
    <TableContainer component={Paper}>
      <Table className={classes.table} size="small" aria-label="a dense table">
        <TableHead>
          <TableRow>
            <TableCell>Kierunek</TableCell>
            <TableCell align="right">Wydział</TableCell>
            <TableCell align="right">Typ</TableCell>
            <TableCell align="right">Kontakt 1</TableCell>
            <TableCell align="right">Kontakt 2</TableCell>
            <TableCell align="right">Łączony</TableCell>
            <TableCell align="right">Uwagi</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {courses.map((course) => {
            const type =
              course.courseType == "FULL_TIME" ? "Stacjonarny" : "Zaoczny";
            const contactPerson2 =
              course.contactPerson2 != null ? course.contactPerson2.login : "";
            const remarks = course.remarks != null ? course.remarks : "";
            const isJoined = course.isJoined ? "tak" : "nie";

            return (
              <TableRow key={course.id}>
                <TableCell>{course.name}</TableCell>
                <TableCell align="right">{course.faculty.name}</TableCell>
                <TableCell align="right">{type}</TableCell>
                <TableCell align="right">
                  {course.contactPerson1.login}
                </TableCell>
                <TableCell align="right">{contactPerson2}</TableCell>
                <TableCell align="right">{isJoined}</TableCell>
                <TableCell align="right">{remarks}</TableCell>
              </TableRow>
            );
          })}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export default CoursesTable;
