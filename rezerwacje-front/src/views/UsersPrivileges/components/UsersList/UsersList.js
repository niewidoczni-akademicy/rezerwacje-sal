import React, { useState, useEffect } from "react";
import PerfectScrollbar from "react-perfect-scrollbar";
import { makeStyles } from "@material-ui/styles";
import clsx from "clsx";
import {
  Card,
  CardActions,
  CardContent,
  Avatar,
  Divider,
  CardHeader,
  Checkbox,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  Typography,
  TablePagination
} from "@material-ui/core";
import "./UsersList.scss";
import ChangeAccessForm from "../ChangeAccessForm/ChangeAccessForm";

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
    display: "flex",
    alignItems: "center"
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
    justifyContent: "flex-end"
  }
}));

const UsersList = () => {
  const [selectedUser, setSelectedUser] = useState("");
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [page, setPage] = useState(0);
  const [users, setUsers] = useState([]);

  const handlePageChange = (event, page) => {
    setPage(page);
  };

  const handleRowsPerPageChange = event => {
    setRowsPerPage(event.target.value);
  };

  const classes = useStyles();

  const handleSelectOne = (event, id) => {
    if (selectedUser === id) setSelectedUser(-1);
    else setSelectedUser(id);
  };

  useEffect(() => {
    fetch("/api/system-user/all")
      .then(res => res.json())
      .then(json => {
        console.log(json);
        setUsers(json["systemUsers"]);
      })
      .catch(e => console.log(e));
  }, []);

  const getSelectedUser = () => {
    if (selectedUser == -1) 
      return null;
    else {
      return users.find(user => user.id === selectedUser);
    }
  }

  return (
    <React.Fragment>
    <Card>
    <CardHeader title="Użytkownicy standardowi" />
     <Divider />
      <CardContent className={classes.content}>
        <PerfectScrollbar>
          <div className={classes.inner}>
            <Table className={classes.table}>
              <TableHead>
                <TableRow>
                  <TableCell padding="checkbox" />
                  <TableCell>Login</TableCell>
                  <TableCell>Imię</TableCell>
                  <TableCell>Nazwisko</TableCell>
                  <TableCell>Adres email</TableCell>
                  <TableCell>Numer telefonu</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {users
                  .filter(user => user.userType === "STANDARD")
                  .map(user => (
                    <TableRow
                      className={classes.tableRow}
                      hover
                      key={user.id}
                      selected={user.id == selectedUser}
                    >
                      <TableCell padding="checkbox" className={classes.cell}>
                        <Checkbox
                          checked={user.id == selectedUser}
                          color="primary"
                          onChange={event => handleSelectOne(event, user.id)}
                          value="true"
                        />
                      </TableCell>
                      <TableCell className={classes.cell}>
                        <div className={classes.nameContainer}>
                          <Typography variant="body1">{user.login}</Typography>
                        </div>
                      </TableCell>
                      <TableCell className={classes.cell}>
                        <div className={classes.nameContainer}>
                          <Typography variant="body1">
                            {user.firstName}
                          </Typography>
                        </div>
                      </TableCell>
                      <TableCell className={classes.cell}>
                        <div className={classes.nameContainer}>
                          <Typography variant="body1">
                            {user.lastName}
                          </Typography>
                        </div>
                      </TableCell>
                      <TableCell className={classes.cell}>
                        {user.emailAddress}
                      </TableCell>
                      <TableCell className={classes.cell}>
                        {user.phoneNumber}
                      </TableCell>
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
    <br/>
    <ChangeAccessForm user={getSelectedUser()}/>
    </React.Fragment>
  );
};

export default UsersList;
