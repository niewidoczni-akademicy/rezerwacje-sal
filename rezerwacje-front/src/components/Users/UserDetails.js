import React, { useState } from 'react';
import clsx from 'clsx';
import { makeStyles } from '@material-ui/styles';
import {
  Card,
  CardHeader,
  CardContent,
  CardActions,
  Divider,
  Grid,
  Button,
  TextField,
  Typography,
  Radio, 
  RadioGroup,
  FormControlLabel
} from '@material-ui/core';
import useForm from "./useForm"
import validateUserForm from "./validateUserForm"
import './User.css'

const useStyles = makeStyles(() => ({
  root: {}
}));

const UserDetails = () => {

   const initState = {
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    login: '',
    password: '',
    confirmPassword: '',
    role: ''
  };

  const classes = useStyles();

  const submit = e => {
      const {firstName, lastName, email, phone, login, password, role} = values
      console.log(JSON.stringify({firstName: firstName, lastName: lastName, emailAddress: email, phoneNumber: phone, 
        login: login,  password: password, userType: role}))
      fetch("/api/system-user", {
          method: "POST", 
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({firstName: firstName, lastName: lastName, emailAddress: email, phoneNumber: phone, 
      login: login,  password: password, userType: role}),
        }).then(
          function (res) {
            if (res.ok) {
              alert("Użytkownik został dodany do bazy.");
            } else if (res.status === 400) {
              alert("Wystąpił błąd.");
            }
          },
          function (e) {
            alert("Wystąpił błąd.");
          }
        );
  }

  const { handleChange, handleSubmit, values, errors } = useForm(
    initState,
    submit,
    validateUserForm
  );
 
  return (
    <Card
    >
      <form
        onSubmit={handleSubmit}
        autoComplete="off"
        noValidate
      >
        <CardHeader
          title="Nowy użytkownik"
        />
        <Divider />
        <CardContent>
          <Grid
            container
            spacing={3}
          >
            <Grid
              item
              md={12}
              xs={12}
            >
            <Typography
               gutterBottom
               variant="h6">
                 Podstawowe dane
             </Typography>
              </Grid>
            <Grid
              item
              md={6}
              xs={12}
            >
              <TextField
                fullWidth
                label="Imię"
                margin="dense"
                name="firstName"
                onChange={handleChange}
                required
                value={values.firstName}
                variant="outlined"
              />
              <p className="error">{errors.firstName}</p>
            </Grid>
            <Grid
              item
              md={6}
              xs={12}
            >
              <TextField
                fullWidth
                label="Nazwisko"
                margin="dense"
                name="lastName"
                onChange={handleChange}
                required
                variant="outlined"
                value={values.lastName}
              />
              <p className="error">{errors.lastName}</p>
            </Grid>
            <Grid
              item
              md={12}
              xs={12}
            >
            <Typography
               gutterBottom
               variant="h6">
                 Dane kontaktowe
             </Typography>
              </Grid>
            <Grid
              item
              md={6}
              xs={12}
            >
              <TextField
                fullWidth
                label="Adres email"
                margin="dense"
                name="email"
                onChange={handleChange}
                required
                variant="outlined"
                value={values.email}
              />
              <p className="error">{errors.email}</p>
            </Grid>
            <Grid
              item
              md={6}
              xs={12}
            >
              <TextField
                fullWidth
                label="Numer telefonu"
                margin="dense"
                name="phone"
                onChange={handleChange}
                type="phone"
                value={values.phone}
                variant="outlined"
              />
              <p className="error">{errors.phone}</p>
            </Grid>
            <Grid
              item
              md={12}
              xs={12}
            >
            <Typography
               gutterBottom
               variant="h6">
                 Dane logowania
             </Typography>
             </Grid>
            <Grid
              item
              md={12}
              xs={12}
            >
              <TextField
                fullWidth
                label="Login"
                margin="dense"
                name="login"
                onChange={handleChange}
                required
                value={values.login}
                variant="outlined"
              />
              <p className="error">{errors.login}</p>
            </Grid>
            <Grid
              item
              md={6}
              xs={12}
            >
              <TextField
                fullWidth
                label="Hasło"
                margin="dense"
                name="password"
                type="password"
                onChange={handleChange}
                required
                value={values.password}
                variant="outlined"
              />
            <p className="error">{errors.password}</p>
            </Grid>
            <Grid
              item
              md={6}
              xs={12}
            >
           <TextField
                fullWidth
                label="Powtórz hasło"
                margin="dense"
                name="confirmPassword"
                type="password"
                onChange={handleChange}
                required
                variant="outlined"
              />
           <p className="error">{errors.confirmPassword}</p>
           </Grid>
           <Grid
              item
              md={12}
              xs={12}
            >
            <Typography
               gutterBottom
               variant="h6">
                 Uprawnienia
             </Typography>
             </Grid>
          </Grid>
          <Grid
          item
          md={12}
          xs={12}>
          <RadioGroup name="role">
               <FormControlLabel value="ADMINISTRATOR" control={<Radio color='primary'/>} label="Administrator" onChange={handleChange}/>
               <FormControlLabel value="SUPERVISOR" control={<Radio color='primary'/>} label="Użytkownik-nadzór" onChange={handleChange}/>
               <FormControlLabel value="STANDARD" control={<Radio color='primary'/>} label="Użytkownik standardowy" onChange={handleChange}/>
          </RadioGroup>
           <p className="error">{errors.role}</p>
          </Grid>
        </CardContent>
        <Divider />
        <CardActions>
          <Button
            color="primary"
            variant="contained"
            type="submit"
          >
            DODAJ
          </Button>
        </CardActions>
      </form>
    </Card>
  );
};

export default UserDetails;
