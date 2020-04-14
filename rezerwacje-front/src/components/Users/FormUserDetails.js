import React, { Component } from 'react'
import { ThemeProvider as MuiThemeProvider , createMuiTheme, makeStyles} from '@material-ui/core/styles'
import {AppBar, TextField, Button, Grid, Paper, FormLabel, RadioGroup, FormControlLabel, FormControl, Radio} from '@material-ui/core'
import './Users.css'
import Sidebar from '../Sidebar'
import clsx from 'clsx';


export class FormUserDetails extends Component {
    continue = e => {
        e.preventDefault();
        this.props.nextStep();
    }
    

    render() {
        const { values, handleChange } = this.props
        return (
            <MuiThemeProvider theme={theme}>
             <Sidebar />

                <React.Fragment>
                   <Grid className="Grid" container spacing={2}>
                        <Grid item className="GridItem">
                        <AppBar color="primary" className="AppBar">        
                                NOWY UŻYTKOWNIK
                            </AppBar>
                        </Grid>
                    </Grid>


                    <Paper className="Paper" elevation={3}>
                    <Grid className="Grid" container spacing={2}>
                        <Grid item xs={12}>
                            <FormLabel color="primary">
                                PODSTAWOWE DANE
                            </FormLabel>
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                helperText="Imię"
                                onChange={handleChange('firstName')}
                                defaultValue={values.firstName}
                                /> 
                        </Grid> 
                        <Grid item xs={12}>
                             <TextField
                                helperText="Nazwisko"
                                onChange={handleChange('lastName')}
                                defaultValue={values.lastName}
                                />
                        </Grid>
                    </Grid>
                    </Paper>


                    <Paper className="Paper" elevation={3}>
                    <Grid className="Grid" container spacing={2}>  
                        <Grid item xs={12} sm={12}>
                        <FormLabel color="primary">
                            DANE KONTAKTOWE
                        </FormLabel>
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                helperText="Adres email"
                                onChange={handleChange('email')}
                                defaultValue={values.lastName}
                            />    
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                helperText="Numer telefonu"
                                onChange={handleChange('phone')}
                                defaultValue={values.lastName}
                            />    
                        </Grid> 
                        </Grid>
                    </Paper>  

                    <Paper className="Paper" elevation={3}>
                    <Grid className="Grid" container spacing={1}>  
                        <Grid item xs={12} sm={12}>
                        <FormLabel color="primary">
                            DANE LOGOWANIA
                        </FormLabel>
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                helperText="Login"
                                onChange={handleChange('login')}
                                defaultValue={values.lastName}
                            />    
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                helperText="Hasło"
                                onChange={handleChange('password')}
                                type="password"
                                defaultValue={values.lastName}
                            />    
                        </Grid> 
                        <Grid item xs={12}>
                            <TextField
                                helperText="Powtórz hasło"
                                type="password"
                                onChange={handleChange('repeatPassword')}
                                defaultValue={values.lastName}
                            />    
                        </Grid> 
                    </Grid>
                    </Paper>

                    <Paper className="Paper" elevation={3}>
                    <Grid className="Grid">
                        <Grid item xs={12} sm={12}>
                            <FormLabel>UPRAWNIENIA</FormLabel>
                        </Grid>
                        <Grid item xs={12}>
                            <RadioGroup aria-label="privileges" name="privileges">
                                <FormControlLabel value="Administrator" control={<StyledRadio />} label="Administrator" onChange={handleChange('role')}/>
                                <FormControlLabel value="Użytkownik-nadzór" control={<StyledRadio />} label="Użytkownik-nadzór" onChange={handleChange('role')}/>
                                <FormControlLabel value="Użytkownik-standard" control={<StyledRadio />} label="Użytkownik standardowy" onChange={handleChange('role')}/>
                           </RadioGroup>
                        </Grid>
                    </Grid>
                    </Paper>

                     <Grid className="Grid" container spacing = {1}>
                        <Grid item xs={12} className="GridItem">
                            <Button
                                className="Grid"
                                variant="contained"
                                color="primary"
                                onClick={this.continue}>
                                Dodaj
                            </Button>    
                      </Grid>
                    </Grid>
                  </React.Fragment>
            </MuiThemeProvider>
        )
    }

    
}

let theme = createMuiTheme({
    palette: {
        primary: {
            main: '#111', 
        },
    secondary: {
        main: '#818181' 
    },},}
)


const useStyles = makeStyles({
  root: {
    '&:hover': {
      backgroundColor: 'transparent',
    },
  },
  icon: {
    borderRadius: '50%',
    width: 16,
    height: 16,
    boxShadow: 'inset 0 0 0 1px rgba(16,22,26,.2), inset 0 -1px 0 rgba(16,22,26,.1)',
    backgroundColor: '#f5f8fa',
    backgroundImage: 'linear-gradient(180deg,hsla(0,0%,100%,.8),hsla(0,0%,100%,0))',
    '$root.Mui-focusVisible &': {
      outline: '2px auto rgba(19,124,189,.6)',
      outlineOffset: 2,
    },
    'input:hover ~ &': {
      backgroundColor: '#ebf1f5',
    },
    'input:disabled ~ &': {
      boxShadow: 'none',
      background: 'rgba(206,217,224,.5)',
    },
  },
  checkedIcon: {
    backgroundColor: '#111',
    backgroundImage: 'linear-gradient(180deg,hsla(0,0%,100%,.1),hsla(0,0%,100%,0))',
    '&:before': {
      display: 'block',
      width: 16,
      height: 16,
      backgroundImage: 'radial-gradient(#fff,#fff 28%,transparent 32%)',
      content: '""',
    },
    'input:hover ~ &': {
      backgroundColor: '#111',
    },
  },
});

function StyledRadio(props) {
  const classes = useStyles();

  return (
    <Radio
      className={classes.root}
      disableRipple
      color="#111"
      checkedIcon={<span className={clsx(classes.icon, classes.checkedIcon)} />}
      icon={<span className={classes.icon} />}
      {...props}
    />
  );
}




export default FormUserDetails