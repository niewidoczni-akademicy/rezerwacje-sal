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

export default function RoomsTable() {
  const classes = useStyles();

  const [rooms, setRooms] = useState([]);

  useEffect(() => {
    fetch("/api/rooms")
      .then((res) => res.json())
      .then((json) => {
        setRooms(json["rooms"]);
      })
      .catch((e) => console.log(e));
  }, []);

  return (
    <TableContainer component={Paper}>
      <Table className={classes.table} size="small" aria-label="a dense table">
        <TableHead>
          <TableRow>
            <TableCell>Sala</TableCell>
            <TableCell align="right">Budynek</TableCell>
            <TableCell align="right">Pojemność</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rooms.map((room) => (
            <TableRow key={room.id}>
              <TableCell>{room.name}</TableCell>
              <TableCell align="right">{room.building}</TableCell>
              <TableCell align="right">{room.capacity}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
