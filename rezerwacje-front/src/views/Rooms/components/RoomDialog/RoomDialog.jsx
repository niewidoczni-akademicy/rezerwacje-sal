import React, { useState } from 'react';
import {
  Grid,
  Button,
  TextField,
  Typography,
  DialogActions,
  IconButton,
} from '@material-ui/core';
import { makeStyles, useTheme } from '@material-ui/styles';
import CloseIcon from '@material-ui/icons/Close';
import Dialog from '@material-ui/core/Dialog';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';

import validateRoomForm from './validateRoomForm.js';
import WeekTimeRangePicker from './WeekTimeRangePicker';
import { useDialogForm } from 'common/utilities';

var dateFormat = require('dateformat');

const useStyles = makeStyles((theme) => ({
  root: {
    margin: 0,
    padding: theme.spacing(2),
  },
  closeButton: {
    position: 'absolute',
    right: theme.spacing(1),
    top: theme.spacing(1),
    color: theme.palette.grey[500],
  },
}));

const DOW = Object.freeze([
  'monday',
  'tuesday',
  'wednesday',
  'thursday',
  'friday',
  'saturday',
  'sunday',
]);

const transformAvailability = (details) => {
  const result = {};

  for (var key in details) {
    const dow = DOW[key];
    result[dow] = [];

    details[key].forEach((element) => {
      result[dow].push({
        timeStart: dateFormat(element[0], 'hh:MM'),
        timeEnd: dateFormat(element[1], 'hh:MM'),
      });
    });
  }

  console.log(result);
  return result;
};

const RoomDialog = (props) => {
  const classes = useStyles();

  const submit = () => {
    const { id, name, building, capacity, availabilityHours } = values;

    fetch(props.url, {
      method: props.httpMethod,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        id: id,
        name: name,
        building: building,
        capacity: capacity,
        availabilityHours: transformAvailability(availabilityHours),
      }),
    }).then(
      function (res) {
        if (res.ok) {
          alert(props.message);
          props.onSubmitted();
        } else {
          alert('Wystąpił błąd.');
        }
      },
      function (e) {
        alert('Wystąpił błąd.');
      }
    );
  };

  const {
    handleChange,
    handleChangeEvent,
    handleSubmit,
    values,
    errors,
  } = useDialogForm(props.initState, submit, validateRoomForm);

  const setAvailabilityHours = (details) => {
    handleChange('availabilityHours', details);
  };

  const addRange = (selectedDay) => {
    let currentRanges = [...values.availabilityDetails[selectedDay]];
    currentRanges.push([new Date(), new Date()]);
    setAvailabilityHours({
      ...values.availabilityHours,
      [selectedDay]: currentRanges,
    });
  };

  const deleteRange = (selectedDay) => (index) => {
    let currentRanges = [...values.availabilityDetails[selectedDay]];
    let newRanges = currentRanges.reduce(function (acc, value, ind) {
      if (ind !== index) {
        acc.push(value);
      }
      return acc;
    }, []);
    setAvailabilityHours({
      ...values.availabilityHours,
      [selectedDay]: newRanges,
    });
  };

  const changeRange = (selectedDay) => (index, range) => {
    let currentRanges = [...values.availabilityHours[selectedDay]];
    currentRanges[index] = range;
    setAvailabilityHours({
      ...values.availabilityHours,
      [selectedDay]: currentRanges,
    });
  };

  return (
    <Dialog
      open={props.open}
      onClose={props.handleClose}
      aria-labelledby="form-dialog-title"
    >
      <DialogTitle className={classes.root}>
        <Typography variant="h3">{props.title}</Typography>
        <IconButton
          aria-label="close"
          className={classes.closeButton}
          onClick={props.handleClose}
        >
          <CloseIcon />
        </IconButton>
      </DialogTitle>
      <DialogContent dividers>
        <Grid container spacing={3}>
          <Grid item xs={6}>
            <TextField
              label="Nazwa"
              margin="dense"
              name="name"
              onChange={handleChangeEvent}
              required
              value={values.name}
              variant="outlined"
            />
            <p className="error">{errors.name}</p>
          </Grid>
          <Grid item xs={12}>
            <TextField
              label="Budynek"
              margin="dense"
              name="building"
              onChange={handleChangeEvent}
              required
              variant="outlined"
              value={values.building}
            />
            <p className="error">{errors.building}</p>
          </Grid>
          <Grid item xs={12}>
            <TextField
              label="Pojemność"
              margin="dense"
              name="capacity"
              onChange={handleChangeEvent}
              required
              value={values.capacity}
              variant="outlined"
            />
            <p className="error">{errors.capacity}</p>
          </Grid>
          <Grid item xs={12}>
            <Typography variant="h4">Dostępna w godzinach:</Typography>
          </Grid>
          <Grid item xs={12}>
            <WeekTimeRangePicker
              addRange={addRange}
              changeRange={changeRange}
              deleteRange={deleteRange}
              weekTimeRanges={values.availabilityHours}
            />
          </Grid>
        </Grid>
      </DialogContent>
      <DialogActions>
        <Button
          color="primary"
          variant="contained"
          onClick={() => handleSubmit()}
        >
          {props.action}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default RoomDialog;
