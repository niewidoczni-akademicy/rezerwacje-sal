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
import { ImportCoursesDialog, CourseDialog, CoursesTable } from '../';
import { useAutocomplete, useEntryList } from 'common/utilities';

const fetchPromises = [
  // TODO: don't fetch passwords o.O
  fetch('/api/faculties')
    .then((response) => response.json())
    .then((json) =>
      json['faculties'].map((faculty) => {
        return {
          id: faculty.id,
          name: faculty.name,
        };
      })
    ),

  fetch('/api/system-user/all')
    .then((response) => response.json())
    .then((json) =>
      json['systemUsers'].map((user) => {
        return {
          id: user.id,
          login: user.login,
          firstName: user.firstName,
          lastName: user.lastName,
        };
      })
    ),
];

const CoursesContent = (props) => {
  const [showImportDialog, setShowImportDialog] = useState(false);
  const [showAddDialog, setShowAddDialog] = useState(false);
  const [showEditDialog, setShowEditDialog] = useState(false);

  const { entries, findEntry, refreshEntries } = useEntryList(
    '/api/course-of-study',
    'courseOfStudies'
  );
  const acValues = useAutocomplete(['faculties', 'users'], fetchPromises);

  const [initEditState, setEditState] = useState({
    id: null,
    name: '',
    faculty: null,
    courseType: 'FULL_TIME',
    contactPerson1: null,
    contactPerson2: null,
    isJoined: false,
    remarks: '',
  });
  const initAddState = {
    id: null,
    name: '',
    faculty: null,
    courseType: 'FULL_TIME',
    contactPerson1: null,
    contactPerson2: null,
    isJoined: false,
    remarks: '',
  };

  return (
    <React.Fragment>
      <Card style={{ width: 900 }}>
        <CardHeader title="Kierunki" />
        <Divider />
        <CardContent>
          <CoursesTable
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
            Dodaj kurs
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
      <CourseDialog
        title="Nowy kierunek"
        action="DODAJ"
        message="Kierunek został dodany do bazy"
        url="/api/course-of-study"
        httpMethod="POST"
        open={showAddDialog}
        initState={initAddState}
        autoCompleteValues={acValues}
        onSubmitted={() => {
          setShowAddDialog(false);
          refreshEntries();
        }}
        handleClose={() => {
          setShowAddDialog(false);
        }}
      />
      <CourseDialog
        title="Edytuj kierunek"
        action="ZAPISZ"
        message="Zapisano"
        url="/api/course-of-study"
        httpMethod="PUT"
        open={showEditDialog}
        initState={initEditState}
        autoCompleteValues={acValues}
        onSubmitted={() => {
          setShowEditDialog(false);
          refreshEntries();
        }}
        handleClose={() => {
          setShowEditDialog(false);
        }}
      />
      <ImportCoursesDialog
        open={showImportDialog}
        onUploaded={refreshEntries}
        handleClose={() => {
          setShowImportDialog(false);
        }}
      />
    </React.Fragment>
  );
};

CoursesContent.propTypes = {
  className: PropTypes.string,
};

export default CoursesContent;
