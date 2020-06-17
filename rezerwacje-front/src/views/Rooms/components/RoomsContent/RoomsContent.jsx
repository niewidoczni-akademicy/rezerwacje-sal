import React from 'react';
import PropTypes from 'prop-types';
import { useState } from 'react';
import {
  Button,
  Card,
  CardActions,
  Divider,
  CardContent,
  CardHeader,
} from '@material-ui/core';
import { ImportRoomsDialog, RoomDialog, RoomsTable } from '../';
import { useEntryList } from 'common/utilities';

const RoomsContent = (props) => {
  const [showImportDialog, setShowImportDialog] = useState(false);
  const [showAddDialog, setShowAddDialog] = useState(false);
  const [showEditDialog, setShowEditDialog] = useState(false);

  const { entries, findEntry, refreshEntries } = useEntryList(
    '/api/rooms',
    'rooms'
  );

  const [initEditState, setEditState] = useState({
    id: null,
    name: '',
    building: '',
    capacity: '',
    availabilityHours: {},
  });
  const initAddState = {
    id: null,
    name: '',
    building: '',
    capacity: '',
    availabilityHours: {},
  };

  return (
    <React.Fragment>
      <Card style={{ width: 900 }}>
        <CardHeader title="Sale" />
        <Divider />
        <CardContent>
          <RoomsTable
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
            Dodaj salę
          </Button>
          <Button
            color="primary"
            variant="contained"
            onClick={() => setShowImportDialog(true)}
          >
            Importuj z CSV
          </Button>
        </CardActions>
      </Card>
      <RoomDialog
        title="Dodaj salę"
        action="DODAJ"
        message="Sala została dodana do bazy"
        url="/api/rooms"
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
      <RoomDialog
        title="Edytuj salę"
        action="ZAPISZ"
        message="Zapisano"
        url="/api/rooms"
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
      <ImportRoomsDialog
        open={showImportDialog}
        onUploaded={refreshEntries}
        handleClose={() => {
          setShowImportDialog(false);
        }}
      />
    </React.Fragment>
  );
};

RoomsContent.propTypes = {
  className: PropTypes.string,
};

export default RoomsContent;
