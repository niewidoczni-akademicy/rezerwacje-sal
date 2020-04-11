import React from "react";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";

import { makeStyles } from "@material-ui/core/styles";

const useStyles = makeStyles({
  table: {
    "border-collapse": true,
    minWidth: 650,
  },
});

function Header(props) {
  return (
    <TableHead>
      <TableRow>
        <TableCell />
        {props.fields.map((field) => (
          <TableCell>{field}</TableCell>
        ))}
      </TableRow>
    </TableHead>
  );
}

function Row(props) {
  return (
    <TableRow key={props.id}>
      <TableCell component="th" scope="row">
        {props.id}
      </TableCell>
      {props.fields.map((field) => (
        <TableCell>{field}</TableCell>
      ))}
    </TableRow>
  );
}

function SimpleTable(props) {
  const classes = useStyles();

  return (
    <TableContainer component={Paper}>
      <Table className={classes.table} stickyHeader aria-label="sticky header">
        <Header fields={props.header} />
        <TableBody>
          {props.rows.map((row) => (
            <Row id={row.id} fields={row.fields} />
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export default SimpleTable;
