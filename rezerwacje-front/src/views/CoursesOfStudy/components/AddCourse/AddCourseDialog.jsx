import React from 'react';
import {
  Card,
  CardHeader,
  CardContent,
  Divider,
  Grid,
  Radio,
  RadioGroup,
  FormControl,
  FormLabel,
  FormControlLabel,
  Button,
  IconButton,
  TextField,
  Typography,
  DialogActions,
} from '@material-ui/core';

import { makeStyles, useTheme } from '@material-ui/styles';
import CloseIcon from '@material-ui/icons/Close';
import Dialog from '@material-ui/core/Dialog';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import Autocomplete from '@material-ui/lab/Autocomplete';

import validateCourseForm from './validateCourseForm.js';
import useForm from './useForm.jsx';
import useAutocomplete from './useAutocomplete.jsx';

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

const AddCourseDialog = (props) => {
  const classes = useStyles();
  const initState = {
    name: '',
    faculty: '',
    courseType: 'FULL_TIME',
    contactPerson1: '',
    contactPerson2: '',
    isJoined: 'false',
    remarks: '',
  };

  const initAutocompleteState = {
    faculties: [],
    users: [],
  };
  const fetchAutocomplete = async () => {
    const facultiesPromise = fetch('/api/faculties')
      .then((response) => response.json())
      .then((json) => json['faculties'].map((faculty) => faculty['name']));

    // TODO: fetch just logins/personal data (without passwords!)
    const usersPromise = fetch('/api/system-user/all')
      .then((response) => response.json())
      .then((json) => json['systemUsers'].map((user) => user['login']));

    let [faculties, users] = await Promise.all([
      facultiesPromise,
      usersPromise,
    ]);

    return {
      faculties: faculties,
      users: users,
    };
  };
  const autoCompleteState = useAutocomplete(
    initAutocompleteState,
    fetchAutocomplete
  );

  const submit = () => {
    const {
      name,
      faculty,
      courseType,
      contactPerson1,
      contactPerson2,
      isJoined,
      remarks,
    } = values;
    console.log(faculty);
    fetch('/api/course-of-study', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        name: name,
        faculty: faculty,
        courseType: courseType,
        contactPerson1: contactPerson1,
        contactPerson2: contactPerson2,
        isJoined: isJoined,
        remarks: remarks,
      }),
    }).then(
      function (res) {
        if (res.ok) {
          setState(initState);
          console.log(values);
          alert('Kierunek został dodany do bazy.');
        } else {
          alert('Wystąpił błąd.');
        }
      },
      function (e) {
        alert('Wystąpił błąd.');
      }
    );
  };

  const { handleChange, handleSubmit, values, errors, setState } = useForm(
    initState,
    submit,
    validateCourseForm
  );

  return (
    <Dialog
      open={props.open}
      onClose={props.handleClose}
      aria-labelledby="form-dialog-title"
    >
      <DialogTitle className={classes.root}>
        <Typography variant="h3">Nowy kierunek</Typography>
        <IconButton
          aria-label="close"
          className={classes.closeButton}
          onClick={props.handleClose}
        >
          <CloseIcon />
        </IconButton>
      </DialogTitle>
      <DialogContent dividers>
        <Card>
          <CardHeader title="Nowy kierunek" />
          <Divider />
          <CardContent>
            <Grid container spacing={3}>
              <Grid item md={6} xs={12}>
                <TextField
                  fullWidth
                  label="Nazwa"
                  margin="dense"
                  name="name"
                  onChange={handleChange}
                  required
                  value={values.name}
                  variant="outlined"
                />
                <p className="error">{errors.name}</p>
              </Grid>
              <Grid item md={6} xs={12}>
                <Autocomplete
                  name="faculty"
                  options={autoCompleteState['faculties']}
                  getOptionLabel={(option) => option}
                  value={values.faculty}
                  onChange={(event, newValue) =>
                    handleChange({
                      target: {
                        name: 'faculty',
                        value: newValue,
                      },
                    })
                  }
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      fullWidth
                      label="Wydział"
                      margin="dense"
                      required
                      variant="outlined"
                    />
                  )}
                />
                <p className="error">{errors.faculty}</p>
              </Grid>
              <Grid item md={6} xs={12}>
                <TextField
                  fullWidth
                  label="Typ"
                  margin="dense"
                  name="courseType"
                  select
                  SelectProps={{ native: true }}
                  onChange={handleChange}
                  value={values.courseType}
                  variant="outlined"
                >
                  <option key="FULL_TIME" value="FULL_TIME">
                    Stacjonarny
                  </option>
                  <option key="EXTERNAL" value="EXTERNAL">
                    Zaoczny
                  </option>
                </TextField>
                <p className="error">{errors.courseType}</p>
              </Grid>
              <Grid item md={6} xs={12}>
                <Autocomplete
                  name="contactPerson1"
                  options={autoCompleteState['users']}
                  getOptionLabel={(option) => option}
                  value={values.contactPerson1}
                  onChange={(event, newValue) =>
                    handleChange({
                      target: {
                        name: 'contactPerson1',
                        value: newValue,
                      },
                    })
                  }
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      fullWidth
                      label="Kontakt 1"
                      margin="dense"
                      variant="outlined"
                    />
                  )}
                />
                <p className="error">{errors.contactPerson1}</p>
              </Grid>
              <Grid item md={6} xs={12}>
                <Autocomplete
                  name="contactPerson2"
                  options={autoCompleteState['users']}
                  getOptionLabel={(option) => option}
                  value={values.contactPerson2}
                  onChange={(event, newValue) =>
                    handleChange({
                      target: {
                        name: 'contactPerson2',
                        value: newValue,
                      },
                    })
                  }
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      fullWidth
                      label="Kontakt 2"
                      margin="dense"
                      variant="outlined"
                    />
                  )}
                />
                <p className="error">{errors.contactPerson2}</p>
              </Grid>
              <Grid item md={6} xs={12}>
                <FormControl component="fieldset">
                  <FormLabel component="legend">Łączony</FormLabel>
                  <RadioGroup
                    aria-label="isJoined"
                    value={values.isJoined}
                    name="isJoined"
                  >
                    <FormControlLabel
                      value={'true'}
                      control={<Radio />}
                      onChange={handleChange}
                      label="Tak"
                    />
                    <FormControlLabel
                      value={'false'}
                      control={<Radio />}
                      onChange={handleChange}
                      label="Nie"
                    />
                  </RadioGroup>
                </FormControl>
                <p className="error">{errors.isJoined}</p>
              </Grid>
              <Grid item md={6} xs={12}>
                <TextField
                  fullWidth
                  label="Uwagi"
                  margin="dense"
                  name="remarks"
                  onChange={handleChange}
                  value={values.remarks}
                  variant="outlined"
                />
                <p className="error">{errors.remarks}</p>
              </Grid>
            </Grid>
          </CardContent>
          <Divider />
        </Card>
      </DialogContent>
      <DialogActions>
        <Button
          color="primary"
          variant="contained"
          onClick={() => handleSubmit()}
        >
          DODAJ
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default AddCourseDialog;
