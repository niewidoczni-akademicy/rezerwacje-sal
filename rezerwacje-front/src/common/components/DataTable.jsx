import React from 'react';
import { useState, useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from '@material-ui/core';

const useStyles = makeStyles({
  table: {
    minWidth: 650,
  },
});

function DataTable(props) {
  const classes = useStyles();
  const [entries, setEntries] = useState([]);

  useEffect(() => {
    fetch(props.url)
      .then((res) => res.json())
      .then((json) => {
        console.log(json);
        setEntries(json[props.responseKey]);
      })
      .catch((e) => console.log(e));
  }, []);

  return (
    <TableContainer component={Paper}>
      <Table className={classes.table} size="small">
        <TableHead>
          <TableRow>
            <TableCell>{props.header[0]}</TableCell>
            {props.header.slice(1).map((column) => (
              <TableCell align="right">{column}</TableCell>
            ))}
          </TableRow>
        </TableHead>
        <TableBody>
          {entries.map((entry) => {
            const columns = props.mapColumns(entry);

            return (
              <TableRow key={entry.id}>
                <TableCell>{columns[0]}</TableCell>
                {columns.slice(1).map((column) => (
                  <TableCell align="right">{column}</TableCell>
                ))}
              </TableRow>
            );
          })}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export default DataTable;
