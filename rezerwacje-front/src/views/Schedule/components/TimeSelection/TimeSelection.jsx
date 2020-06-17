import React, { useState } from 'react';
import PropTypes from 'prop-types';
import DateFnsUtils from '@date-io/date-fns';
import { KeyboardDatePicker, MuiPickersUtilsProvider } from '@material-ui/pickers';
import { 
  Card, 
  CardContent,
  Typography, 
} from '@material-ui/core';

const TimeSelection = props => {
  const { className, ...rest } = props;

  const [startDate, setStartDate] = React.useState(props.start);

  const handleStartDateChange = date => {
    setStartDate(date)
    props.updateStart(date)
  };

  const [endDate, setEndDate] = React.useState(props.end);

  const handleEndDateChange = date => {
    setEndDate(date)
    props.updateEnd(date)
  };

  return (
    <Card>
      <CardContent>
        <Typography variant="h4" gutterBottom>
          Przedział czasu
        </Typography>
        <MuiPickersUtilsProvider utils={DateFnsUtils}>
          <KeyboardDatePicker
            disableToolbar
            helperText="od"
            variant="inline"
            format="MM/dd/yyyy"
            margin="normal"
            id="date-picker-from"
            value={startDate}
            onChange={handleStartDateChange}
            KeyboardButtonProps={{
              'aria-label': 'change date',
            }}
          />
          <KeyboardDatePicker
            disableToolbar
            helperText="do"
            variant="inline"
            format="MM/dd/yyyy"
            margin="normal"
            id="date-picker-to"
            value={endDate}
            onChange={handleEndDateChange}
            KeyboardButtonProps={{
              'aria-label': 'change date',
            }}
          />
        </MuiPickersUtilsProvider>
      </CardContent>
    </Card>
  );
};

TimeSelection.propTypes = {
  className: PropTypes.string
};

export default TimeSelection;
