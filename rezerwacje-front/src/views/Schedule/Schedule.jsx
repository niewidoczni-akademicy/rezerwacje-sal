import React, { useState } from 'react';
import { makeStyles } from '@material-ui/styles';
import { 
  Grid, 
  Card, 
  Typography, 
  TextField,
  CardContent,
  Checkbox,
  Select,
  Input, 
  Chip, 
  MenuItem,
  InputLabel,
  FormControl,
  CardActions,
  Button,
} from '@material-ui/core';
import DateFnsUtils from '@date-io/date-fns';
import { KeyboardDatePicker, MuiPickersUtilsProvider } from '@material-ui/pickers';

import {
  ScheduleContent,
  Calendar,
  RecruitmentSelection,
  CoursesSelection,
  RoomsSelection,
  TimeSelection,
} from './components';

const useStyles = makeStyles(theme => ({
  root: {
    padding: theme.spacing(4)
  },
  formControl: {
    width: '100%',
  },
  chips: {
    display: 'flex',
    flexWrap: 'wrap',
  },
  chip: {
    margin: 2,
  },
}));

const Schedule = () => {
  const classes = useStyles();

  const recruitments = ["lato 2020", "zima 2021"];

  const cycles = ['1', '2'];

  const courses = ['IET Informatyka', 'IMiC Ceramika'];

  const rooms = ['D17 2.41', 'D17 1.38', 'Hehe online'];

  const [values, setValues] = useState({
    recruitment: recruitments.length > 0 ? recruitments[0] : '',
    cycle: cycles.length > 0 ? cycles[0] : '',
    dateFrom: new Date(Date.now() - (7 * 24 * 60 * 60 * 1000)),
    dateTo: new Date(Date.now()),
    courses: courses.slice(0),
    rooms: rooms.slice(0),
    recruitments: recruitments.slice(0),
    cycles: cycles.slice(0),
  });

  const handleChange = event => {
    const { name, value } = event.target;
    setValues({
        ...values,
        [name]: value
    });
  };

  const updateRecruitment = value => {
    setValues({
      ...values,
      ['recruitment']: value
    });
  }

  const updateCycle = value => handleChange({target: {name: 'cycle', value: value }})

  const updateCourses = value => handleChange({target: {name: 'courses', value: value }})

  const updateRooms = value => handleChange({target: {name: 'rooms', value: value }})

  const updateDateFrom = value => handleChange({target: {name: 'dateFrom', value: value }})

  const updateDateTo = value => handleChange({target: {name: 'dateTo', value: value }})

  const getFilterValues = () => {
    return {
      from: values.dateFrom, 
      to: values.dateTo,
      rooms: values.rooms,
      courses: values.courses,
    }
  }

  return (
    <div className={classes.root}>
      <Grid
        container
        spacing={4}
      >
        <Grid item xs={3}>
          <RecruitmentSelection 
            updateRecruitment={updateRecruitment} 
            updateCycle={updateCycle}
            recruitments={values.recruitments}
            cycles={values.cycles}
          />
        </Grid>
        <Grid item xs={3}>
          <CoursesSelection 
            updateCourses={updateCourses} 
            courses={values.courses}
          />
        </Grid>
        <Grid item xs={3}>
          <RoomsSelection 
            updateRooms={updateRooms} 
            rooms={values.rooms}
          />
        </Grid>
        <Grid item xs={3}>
          <TimeSelection 
            updateStart={updateDateFrom} 
            updateEnd={updateDateTo} 
            start={values.dateFrom}
            end={values.dateTo}
          />
        </Grid>
        <Grid item xs={12}>
          <Calendar 
            getFilterValues={getFilterValues}
            defaults={getFilterValues()}
          />
        </Grid>
      </Grid>
    </div>
  );
};

export default Schedule;
