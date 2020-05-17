import React from "react";
import PropTypes from "prop-types";
import { useState } from "react";
import {
  Button,
  Card,
  CardActions,
  Divider,
  CardContent,
  CardHeader,
} from "@material-ui/core";
import { ImportCoursesDialog, CoursesTable } from "../";
import AddCourseForm from "../AddCourse";

const CoursesContent = (props) => {
  const [showImportDialog, setShowImportDialog] = useState(false);
  const [showAddDialog, setShowAddDialog] = useState(false);

  return (
    <React.Fragment>
      <Card style={{ width: 900 }}>
        <CardHeader title="Kierunki" />
        <Divider />
        <CardContent>
          <CoursesTable />
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
      <AddCourseForm
        open={showAddDialog}
        handleClose={() => {
          setShowAddDialog(false);
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
