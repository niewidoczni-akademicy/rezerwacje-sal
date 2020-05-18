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
import { ImportRoomsDialog, RoomsTable } from "../";
import AddRoomForm from "../AddRoom";

const RoomsContent = (props) => {
  const [showImportDialog, setShowImportDialog] = useState(false);
  const [showAddDialog, setShowAddDialog] = useState(false);

  return (
    <React.Fragment>
      <Card style={{ width: 900 }}>
        <CardHeader title="Sale" />
        <Divider />
        <CardContent>
          <RoomsTable />
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
      <AddRoomForm
        open={showAddDialog}
        handleClose={() => {
          setShowAddDialog(false);
        }}
      />
      <ImportRoomsDialog
        open={showImportDialog}
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
