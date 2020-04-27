import React from 'react';
import { makeStyles } from '@material-ui/styles';
import { Grid } from '@material-ui/core';

import {
  RoomsContent
} from './components';

const useStyles = makeStyles(theme => ({
  root: {
    padding: theme.spacing(4)
  }
}));

const Rooms = () => {
  const classes = useStyles();

  return (
    <div className={classes.root}>
      <Grid
        container
        spacing={4}
      >
        <Grid
          lg={3}
          sm={6}
          xl={3}
          xs={12}
        >
          <RoomsContent />
        </Grid>
      </Grid>
    </div>
  );
};

export default Rooms;
