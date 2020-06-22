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
import validateRecruitmentCycleForm from './validateRecruitmentCycleForm';
import './RecruitmentCycleFormDialog.scss';

export default function RecruitmentCycleFormDialog(props) {

  const studiesTypes = [
    {value: "FULL_TIME", text: "Studia dzienne"}, 
    {value: "PART_TIME", text: "Studia zaoczne"},
  ]

  const studiesLevels = [
    {value: "FIRST_DEGREE", text: "Pierwszy stopień"}, 
    {value: "SECOND_DEGREE", text: "Drugi stopień"},
  ]

  const initState = {
    type: studiesTypes[0].value,
    level: studiesLevels[0].value,
  };

  const [startTime, setStartTime] = useState(new Date());
  const [endTime, setEndTime] = useState(new Date());

  const handleStartTimeChange = date => setStartTime(date);
  const handleEndTimeChange = date => setEndTime(date);


  const submitRecruitmentCycle = () => {
    fetch("/api/recruitment-period", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        startDate: startTime.toISOString().slice(0, 10),
        endDate: endTime.toISOString().slice(0, 10),
        studyType: values.type,
        studyDegree: values.level,
        recruitmentId: props.recruitmentId,
      })
    }).then(
      function (res) {
        if (res.ok) {
          setState(initState);
          console.log(values);
          alert("Cykl Rekrutacyjny został dodany do bazy.");
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

  const { handleChange, handleSubmit, values, errors, setState } = useForm(
    initState,
    submitRecruitmentCycle,
    validateRecruitmentCycleForm
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
            Nowy cykl rekrutacyjny
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
              >
                <TextField
                  fullWidth
                  margin="dense"
                  name="type"
                  onChange={handleChange}
                  required
                  select
                  SelectProps={{ native: true }}
                  variant="outlined"
                >
                  {studiesTypes.map(type => (
                    <option value={type.value}>{type.text}</option>
                  ))}
                </TextField>
                <p className="error">{errors.type}</p>
              </Grid>
              <Grid
                item
                xs={12}
              >
                <TextField
                  fullWidth
                  margin="dense"
                  name="level"
                  onChange={handleChange}
                  required
                  select
                  SelectProps={{ native: true }}
                  variant="outlined"
                >
                  {studiesLevels.map(level => (
                    <option value={level.value}>{level.text}</option>
                  ))}
                </TextField>
                <p className="error">{errors.level}</p>
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