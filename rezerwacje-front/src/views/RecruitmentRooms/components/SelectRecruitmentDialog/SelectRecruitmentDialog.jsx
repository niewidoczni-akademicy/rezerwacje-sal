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
import './SelectRecruitmentDialog.scss'

export default function SelectRecruitmentDialog(props) {

  const handleSubmit = () => {
    // const body = JSON.stringify({
    //   recruitmentId: props.recruitment,
    //   roomId: props.room,
    //   availableFrom: getTime(startTime),
    //   availableTo: getTime(endTime)
    // });
    // console.log(body);

    // fetch("/api/connection/connect-recruitment-and-room", {
    //   method: "POST",
    //   headers: { "Content-Type": "application/json" },
    //   body: body
    // }).then(
    //   function (res) {
    //     if (res.ok) {
    //       alert("Sala została dodana do rekrutacji.");
    //       props.handleClose();
    //     } else {
    //       alert("Wystąpił błąd.");
    //     }
    //   },
    //   function (e) {
    //     alert("Wystąpił błąd.");
    //   }
    // );
  };

  return (
    <Dialog
      open={props.open}
      onClose={props.handleClose}
      aria-labelledby="form-dialog-title"
    >
      <DialogTitle>
        <Typography variant="h3">
          Wybór sal z istniejącej rekrutacji
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
            </Grid>
            <Grid
              item
              md={6}
              md={12}
            >
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