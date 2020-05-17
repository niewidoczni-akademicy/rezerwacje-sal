import React from "react";
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
} from "@material-ui/core";

import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";

import validateRoomForm from "./validateRoomForm.js";
import useForm from "./useForm.jsx";

const AddRoomForm = (props) => {
  const initState = {
    name: "",
    building: "",
    capacity: "",
  };

  const submit = () => {
    const { name, building, capacity } = values;

    fetch("/api/rooms", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        name: name,
        building: building,
        capacity: capacity,
      }),
    }).then(
      function (res) {
        if (res.ok) {
          setState(initState);
          console.log(values);
          alert("Sala została dodana do bazy.");
        } else {
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
    submit,
    validateRoomForm
  );

  return (
    <Dialog
      open={props.open}
      onClose={props.handleClose}
      aria-labelledby="form-dialog-title"
    >
      <DialogTitle>
        <Typography variant="h3">Nowa sala</Typography>
      </DialogTitle>
      <DialogContent dividers>
        <Card>
          <CardHeader title="Nowa sala" />
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
                <TextField
                  fullWidth
                  label="Budynek"
                  margin="dense"
                  name="building"
                  onChange={handleChange}
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
                  onChange={handleChange}
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
          DODAJ
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default AddRoomForm;