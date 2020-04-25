import React from 'react';
import { makeStyles } from '@material-ui/styles';
import { Grid } from '@material-ui/core';

import UserDetails from './UserDetails';

const UserForm = () => {

  return (
    <div> 
      <Grid
        container
        spacing={4}
      >
        <Grid
          item
          lg={4}
          md={6}
          xl={4}
          xs={12}
        >
          <UserDetails />
        </Grid>
      </Grid>
    </div>
  );
};

export default UserForm;
