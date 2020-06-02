import React from 'react';
import {
  Card,
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

import { makeStyles } from '@material-ui/styles';
import CloseIcon from '@material-ui/icons/Close';
import Dialog from '@material-ui/core/Dialog';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import Autocomplete from '@material-ui/lab/Autocomplete';

import validateCourseForm from './validateCourseForm.js';
import useDialogForm from 'common/utilities/useDialogForm.jsx';

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

const CourseDialog = (props) => {
  const classes = useStyles();

  const submit = () => {
    const {
      id,
      name,
      faculty,
      courseType,
      contactPerson1,
      contactPerson2,
      isJoined,
      remarks,
    } = values;
    fetch(props.url, {
      method: props.httpMethod,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        id: id,
        name: name,
        faculty: faculty.name,
        courseType: courseType,
        contactPerson1: contactPerson1.login,
        contactPerson2: contactPerson2 != null ? contactPerson2.login : null,
        isJoined: isJoined,
        remarks: remarks,
      }),
    }).then(
      function (res) {
        if (res.ok) {
          alert(props.message);
        } else {
          alert('Wystąpił błąd.');
        }
      },
      function (e) {
        alert('Wystąpił błąd.');
      }
    );
  };

  const {
    handleChange,
    handleChangeEvent,
    handleSubmit,
    values,
    errors,
  } = useDialogForm(props.initState, submit, validateCourseForm);

  return (
    <Dialog
      open={props.open}
      onClose={props.handleClose}
      aria-labelledby="form-dialog-title"
    >
      <DialogTitle className={classes.root}>
        <Typography variant="h3">{props.title}</Typography>
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
          <Divider />
          <CardContent>
            <Grid container spacing={3}>
              <Grid item md={6} xs={12}>
                <TextField
                  fullWidth
                  label="Nazwa"
                  margin="dense"
                  name="name"
                  onChange={handleChangeEvent}
                  required
                  value={values.name}
                  variant="outlined"
                />
                <p className="error">{errors.name}</p>
              </Grid>
              <Grid item md={6} xs={12}>
                <Autocomplete
                  name="faculty"
                  options={props.autoCompleteValues.faculties}
                  getOptionLabel={(option) => option.name}
                  getOptionSelected={(option, value) =>
                    option.name === value.name
                  }
                  value={values.faculty}
                  onChange={(event, newValue) => {
                    handleChange('faculty', newValue);
                  }}
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
                  onChange={handleChangeEvent}
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
                  options={props.autoCompleteValues.users}
                  getOptionLabel={(option) =>
                    option.firstName + ' ' + option.lastName
                  }
                  getOptionSelected={(option, value) =>
                    option.login === value.login
                  }
                  value={values.contactPerson1}
                  onChange={(event, newValue) =>
                    handleChange('contactPerson1', newValue)
                  }
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      fullWidth
                      label="Kontakt 1"
                      margin="dense"
                      required
                      variant="outlined"
                    />
                  )}
                />
                <p className="error">{errors.contactPerson1}</p>
              </Grid>
              <Grid item md={6} xs={12}>
                <Autocomplete
                  name="contactPerson2"
                  options={props.autoCompleteValues.users}
                  getOptionLabel={(option) =>
                    option.firstName + ' ' + option.lastName
                  }
                  getOptionSelected={(option, value) =>
                    option.login === value.login
                  }
                  value={values.contactPerson2}
                  onChange={(event, newValue) =>
                    handleChange('contactPerson2', newValue)
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
                      checked={values.isJoined === true}
                      onChange={handleChangeEvent}
                      label="Tak"
                    />
                    <FormControlLabel
                      value={'false'}
                      control={<Radio />}
                      checked={values.isJoined !== true}
                      onChange={handleChangeEvent}
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
                  onChange={handleChangeEvent}
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
          {props.action}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default CourseDialog;
