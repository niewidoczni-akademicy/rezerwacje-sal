import React from 'react';
import {
  Card,
  CardHeader,
  CardContent,
  Divider,
  Grid,
  Button,
  TextField,
  Typography,
  DialogActions,
  IconButton,
} from '@material-ui/core';
import { makeStyles, useTheme } from '@material-ui/styles';
import CloseIcon from '@material-ui/icons/Close';
import Dialog from '@material-ui/core/Dialog';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';

import validateRoomForm from './validateRoomForm.js';
import { useDialogForm } from 'common/utilities';

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

const RoomDialog = (props) => {
  const classes = useStyles();

  const submit = () => {
    const { id, name, building, capacity } = values;

    fetch(props.url, {
      method: props.httpMethod,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        id: id,
        name: name,
        building: building,
        capacity: capacity,
      }),
    }).then(
      function (res) {
        if (res.ok) {
          alert(props.message);
          props.onSubmitted();
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
  } = useDialogForm(props.initState, submit, validateRoomForm);

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
                <TextField
                  fullWidth
                  label="Budynek"
                  margin="dense"
                  name="building"
                  onChange={handleChangeEvent}
                  required
                  variant="outlined"
                  value={values.building}
                />
                <p className="error">{errors.building}</p>
              </Grid>
              <Grid item md={6} xs={12}>
                <TextField
                  fullWidth
                  label="Pojemność"
                  margin="dense"
                  name="capacity"
                  onChange={handleChangeEvent}
                  value={values.capacity}
                  variant="outlined"
                />
                <p className="error">{errors.capacity}</p>
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

export default RoomDialog;
