import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core';
import DialogTitle from '@material-ui/core/DialogTitle';
import {
  Grid,
  Typography
}
  from "@material-ui/core"
import DateFnsUtils from '@date-io/date-fns';
import {
  MuiPickersUtilsProvider,
  KeyboardTimePicker,
  KeyboardDatePicker,
} from '@material-ui/pickers';


export default function ExamFormDialog(props) {

  const [selectedDate, setSelectedDate] = React.useState(new Date(''));

  const handleDateChange = (date) => {
    setSelectedDate(date);
  };

  const courses = ["Informatyka, WIEiT, stacjonarne", "Elektronika, WIEiT, stacjonarne", "Automatyka i Robotyka, EAIiB"]
  const rooms = ["1.38, D17", "3.27a, D17", "1.23, D10"]

  return (
    <div>
      <Dialog
        open={props.open}
        onClose={props.handleClose}
        aria-labelledby="form-dialog-title"
      >
        <DialogTitle>
          <Typography variant="h3">
            Nowy egzamin
          </Typography>
        </DialogTitle>
        <DialogContent>
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
                md={12}
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
                        <option key={course} value={course}>{course}</option>
                    ))}
                </TextField>
              </Grid>
              <Grid
                item
                md={12}
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
                        <option key={room} value={room}>{room}</option>
                    ))}
                </TextField>
              </Grid>
              <Grid 
              item
              md={12}
              md={12}
              >
                <KeyboardDatePicker
          disableToolbar
          variant="inline"
          format="MM/dd/yyyy"
          margin="normal"
          id="date-picker-inline"
          label="Date picker inline"
          value={selectedDate}
          onChange={handleDateChange}
          KeyboardButtonProps={{
            'aria-label': 'change date',
          }}
        />
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
    </div>
  );
}