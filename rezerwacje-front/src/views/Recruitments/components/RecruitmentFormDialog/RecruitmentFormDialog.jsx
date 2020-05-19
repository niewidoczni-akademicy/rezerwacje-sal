import React, {useState} from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import {
  Grid,
  Typography,
  Divider
}
  from "@material-ui/core"
import DateFnsUtils from '@date-io/date-fns';
import {
  MuiPickersUtilsProvider,
  KeyboardTimePicker,
  KeyboardDatePicker,
} from '@material-ui/pickers';
import useForm from '/webapp/src/common/utilities/useForm';
import validateRecruitmentForm from './validateRecruitmentForm';
import './RecruitmentFormDialog.scss';

export default function RecruitmentFormDialog(props) {

  const initState = {
        name: "",
        status: ""
  };



  const submit = () => {
    fetch("/api/recruitment", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        name: values.name,
        startTime: startDate,
        endTime: endDate,
      })
    }).then(
      function(res) {
        if (res.ok) {
          setState(initState);
          console.log(values);
          alert("Rekrutacja została dodana do bazy.");
        } else if (res.status === 400) {
          alert("Wystąpił błąd.");
        }
      },
      function(e) {
        alert("Wystąpił błąd.");
      }
    );
  };

  const { handleChange, handleSubmit, values, errors, setState } = useForm(
    initState,
    submit,
    validateRecruitmentForm
  );

  return (
    <div>
      <Dialog
        open={props.open}
        onClose={props.handleClose}
        aria-labelledby="form-dialog-title"
      >
        <DialogTitle>
          <Typography variant="h3">
            Nowa rekrutacja
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
                xs={12}
                md={12}
              >
                <TextField
                fullWidth
                margin="dense"
                name="name"
                label="Nazwa"
                onChange={handleChange}
                type="name"
                value={values.name}
                variant="outlined"
              />
              <p className="error">{errors.name}</p>
              </Grid>
              <Grid
                item
                xs={12}
                md={6}
              >
                <MuiPickersUtilsProvider utils={DateFnsUtils}>
                  <KeyboardDatePicker
                    disableToolbar
                    variant="inline"
                    format="MM/dd/yyyy"
                    margin="normal"
                    name="startDate"
                    label="Data początkowa"
                    value={values.startDate}
                    onChange={handleChange}
                    color="primary"
                    KeyboardButtonProps={{
                      'aria-label': 'change date',
                    }}
                  />
                </MuiPickersUtilsProvider>
                <p className="error">{errors.startDate}</p>
              </Grid>
              <Grid
                item
                xs={12}
                md={6}>
                </Grid>
                <MuiPickersUtilsProvider utils={DateFnsUtils}>
                  <KeyboardDatePicker
                    disableToolbar
                    variant="inline"
                    format="MM/dd/yyyy"
                    margin="normal"
                    name="endDate"
                    label="Data końcowa"
                    value={values.endDate}
                    onChange={handleChange}
                    color="primary"
                    KeyboardButtonProps={{
                      'aria-label': 'change date',
                    }}
                  />
                </MuiPickersUtilsProvider>
                <p className="error">{errors.endDate}</p>
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
    </div>
  );
}