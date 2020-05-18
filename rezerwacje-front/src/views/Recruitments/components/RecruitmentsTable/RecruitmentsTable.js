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
  Button,
  IconButton,
  TablePagination
} from "@material-ui/core";
import "./RecruitmentsTable.scss";
import RecruitmentFormDialog from "../RecruitmentFormDialog"
import DeleteIcon from "@material-ui/icons/Delete";

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

const RecruitmentsTable = () => {
  const [selectedRecruitment, setSelectedRecruitment] = useState(-1);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [page, setPage] = useState(0);
  const [recruitments, setRecruitments] = useState([]);
  const [modalShow, setModalShow] = useState(false);

  const handleClose = () => setModalShow(false)

  const handlePageChange = (event, page) => {
    setPage(page);
  };

  const handleRowsPerPageChange = event => {
    setRowsPerPage(event.target.value);
  };

  const classes = useStyles();

  const handleSelectOne = (event, id) => {
    if (selectedRecruitment === id) setSelectedRecruitment(-1);
    else setSelectedRecruitment(id);
  };

  // useEffect(() => {
  //   fetch("/api/system-user?type=STANDARD")
  //     .then(res => res.json())
  //     .then(json => {
  //       console.log(json);
  //       setUsers(json["systemUsers"]);
  //     })
  //     .catch(e => console.log(e));
  // }, []);

  // const getSelectedUser = () => {
  //   if (selectedUser == -1) 
  //     return null;
  //   else {
  //     return users.find(user => user.id === selectedUser);
  //   }
  // }

  return (
    <React.Fragment>
    <Card>
    <CardHeader title="Rekrutacje" />
     <Divider />
      <CardContent className={classes.content}>
        <PerfectScrollbar>
          <div className={classes.inner}>
            <Table className={classes.table}>
              <TableHead>
                <TableRow>
                  <TableCell padding="checkbox" />
                  <TableCell>Nazwa</TableCell>
                  <TableCell>Data początkowa</TableCell>
                  <TableCell>Data końcowa</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {/* {recruitments
                  .map(recruitment => (
                    <TableRow
                      className={classes.tableRow}
                      hover
                      key={recruitment.id}
                      selected={recruitment.id == selectedRecruitment}
                    >
                      <TableCell padding="checkbox" className={classes.cell}>
                        <Checkbox
                          checked={recruitment.id == selectedRecruitment}
                          color="primary"
                          onChange={event => handleSelectOne(event, recruitment.id)}
                          value="true"
                        />
                      </TableCell>
                      <TableCell className={classes.cell}>
                        <div className={classes.nameContainer}>
                          <Typography variant="body1">{recruitment.name}</Typography>
                        </div>
                      </TableCell>
                      <TableCell className={classes.cell}>
                        <div className={classes.nameContainer}>
                          <Typography variant="body1">
                            {recruitment.startTime}
                          </Typography>
                        </div>
                      </TableCell>
                      <TableCell className={classes.cell}>
                        <div className={classes.nameContainer}>
                          <Typography variant="body1">
                            {recruitment.endTime}
                          </Typography>
                        </div>
                      </TableCell>
                    </TableRow>
                  ))} */}
                  <IconButton edge="end" aria-label="delete">
                        <DeleteIcon />
                      </IconButton>
              </TableBody>
            </Table>
          </div>
        </PerfectScrollbar>
      </CardContent>
      <CardActions className={classes.actions}>
        <TablePagination
          component="div"
          count={100}
          // count={recruitments.length}
          onChangePage={handlePageChange}
          onChangeRowsPerPage={handleRowsPerPageChange}
          page={page}
          rowsPerPage={rowsPerPage}
          rowsPerPageOptions={[5, 10, 25]}
        />
        <Button color="primary" variant="contained" type="submit" onClick={() => setModalShow(true)}>
            DODAJ REKRUTACJĘ
          </Button>
        <RecruitmentFormDialog open={modalShow} handleClose={handleClose}/>
      </CardActions>
    </Card>
    <br/>
    </React.Fragment>
  );
};

export default RecruitmentsTable;