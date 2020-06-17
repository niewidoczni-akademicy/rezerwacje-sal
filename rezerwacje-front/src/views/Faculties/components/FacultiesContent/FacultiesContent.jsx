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
import { FacultyDialog, FacultiesTable } from '..';
import { useEntryList } from 'common/utilities';

const FacultiesContent = (props) => {
  const [showAddDialog, setShowAddDialog] = useState(false);
  const [showEditDialog, setShowEditDialog] = useState(false);

  const { entries, findEntry, refreshEntries } = useEntryList(
    '/api/faculties',
    'faculties'
  );

  const [initEditState, setEditState] = useState({
    id: null,
    name: '',
  });
  const initAddState = {
    id: null,
    name: '',
  };

  return (
    <React.Fragment>
      <Card style={{ width: 900 }}>
        <CardHeader title="Wydziały" />
        <Divider />
        <CardContent>
          <FacultiesTable
            entries={entries}
            onRowClick={(entryId) => {
              let editEntryCopy = { ...initEditState, ...findEntry(entryId) };
              setEditState(editEntryCopy);
              setShowEditDialog(true);
            }}
          />
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
      <FacultyDialog
        title="Dodaj wydział"
        action="DODAJ"
        message="Wydział został dodany do bazy."
        url="/api/faculties"
        httpMethod="POST"
        open={showAddDialog}
        initState={initAddState}
        onSubmitted={() => {
          setShowAddDialog(false);
          refreshEntries();
        }}
        handleClose={() => {
          setShowAddDialog(false);
        }}
      />
      <FacultyDialog
        title="Edytuj wydział"
        action="ZAPISZ"
        message="Zapisano"
        url="/api/faculties"
        httpMethod="PUT"
        open={showEditDialog}
        initState={initEditState}
        onSubmitted={() => {
          setShowEditDialog(false);
          refreshEntries();
        }}
        handleClose={() => {
          setShowEditDialog(false);
        }}
      />
    </React.Fragment>
  );
};

FacultiesContent.propTypes = {
  className: PropTypes.string,
};

export default FacultiesContent;
