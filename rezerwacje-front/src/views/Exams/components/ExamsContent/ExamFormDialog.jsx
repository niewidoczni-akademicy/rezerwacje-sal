import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import {
  Grid,
  Typography,
  Divider
}
  from "@material-ui/core"
import DateFnsUtils from '@date-io/date-fns';
import {
  MuiPickersUtilsProvider,
  KeyboardTimePicker,
  KeyboardDatePicker,
} from '@material-ui/pickers';
import { useEffect, useState } from "react"


export default function ExamFormDialog(props) {

  const [selectedDate, setSelectedDate] = React.useState(new Date());
  const [courses, setCourses] = useState([]);
  const [rooms, setRooms] = useState([]);

  const handleDateChange = (date) => {
    setSelectedDate(date);
  };


  useEffect(() => {
    fetch("/api/course-of-study")
      .then(res => res.json())
      .then(json => {
        console.log(json);
        setCourses(json["courseOfStudies"]);
      })
      .catch(e => console.log(e));
  }, []);

  useEffect(() => {
    fetch("/api/rooms")
      .then(res => res.json())
      .then(json => {
        console.log(json);
        setRooms(json["rooms"]);
      })
      .catch(e => console.log(e));
  }, []);

  return (
      <Dialog
        open={props.open}
        onClose={props.handleClose}
        aria-labelledby="form-dialog-title"
      >
        <DialogTitle>
          <Typography variant="h3">
            Nowy egzamin
          </Typography>
          <Typography variant="h5">
            Rekrutacja: {props.recruitment}
          </Typography>
          <Typography variant="h5">
            Cykl: {props.period}
          </Typography>
        </DialogTitle>
        <DialogContent dividers>
          <form
            autoComplete="off"
            noValidate
          >
            <Grid
              container
              spacing={1}
            >
              <Grid
                item
                xs={12}
              >
                <TextField
                  fullWidth
                  helperText="Kierunek"
                  margin="dense"
                  required
                  select
                  SelectProps={{ native: true }}
                  variant="outlined"
                >
                  {courses.map(course => (
                    <option value={course.id}>{course.name}</option>
                  ))}
                </TextField>
              </Grid>
              <Grid
                item
                xs={12}
              >
                <TextField
                  fullWidth
                  margin="dense"
                  helperText="Sala"
                  required
                  select
                  SelectProps={{ native: true }}
                  variant="outlined"
                >
                  {rooms.map(room => (
                    <option value={room}>{`${room.name}, ${room.building}`}</option>
                  ))}
                </TextField>
              </Grid>
              <Grid
                item
                md={12}
                md={12}
              >
                <MuiPickersUtilsProvider utils={DateFnsUtils}>
                  <KeyboardDatePicker
                    disableToolbar
                    variant="inline"
                    format="MM/dd/yyyy"
                    margin="normal"
                    label="Data"
                    value={selectedDate}
                    onChange={handleDateChange}
                    color="primary"
                    KeyboardButtonProps={{
                      'aria-label': 'change date',
                    }}
                  />
                </MuiPickersUtilsProvider>
              </Grid>
              <Grid
                item
                md={6}
                md={12}
              >
                <MuiPickersUtilsProvider utils={DateFnsUtils}>

                  <KeyboardTimePicker
                    margin="normal"
                    label="Od godziny"
                    value={selectedDate}
                    onChange={handleDateChange}
                    KeyboardButtonProps={{
                      'aria-label': 'change time',
                    }}
                  />
                </MuiPickersUtilsProvider>
              </Grid>
              <Grid
                item
                md={6}
                md={12}
              >
                <MuiPickersUtilsProvider utils={DateFnsUtils}>

                  <KeyboardTimePicker
                    margin="normal"
                    label="Do godziny"
                    value={selectedDate}
                    onChange={handleDateChange}
                    KeyboardButtonProps={{
                      'aria-label': 'change time',
                    }}
                  />
                </MuiPickersUtilsProvider>

              </Grid>
            </Grid>
          </form>
        </DialogContent>
        <DialogActions>
          <Button onClick={props.handleClose} color="secondary" variant="contained">
            ZAMKNIJ
          </Button>
          <Button onClick={props.handleClose} color="primary" variant="contained">
            DODAJ
          </Button>
        </DialogActions>
      </Dialog>
  );
}