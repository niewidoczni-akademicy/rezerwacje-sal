import React from "react";
import { useRef, useState } from "react";
import { useEffect, useRef, useState } from "react";
import { makeStyles, useTheme } from "@material-ui/styles";
import Button from "@material-ui/core/Button";
import IconButton from "@material-ui/core/IconButton";
import CloseIcon from "@material-ui/icons/Close";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import { Typography } from "@material-ui/core";

const uploadRoomFiles = (files) => {
  for (let i = 0; i < files.length; i++) {
    const data = new FormData();
    data.append("file", files[i]);

    fetch("/api/rooms/upload", {
      method: "POST",
      body: data,
    }).then(
      function (res) {
        if (res.ok) {
          alert("Zapisano.");
        } else {
          alert("Wystąpił błąd.");
        }
      },
      function (e) {
        alert("Wystąpił błąd.");
      }
    );
  }
};

const useStyles = makeStyles((theme) => ({
  root: {
    margin: 0,
    padding: theme.spacing(2),
  },
  closeButton: {
    position: "absolute",
    right: theme.spacing(1),
    top: theme.spacing(1),
    color: theme.palette.grey[500],
  },
}));

const ImportRoomsDialog = (props) => {
  const classes = useStyles();
  const inputHandle = useRef(null);
  const [files, setFiles] = useState([]);

  const onFileInput = (fileList) => {
    setFiles(fileList);
  };

  const onClose = () => {
    setFiles([]);
    props.handleClose();
  };

  return (
    <Dialog
      open={props.open}
      onClose={onClose}
      aria-labelledby="form-dialog-title"
    >
      <DialogTitle className={classes.root}>
        Import z pliku CSV
        <IconButton
          aria-label="close"
          className={classes.closeButton}
          onClick={onClose}
        >
          <CloseIcon />
        </IconButton>
      </DialogTitle>
      <DialogContent>
        <List component="nav" aria-label="main mailbox folders">
          {Array.from(files).map((f) => (
            <ListItem>
              <ListItemText>{f.name}</ListItemText>
            </ListItem>
          ))}
        </List>
        <input
          type="file"
          id="file"
          ref={inputHandle}
          multiple="true"
          onInput={(e) => onFileInput(e.target.files)}
          style={{ display: "none" }}
        />
      </DialogContent>
      <DialogActions>
        <Button
          color="primary"
          variant="contained"
          onClick={() => inputHandle.current.click()}
        >
          Wybierz plik
        </Button>
        <Button
          color="primary"
          variant="contained"
          onClick={() => uploadRoomFiles(files)}
        >
          Importuj
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default ImportRoomsDialog;
