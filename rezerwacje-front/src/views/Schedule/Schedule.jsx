import React, { useState } from 'react';
import clsx from 'clsx';
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
  Calendar
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
  });

  const handleDateFromChange = date => {
    setValues({
      ...values,
      ['dateFrom']: date
    });
  };

  const handleDateToChange = date => {
    setValues({
      ...values,
      ['dateTo']: date
    });
  };

  const handleChange = event => {
    const { name, value } = event.target;
    setValues({
        ...values,
        [name]: value
    });
  };

  const [selectedCourses, setSelectedCourses] = React.useState(courses.slice(0));

  const handleSelectedCoursesChange = event => {
    setSelectedCourses(event.target.value)
  };

  const [selectedRooms, setSelectedRooms] = React.useState(rooms.slice(0));

  const handleSelectedRoomsChange = event => {
    setSelectedRooms(event.target.value)
  };

  const getFilterValues = () => {
    return {
      from: values.dateFrom, 
      to: values.dateTo,
      rooms: selectedRooms,
      courses: selectedCourses,
    }
  }

  return (
    <div className={classes.root}>
      <Grid
        container
        spacing={4}
      >
        <Grid item xs={3}>
          <Card>
            <CardContent>
              <Typography variant="h4" gutterBottom>
                Recruitment
              </Typography>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  helperText="Recruitment"
                  margin="dense"
                  name="recruitment"
                  onChange={handleChange}
                  required
                  select
                  SelectProps={{ native: true }}
                  value={values.recruitment}
                  variant="outlined"
                >
                  {recruitments.map(recruitment => (
                    <option key={recruitment} value={recruitment}>{recruitment}</option>
                  ))}
                </TextField>
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  helperText="Cycle"
                  margin="dense"
                  name="cycle"
                  onChange={handleChange}
                  required
                  select
                  SelectProps={{ native: true }}
                  value={values.cycle}
                  variant="outlined"
                >
                  {cycles.map(cycle => (
                    <option key={cycle} value={cycle}>{cycle}</option>
                  ))}
                </TextField>
              </Grid>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={3}>
          <Card>
            <CardContent>
              <Typography variant="h4" gutterBottom>
                Courses
              </Typography>
              <Grid item xs={12}>
                <FormControl className={classes.formControl}>
                  <InputLabel id="multiple_selected_courses">Selected courses</InputLabel>
                  <Select
                    labelId="multiple_selected_courses"
                    id="courses_selection_panel"
                    multiple
                    value={selectedCourses}
                    onChange={handleSelectedCoursesChange}
                    input={<Input id="courses_selection" />}
                    renderValue={(selected) => (
                      <div className={classes.chips}>
                        {selected.map((value) => (
                          <Chip key={value} label={value} className={classes.chip} />
                        ))}
                      </div>
                    )}
                  >
                    {courses.map((course) => (
                      <MenuItem key={course} value={course}>
                        {course}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>
              </Grid>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={3}>
          <Card>
            <CardContent>
              <Typography variant="h4" gutterBottom>
                Rooms
              </Typography>
              <Grid item xs={12}>
                <FormControl className={classes.formControl}>
                  <InputLabel id="multiple_selected_rooms">Selected rooms</InputLabel>
                  <Select
                    labelId="multiple_selected_rooms"
                    id="rooms_selection_panel"
                    multiple
                    value={selectedRooms}
                    onChange={handleSelectedRoomsChange}
                    input={<Input id="rooms_selection" />}
                    renderValue={(selected) => (
                      <div className={classes.chips}>
                        {selected.map((value) => (
                          <Chip key={value} label={value} className={classes.chip} />
                        ))}
                      </div>
                    )}
                  >
                    {rooms.map((room) => (
                      <MenuItem key={room} value={room}>
                        {room}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>
              </Grid>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={3}>
          <Card>
            <CardContent>
              <Typography variant="h4" gutterBottom>
                Time interval
              </Typography>
              <MuiPickersUtilsProvider utils={DateFnsUtils}>
                <KeyboardDatePicker
                  disableToolbar
                  helperText="From date"
                  variant="inline"
                  format="MM/dd/yyyy"
                  margin="normal"
                  id="date-picker-from"
                  value={values.dateFrom}
                  onChange={handleDateFromChange}
                  KeyboardButtonProps={{
                    'aria-label': 'change date',
                  }}
                />
                <KeyboardDatePicker
                  disableToolbar
                  helperText="To date"
                  variant="inline"
                  format="MM/dd/yyyy"
                  margin="normal"
                  id="date-picker-to"
                  value={values.dateTo}
                  onChange={handleDateToChange}
                  KeyboardButtonProps={{
                    'aria-label': 'change date',
                  }}
                />
              </MuiPickersUtilsProvider>
            </CardContent>
          </Card>
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
