import React, { useState, useEffect } from 'react';
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

import { BackDOW, BackDOWToIndex } from './dow.js';
import validateRoomForm from './validateRoomForm.js';
import WeekTimeRangePicker from './WeekTimeRangePicker';

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

const convertTimeStamp = (time) => {
  let d = new Date();
  let [hours, minutes, seconds] = time.split(':');

  d.setHours(+hours);
  d.setMinutes(minutes);
  d.setSeconds(seconds);

  return d;
};

// Split by day of week
const convertBackendHours = (hours) => {
  const frontHours = Object.values(BackDOWToIndex).reduce((acc, value) => {
    acc[value] = [];
    return acc;
  }, {});

  hours.forEach((h, index) => {
    const { dayOfWeek, timeStart, timeEnd } = h;
    const dowInd = BackDOWToIndex[dayOfWeek];
    frontHours[dowInd].push([
      convertTimeStamp(timeStart),
      convertTimeStamp(timeEnd),
    ]);
  });

  return frontHours;
};

const convertFrontendHours = (hours) => {
  const result = {};

  for (var key in hours) {
    const dow = BackDOW[key];
    result[dow] = [];

    hours[key].forEach((element) => {
      result[dow].push({
        timeStart: dateFormat(element[0], 'HH:MM'),
        timeEnd: dateFormat(element[1], 'HH:MM'),
      });
    });
  }

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
        availabilityHours: convertFrontendHours(availabilityHours),
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

  const [values, setValues] = useState({
    ...props.initState,
    availabilityHours: convertBackendHours(props.initState.availabilityHours),
  });
  const [errors, setErrors] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleChange = (name, value) => {
    setValues({ ...values, [name]: value });
  };

  const handleChangeEvent = (event) => {
    const { name, value } = event.target;
    handleChange(name, value);
  };

  useEffect(() => {
    setValues({
      ...props.initState,
      availabilityHours: convertBackendHours(props.initState.availabilityHours),
    });
  }, [props.initState]);

  const handleSubmit = async () => {
    setIsSubmitting(true);
    setErrors(await validateRoomForm(values));
  };

  useEffect(() => {
    if (Object.keys(errors).length === 0 && isSubmitting) {
      submit();
    }
  }, [errors]);

  const setAvailabilityHours = (hours) => {
    handleChange('availabilityHours', hours);
  };

  const addRange = (selectedDay) => {
    let currentRanges = [...values.availabilityHours[selectedDay]];
    currentRanges.push([new Date(), new Date()]);
    setAvailabilityHours({
      ...values.availabilityHours,
      [selectedDay]: currentRanges,
    });
  };

  const deleteRange = (selectedDay) => (index) => {
    let currentRanges = [...values.availabilityHours[selectedDay]];
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
