import React, { Component } from 'react'
import { ThemeProvider as MuiThemeProvider , createMuiTheme, makeStyles} from '@material-ui/core/styles'
import {AppBar, TextField, Button, Grid, Paper} from '@material-ui/core'
import './Users.css'


export class FormUserDetails extends Component {
    continue = e => {
        e.preventDefault();
        this.props.nextStep();
    }

    render() {
        const { values, handleChange } = this.props
        return (
            <MuiThemeProvider theme={theme}>
                <React.Fragment>
                   <Grid className="Grid" container spacing={4}>
                        <Grid item xs={12}>
                            <AppBar color="primary">        
                                Nowy użytkownik
                            </AppBar>
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                helperText="Podaj imię"
                                label="Imię"
                                onChange={handleChange('firstName')}
                                defaultValue={values.firstName}
                                /> 
                        </Grid> 
                        <Grid item xs={12}>
                             <TextField
                                helperText="Podaj nazwisko"
                                label="Nazwisko"
                                onChange={handleChange('lastName')}
                                defaultValue={values.lastName}
                                />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                helperText="Podaj adres email"
                                label="Email"
                                onChange={handleChange('email')}
                                defaultValue={values.lastName}
                            />    
                        </Grid>  
                        <Grid item xs={12}>
                                <Button
                                    className="Grid"
                                    variant="contained"
                                    color="primary"
                                    onClick={this.continue}>
                                    Dalej
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
            main: '#115293' 
        },
    secondary: {
        main: '#4791db' 
    },},}
)

export default FormUserDetails