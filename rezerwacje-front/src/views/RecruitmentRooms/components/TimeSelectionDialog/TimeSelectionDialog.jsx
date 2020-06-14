import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import {
  Grid,
  Typography,
}
  from "@material-ui/core"
import DateFnsUtils from '@date-io/date-fns';
import {
  MuiPickersUtilsProvider,
  KeyboardTimePicker,
} from '@material-ui/pickers';
import { useEffect, useState } from "react";
import validateTimeSelectionForm from "./validateTimeSelectionForm";
import './TimeSelectionDialog.scss'

export default function TimeSelectionDialog(props) {
  const [startTime, setStartTime] = useState(new Date());
  const [endTime, setEndTime] = useState(new Date());
  const [errors, setErrors] = useState([]);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const submit = () => {
    const body = JSON.stringify({
      recruitmentId: props.recruitment,
      roomId: props.room,
      availableFrom: getTime(startTime),
      availableTo: getTime(endTime)
    });
    console.log(body);

    fetch("/api/connection/connect-recruitment-and-room", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: body
    }).then(
      function (res) {
        if (res.ok) {
          alert("Sala została dodana do rekrutacji.");
          props.handleClose();
        } else {
          alert("Wystąpił błąd.");
        }
      },
      function (e) {
        alert("Wystąpił błąd.");
      }
    );
  };

  useEffect(() => {
    if (Object.keys(errors).length === 0 && isSubmitting) {
      submit();
    } else {
      console.log(errors);
    }
  }, [errors]);

  const handleSubmit = async event => {
    event.preventDefault();
    setErrors(validateTimeSelectionForm(startTime, endTime));
    setIsSubmitting(true);
  };

  const handleStartTimeChange = (selectedTime) => setStartTime(selectedTime);

  const handleEndTimeChange = (selectedTime) => setEndTime(selectedTime);

  const getTime = time => time.toLocaleString('sv', { timeZoneName: 'short' }).split(" ")[1].slice(0, 6) + "00";

  return (
    <Dialog
      open={props.open}
      onClose={props.handleClose}
      aria-labelledby="form-dialog-title"
    >
      <DialogTitle>
        <Typography variant="h3">
          Wybór godzin dostępności sali
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
              md={6}
              md={12}
            >
              <MuiPickersUtilsProvider utils={DateFnsUtils}>

                <KeyboardTimePicker
                  margin="normal"
                  label="Od godziny"
                  value={startTime}
                  onChange={handleStartTimeChange}
                  KeyboardButtonProps={{
                    'aria-label': 'change time',
                  }}
                />
              </MuiPickersUtilsProvider>
              <p className="error">{errors.startTime}</p>
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
                  value={endTime}
                  onChange={handleEndTimeChange}
                  KeyboardButtonProps={{
                    'aria-label': 'change time',
                  }}
                />
              </MuiPickersUtilsProvider>
              <p className="error">{errors.endTime}</p>
            </Grid>
          </Grid>
        </form>
      </DialogContent>
      <DialogActions>
        <Button onClick={props.handleClose} color="secondary" variant="contained">
          ZAMKNIJ
          </Button>
        <Button onClick={handleSubmit} color="primary" variant="contained">
          DODAJ
          </Button>
      </DialogActions>
    </Dialog>
  );
}