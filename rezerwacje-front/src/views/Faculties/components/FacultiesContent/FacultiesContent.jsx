import React from 'react';
import PropTypes from 'prop-types';
import { useState, useRef } from 'react';
import {
  Button,
  Card,
  CardActions,
  Divider,
  CardContent,
  CardHeader,
} from '@material-ui/core';
import { FacultiesTable } from '..';

const FacultiesContent = (props) => {
  return (
    <React.Fragment>
      <Card style={{ width: 900 }}>
        <CardHeader title="Wydziały" />
        <Divider />
        <CardContent>
          <FacultiesTable />
        </CardContent>
        <Divider />
        <CardActions>
          <Button
            color="primary"
            variant="contained"
            onClick={() => alert('not implemented')}
          >
            Dodaj wydział
          </Button>
        </CardActions>
      </Card>
    </React.Fragment>
  );
};

FacultiesContent.propTypes = {
  className: PropTypes.string,
};

export default FacultiesContent;
