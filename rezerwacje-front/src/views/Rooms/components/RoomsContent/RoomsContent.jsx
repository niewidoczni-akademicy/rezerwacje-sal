import React from "react";
import PropTypes from "prop-types";
import { useState, useRef } from "react";
import {
  Button,
  Card,
  CardActions,
  Divider,
  CardContent,
  CardHeader,
} from "@material-ui/core";
import { ImportRoomsDialog, RoomsForm, RoomsTable } from "../";

const RoomsContent = (props) => {
  const [showImportDialog, setShowImportDialog] = useState(false);

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
            onClick={() => alert("idź sobie")}
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
