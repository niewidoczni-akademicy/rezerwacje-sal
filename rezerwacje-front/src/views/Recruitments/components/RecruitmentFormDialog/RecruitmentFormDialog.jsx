import React, { useState } from 'react';
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

  const [startTime, setStartTime] = useState(new Date());
  const [endTime, setEndTime] = useState(new Date());

  const handleStartTimeChange = date => setStartTime(date);
  const handleEndTimeChange = date => setEndTime(date);


  const submit = () => {
    fetch("/api/recruitment", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        name: values.name,
        startTime: startTime.toISOString().slice(0, 19),
        endTime: endTime.toISOString().slice(0, 19),
        recruitmentStatus: translateStatusType(values.status)
      })
    }).then(
      function (res) {
        if (res.ok) {
          setState(initState);
          console.log(values);
          alert("Rekrutacja została dodana do bazy.");
          window.location.reload();
        } else if (res.status === 400) {
          alert("Wystąpił błąd.");
        }
      },
      function (e) {
        alert("Wystąpił błąd.");
      }
    );
  };


  const translateStatusType = status => {
    if (status == "W przygotowaniu") {
      return "IN_PREPARATION";
    } else if (status == "Trwa") {
      return "IN_PROGRESS";
    } else return "FINISHED";
  }

  const recruitmentStatusTypes = ["W przygotowaniu", "Trwa", "Zakończona"]

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
              >
                <TextField
                  fullWidth
                  margin="dense"
                  name="status"
                  onChange={handleChange}
                  required
                  select
                  SelectProps={{ native: true }}
                  variant="outlined"
                >
                  {recruitmentStatusTypes.map(status => (
                    <option value={status}>{status}</option>
                  ))}
                </TextField>
                <p className="error">{errors.status}</p>
              </Grid>
              <Grid
                item
                xs={6}
                md={12}
              >
                <MuiPickersUtilsProvider utils={DateFnsUtils}>
                  <KeyboardDatePicker
                    disableToolbar
                    variant="inline"
                    format="MM/dd/yyyy"
                    margin="normal"
                    label="Data początkowa"
                    value={startTime}
                    onChange={handleStartTimeChange}
                    color="primary"
                    KeyboardButtonProps={{
                      'aria-label': 'change date',
                    }}
                  />
                </MuiPickersUtilsProvider>
              </Grid>
              <Grid
                item
                xs={6}
                md={12}>
              </Grid>
              <MuiPickersUtilsProvider utils={DateFnsUtils}>
                <KeyboardDatePicker
                  disableToolbar
                  variant="inline"
                  format="MM/dd/yyyy"
                  margin="normal"
                  label="Data końcowa"
                  value={endTime}
                  onChange={handleEndTimeChange}
                  color="primary"
                  KeyboardButtonProps={{
                    'aria-label': 'change date',
                  }}
                />

              </MuiPickersUtilsProvider>
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