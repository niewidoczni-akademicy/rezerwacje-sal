import React, { useState } from 'react';
import PerfectScrollbar from 'react-perfect-scrollbar';
import { makeStyles } from '@material-ui/styles';
import clsx from 'clsx';
import {
  Card,
  CardActions,
  CardContent,
  Avatar,
  Checkbox,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  Typography,
  TablePagination
} from '@material-ui/core';
import "./UsersList.scss";
import mockData from './data'

const useStyles = makeStyles(theme => ({
  root: {},
  content: {
    padding: 0,
    border: 0
  },
  inner: {
    border: 0,
    minWidth: 1050
  },
  nameContainer: {
    display: 'flex',
    alignItems: 'center',
  },
  table: {
    border: 0
  },
  cell: {
    border: 0
  },
  avatar: {
    marginRight: theme.spacing(2)
  },
  actions: {
    justifyContent: 'flex-end'
  }
}));


const UsersList = () => {

  const [selectedUser, setSelectedUser] = useState(-1);
  const [users] = useState(mockData)
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [page, setPage] = useState(0);

  const handlePageChange = (event, page) => {
    setPage(page);
  };

  const handleRowsPerPageChange = event => {
    setRowsPerPage(event.target.value);
  };

  const classes = useStyles();

  const handleSelectOne = (event, id) => {
    if (selectedUser === id)
      setSelectedUser(-1);
    else
      setSelectedUser(id);
  };

  return (
    <Card
    >
      <CardContent className={classes.content}>
        <PerfectScrollbar>
          <div className={classes.inner}>
            <Table className={classes.table}>
              <TableHead>
                <TableRow>
                  <TableCell padding="checkbox"/>
                  <TableCell>Login</TableCell>
                  <TableCell>Imię</TableCell>
                  <TableCell>Nazwisko</TableCell>
                  <TableCell>Adres email</TableCell>
                  <TableCell>Numer telefonu</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {users.slice(0, rowsPerPage).map(user => (
                  <TableRow
                    className={classes.tableRow}
                    hover
                    key={user.id}
                    selected={user.id === selectedUser}
                  >
                    <TableCell padding="checkbox" className={classes.cell}>
                      <Checkbox
                        checked={user.id === selectedUser}
                        color="primary"
                        onChange={event => handleSelectOne(event, user.id)}
                        value="true"
                      />
                    </TableCell>
                    <TableCell className={classes.cell}>
                      <div className={classes.nameContainer} >
                        <Typography variant="body1">{user.name}</Typography>
                      </div>
                    </TableCell>
                    <TableCell className={classes.cell}>{user.email}</TableCell>
                    <TableCell className={classes.cell}>
                      {user.address.city}, {user.address.state},{' '}
                      {user.address.country}
                    </TableCell>
                    <TableCell className={classes.cell}>{user.phone}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </div>
        </PerfectScrollbar>
      </CardContent>
      <CardActions className={classes.actions}>
        <TablePagination
          component="div"
          count={users.length}
          onChangePage={handlePageChange}
          onChangeRowsPerPage={handleRowsPerPageChange}
          page={page}
          rowsPerPage={rowsPerPage}
          rowsPerPageOptions={[5, 10, 25]}
        />
      </CardActions>
    </Card>
  );

};

export default UsersList;
