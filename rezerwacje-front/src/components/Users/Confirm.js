import React, { Component } from 'react'
import { ThemeProvider as MuiThemeProvider , createMuiTheme, makeStyles} from '@material-ui/core/styles'
import {AppBar, TextField, Button, Grid, Paper, List, ListItemText, Radio, ListItem} from '@material-ui/core'
import './Users.css'
import Sidebar from '../Sidebar'
import clsx from 'clsx';


export class Confirm extends Component {
    continue = e => {
        e.preventDefault();
        this.props.nextStep();
    }

    back = e => {
        e.preventDefault();
        this.props.prevStep();
    }
    

    render() {
        const { values: {firstName, lastName, email, phone, login, role} } = this.props
        return (
            <MuiThemeProvider theme={theme}>
             <Sidebar />

                <React.Fragment>
                   <Grid className="Grid" container spacing={3}>
                        <Grid item className="GridItem">
                        <AppBar color="primary" className="AppBar">        
                                NOWY UŻYTKOWNIK
                            </AppBar>
                        </Grid>
                    </Grid>


                    <Paper className="Paper" elevation={3}>
                    <Grid className="Grid" container spacing={3}>
                        <List>
                            <ListItem>
                            <ListItemText primary="IMIĘ" secondary={firstName}/>
                            </ListItem>   
                            <ListItem>
                            <ListItemText primary="NAZWISKO" secondary={lastName}/>
                            </ListItem>   
                            <ListItem>
                                <ListItemText primary="ADRES EMAIL" secondary={email}/>
                            </ListItem>
                            <ListItem>
                                <ListItemText primary="NUMER TELEFONU" secondary={phone}/>
                            </ListItem>
                            <ListItem>
                                <ListItemText primary="LOGIN" secondary={login}/>
                            </ListItem>
                            <ListItem>
                                <ListItemText primary="UPRAWNIENIA" secondary={role}/>
                            </ListItem>
                        </List>
                    </Grid>
                    </Paper>
                     <Grid className="Grid" container spacing = {1}>
                        <Grid item xs={12} className="GridItem">
                            <Button 
                                className="Button"
                                variant="contained"
                                color="secondary"
                                onClick={this.back}>
                                COFNIJ
                            </Button>
                            

                            <Button
                                className="Button"
                                variant="contained"
                                color="primary"
                                onClick={this.continue}>
                                POTWIERDŹ
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

// Inspired by blueprintjs
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




export default Confirm