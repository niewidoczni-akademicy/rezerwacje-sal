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
import { AddFacultyForm } from '..';

const FacultiesContent = (props) => {
  const [showAddDialog, setShowAddDialog] = useState(false);

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
            onClick={() => setShowAddDialog(true)}
          >
            Dodaj wydział
          </Button>
        </CardActions>
      </Card>
      <AddFacultyForm
        open={showAddDialog}
        handleClose={() => {
          setShowAddDialog(false);
        }}
      />
    </React.Fragment>
  );
};

FacultiesContent.propTypes = {
  className: PropTypes.string,
};

export default FacultiesContent;
