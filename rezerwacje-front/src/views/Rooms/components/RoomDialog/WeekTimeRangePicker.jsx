import React, { Fragment } from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import {
  Button,
  Divider,
  FormControl,
  Grid,
  IconButton,
  List,
  ListItem,
  MenuItem,
  Select,
} from '@material-ui/core';
import CloseIcon from '@material-ui/icons/Close';
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
const EngDOW = Object.freeze([
  'MONDAY',
  'TUESDAY',
  'WEDNESDAY',
  'THURSDAY',
  'FRIDAY',
  'SATURDAY',
  'SUNDAY',
]);
function TimeRangePicker(props) {
  const { startDate, endDate, onChange } = props;

  const onStartDateChange = (date) => {
    onChange([date, endDate]);
  };

  const onEndDateChange = (date) => {
    onChange([startDate, date]);
  };

  return (
    <MuiPickersUtilsProvider utils={DateFnsUtils}>
      <KeyboardTimePicker
        margin="normal"
        label="Od godziny"
        value={startDate}
        format="HH:mm"
        onChange={onStartDateChange}
        KeyboardButtonProps={{
          'aria-label': 'change time',
        }}
      />
      <KeyboardTimePicker
        margin="normal"
        label="Do godziny"
        value={endDate}
        format="HH:mm"
        onChange={onEndDateChange}
        KeyboardButtonProps={{
          'aria-label': 'change time',
        }}
      />
    </MuiPickersUtilsProvider>
  );
}

function TimeRangeList(props) {
  const {
    index,
    value,
    timeRanges,
    handleRangeChange,
    handleRangeDelete,
  } = props;

  const setTimeRange = (index) => (range) => {
    handleRangeChange(index, range);
  };

  return (
    <Fragment>
      {index == value && (
        <List>
          {timeRanges.length !== 0 ? (
            timeRanges.map(
              (range, index) => (
                <Fragment>
                  <ListItem>
                    <TimeRangePicker
                      startDate={range[0]}
                      onChange={setTimeRange(index)}
                      endDate={range[1]}
                    />
                    <IconButton
                      aria-label="close"
                      onClick={() => handleRangeDelete(index)}
                    >
                      <CloseIcon />
                    </IconButton>
                  </ListItem>
                  <Divider />
                </Fragment>
              ),
              this
            )
          ) : (
            <Fragment>
              <ListItem>
                <Typography variant="body1">Niedostępna</Typography>
              </ListItem>
              <Divider />
            </Fragment>
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

export default function WeekTimeRangePicker(props) {
  const classes = useStyles();

  const [selectedDay, setSelectedDay] = React.useState(0);

  const handleDayChange = (event) => {
    setSelectedDay(event.target.value);
  };

  const dayTimeRanges = (dayIndex, allTimeRanges) =>
    allTimeRanges.filter((element) => element.dayOfWeek === EngDOW[dayIndex]);

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
            onClick={() => props.addRange(selectedDay)}
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
                timeRanges={dayTimeRanges(index, props.weekTimeRanges)}
                handleRangeChange={props.changeRange(selectedDay)}
                handleRangeDelete={props.deleteRange(selectedDay)}
              />
            ),
            this
          )}
        </Grid>
      </Grid>
    </div>
  );
}
