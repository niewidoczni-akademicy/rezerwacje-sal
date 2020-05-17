import React from "react";
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
  TextField,
  Typography,
  DialogActions,
} from "@material-ui/core";

import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";

import validateCourseForm from "./validateCourseForm.js";
import useForm from "./useForm.jsx";

const AddCourseForm = (props) => {
  const initState = {
    name: "",
    faculty: "",
    courseType: "FULL_TIME",
    contactPerson1: "",
    contactPerson2: "",
    isJoined: "false",
    remarks: "",
  };

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

    fetch("/api/course-of-study", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
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
          alert("Kierunek został dodany do bazy.");
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
    validateCourseForm
  );

  return (
    <Dialog
      open={props.open}
      onClose={props.handleClose}
      aria-labelledby="form-dialog-title"
    >
      <DialogTitle>
        <Typography variant="h3">Nowy kierunek</Typography>
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
                <TextField
                  fullWidth
                  label="Wydział"
                  margin="dense"
                  name="faculty"
                  onChange={handleChange}
                  required
                  variant="outlined"
                  value={values.faculty}
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
                <TextField
                  fullWidth
                  label="Kontakt 1"
                  margin="dense"
                  name="contactPerson1"
                  onChange={handleChange}
                  value={values.contactPerson1}
                  variant="outlined"
                />
                <p className="error">{errors.contactPerson1}</p>
              </Grid>
              <Grid item md={6} xs={12}>
                <TextField
                  fullWidth
                  label="Kontakt 2"
                  margin="dense"
                  name="contactPerson2"
                  onChange={handleChange}
                  value={values.contactPerson2}
                  variant="outlined"
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
                      value={"true"}
                      control={<Radio />}
                      onChange={handleChange}
                      label="Tak"
                    />
                    <FormControlLabel
                      value={"false"}
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

export default AddCourseForm;
