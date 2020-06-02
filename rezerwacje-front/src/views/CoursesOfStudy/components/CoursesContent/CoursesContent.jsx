import React from 'react';
import PropTypes from 'prop-types';
import { useEffect, useState } from 'react';
import {
  Button,
  Card,
  CardActions,
  Divider,
  CardContent,
  CardHeader,
  useForkRef,
} from '@material-ui/core';
import { ImportCoursesDialog, CourseDialog, CoursesTable } from '../';

// Hook to maintain and search list of displayed courses
function useEntryList() {
  const [entries, setEntries] = useState([]);

  const findEntry = (id) => {
    for (var i = 0; i < entries.length; ++i)
      if (entries[i].id === id) return entries[i];
    return null;
  };

  useEffect(() => {
    fetch('/api/course-of-study')
      .then((res) => res.json())
      .then((json) => {
        const entryList = json['courseOfStudies'];
        setEntries(entryList);
      })
      .catch((e) => console.log(e));
  }, []);

  return {
    entries,
    findEntry,
  };
}

// Hook to maintain all autocomplete values for forms
// TODO: use lazy loading?
function useAutocomplete() {
  const [values, setValues] = useState({
    faculties: [],
    users: [],
  });

  const fetchAndSetValues = async () => {
    // Fetch faculties and users in parallel
    // TODO: don't fetch passwords o.O
    const facultiesPromise = fetch('/api/faculties')
      .then((response) => response.json())
      .then((json) =>
        json['faculties'].map((faculty) => {
          return {
            id: faculty.id,
            name: faculty.name,
          };
        })
      );

    const usersPromise = fetch('/api/system-user/all')
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
      );

    let [faculties, users] = await Promise.all([
      facultiesPromise,
      usersPromise,
    ]);

    setValues({ ...values, faculties: faculties, users: users });
  };

  useEffect(() => {
    fetchAndSetValues();
  }, []);

  return values;
}

const CoursesContent = (props) => {
  const [showImportDialog, setShowImportDialog] = useState(false);
  const [showAddDialog, setShowAddDialog] = useState(false);
  const [showEditDialog, setShowEditDialog] = useState(false);

  const { entries, findEntry } = useEntryList();
  const acValues = useAutocomplete();

  const [editEntry, setEditEntry] = useState({
    id: null,
    name: '',
    faculty: null,
    courseType: 'FULL_TIME',
    contactPerson1: null,
    contactPerson2: null,
    isJoined: 'false',
    remarks: '',
  });
  const initAddState = {
    id: null,
    name: '',
    faculty: null,
    courseType: 'FULL_TIME',
    contactPerson1: null,
    contactPerson2: null,
    isJoined: 'false',
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
              let editEntryCopy = { ...editEntry, ...findEntry(entryId) };
              setEditEntry(editEntryCopy);
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
        initState={editEntry}
        autoCompleteValues={acValues}
        handleClose={() => {
          setShowEditDialog(false);
        }}
      />
      <ImportCoursesDialog
        open={showImportDialog}
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
