import React, { Fragment, useState } from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import {
  Button,
  Divider,
  FormControl,
  Grid,
  List,
  ListItem,
  MenuItem,
  Select,
} from '@material-ui/core';
import DateFnsUtils from '@date-io/date-fns';
import {
  MuiPickersUtilsProvider,
  KeyboardTimePicker,
} from '@material-ui/pickers';

const DOW = Object.freeze([
  'Poniedziałek',
  'Wtorek',
  'Środa',
  'Czwartek',
  'Piątek',
  'Sobota',
  'Niedziela',
]);

function TimeRangePicker(props) {
  const { startDate, endDate, onChange } = props;

  const onStartDateChange = (event) => {
    onChange([event.target.value, endDate]);
  };

  const onEndDateChange = (event) => {
    onChange([startDate, event.target.value]);
  };

  return (
    <MuiPickersUtilsProvider utils={DateFnsUtils}>
      <KeyboardTimePicker
        margin="normal"
        label="Od godziny"
        value={startDate}
        onChange={onStartDateChange}
        KeyboardButtonProps={{
          'aria-label': 'change time',
        }}
      />
      <KeyboardTimePicker
        margin="normal"
        label="Do godziny"
        value={endDate}
        onChange={onEndDateChange}
        KeyboardButtonProps={{
          'aria-label': 'change time',
        }}
      />
    </MuiPickersUtilsProvider>
  );
}

function TimeRangeList(props) {
  const { index, value, timeRanges, handleRangeChange } = props;

  const setTimeRange = (index) => (range) => {
    let newTimeRanges = [...timeRanges];
    newTimeRanges[index] = range;
    handleRangeChange(newTimeRanges);
  };

  return (
    <Fragment>
      {index == value && (
        <List>
          {timeRanges.map(
            (range, index) => (
              <Fragment>
                <ListItem>
                  <TimeRangePicker
                    startDate={range[0]}
                    onChange={setTimeRange(index)}
                    endDate={range[1]}
                  />
                </ListItem>
                <Divider />
              </Fragment>
            ),
            this
          )}
        </List>
      )}
    </Fragment>
  );
}

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    backgroundColor: theme.palette.background.paper,
  },
  formControl: {
    minWidth: 120,
  },
  select: {
    height: 30,
    minHeight: 30,
  },
}));

export default function WeekTimeRangePicker() {
  const classes = useStyles();

  const [weekTimeRanges, setWeekTimeRanges] = useState(
    DOW.reduce(function (map, value, index) {
      map[index] = [];
      return map;
    }, {})
  );

  const setDayTimeRanges = (index) => (ranges) =>
    setWeekTimeRanges({
      ...weekTimeRanges,
      [index]: ranges,
    });

  const addRange = () => {
    let currentRanges = [...weekTimeRanges[selectedDay]];
    currentRanges.push([new Date(), new Date()]);
    setWeekTimeRanges({
      ...weekTimeRanges,
      [selectedDay]: currentRanges,
    });
  };

  const [selectedDay, setSelectedDay] = React.useState(0);

  const handleDayChange = (event) => {
    setSelectedDay(event.target.value);
  };

  return (
    <div className={classes.root}>
      <Grid container spacing={1}>
        <Grid item xs={3}>
          <FormControl variant="outlined" className={classes.formControl}>
            <Select
              className={classes.select}
              labelId="demo-simple-select-outlined-label"
              id="demo-simple-select-outlined"
              value={selectedDay}
              onChange={handleDayChange}
            >
              {DOW.map((dow, ind) => (
                <MenuItem value={ind}>{dow}</MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={9}>
          <Button
            variant="contained"
            size="small"
            color="secondary"
            onClick={addRange}
          >
            Dodaj przedział
          </Button>
        </Grid>
        <Grid item xs={12}>
          {DOW.map(
            (value, index) => (
              <TimeRangeList
                value={selectedDay}
                index={index}
                timeRanges={weekTimeRanges[index]}
                handleRangeChange={setDayTimeRanges(index)}
              />
            ),
            this
          )}
        </Grid>
      </Grid>
    </div>
  );
}
