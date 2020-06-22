import React, { useState, useEffect } from "react";
import PerfectScrollbar from "react-perfect-scrollbar";
import { makeStyles } from "@material-ui/styles";
import {
  Card,
  CardActions,
  CardContent,
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
  TablePagination
} from "@material-ui/core";
import "./RecruitmentsTable.scss";
import RecruitmentFormDialog from "../RecruitmentFormDialog";
import RecruitmentCycleFormDialog from "../RecruitmentCycleFormDialog";
import { connect } from "react-redux";
import { selectCurrentUser } from "/webapp/src/redux/user/user.selectors";

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

const RecruitmentsTable = props => {
  const [selectedRecruitment, setSelectedRecruitment] = useState(-1);
  const [recruitments, setRecruitments] = useState([]);
  const [modalShow, setModalShow] = useState(false);
  const [cycleModalShow, setCycleModalShow] = useState(false);

  const handleClose = () => setModalShow(false);
  const handleCycleClose = () => setCycleModalShow(false);

  const classes = useStyles();

  const handleSelectOne = (event, id) => {
    if (selectedRecruitment === id) setSelectedRecruitment(-1);
    else setSelectedRecruitment(id);
  };

  useEffect(() => {
    fetch("/api/recruitment/all")
      .then(res => res.json())
      .then(json => {
        setRecruitments(json["recruitments"]);
      })
      .catch(e => console.log(e));
  }, []);

  const convertStatusType = status => {
    if (status == "IN_PREPARATION") {
      return "W przygotowaniu";
    } else if (status == "IN_PROGRESS") {
      return "Trwa";
    } else return "Zakończona";
  };

  const convertDate = date => {
    return date.split("T")[0];
  };

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
                    <TableCell>Status</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {recruitments.map(recruitment => (
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
                          onChange={event =>
                            handleSelectOne(event, recruitment.id)
                          }
                          value="true"
                        />
                      </TableCell>
                      <TableCell className={classes.cell}>
                        <div className={classes.nameContainer}>
                          <Typography variant="body1">
                            {recruitment.name}
                          </Typography>
                        </div>
                      </TableCell>
                      <TableCell className={classes.cell}>
                        <div className={classes.nameContainer}>
                          <Typography variant="body1">
                            {convertDate(recruitment.startTime)}
                          </Typography>
                        </div>
                      </TableCell>
                      <TableCell className={classes.cell}>
                        <div className={classes.nameContainer}>
                          <Typography variant="body1">
                            {convertDate(recruitment.endTime)}
                          </Typography>
                        </div>
                      </TableCell>
                      <TableCell className={classes.cell}>
                        <div className={classes.nameContainer}>
                          <Typography variant="body1">
                            {convertStatusType(recruitment.recruitmentStatus)}
                          </Typography>
                        </div>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </div>
          </PerfectScrollbar>
        </CardContent>
        <CardActions className={classes.actions}>
          {selectedRecruitment != -1 && props.currentUser.role != "STANDARD" && (
            <Button
              color="primary"
              variant="contained"
              type="submit"
              onClick={() => setCycleModalShow(true)}
            >
              DODAJ CYKL
            </Button>
          )}
          {props.currentUser.role != "STANDARD" && (
            <Button
              color="primary"
              variant="contained"
              type="submit"
              onClick={() => setModalShow(true)}
            >
              DODAJ REKRUTACJĘ
            </Button>
          )}
          <RecruitmentFormDialog open={modalShow} handleClose={handleClose} />
          <RecruitmentCycleFormDialog open={cycleModalShow} handleClose={handleCycleClose} recruitmentId={selectedRecruitment} />
          </CardActions>
      </Card>
      <br />
    </React.Fragment>
  );
};

const mapStateToProps = state => ({
  currentUser: selectCurrentUser(state)
});

export default connect(mapStateToProps)(RecruitmentsTable);
