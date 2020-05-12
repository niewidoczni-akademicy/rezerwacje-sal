import React from "react";
import { useRef, useState } from "react";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";

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

const ImportRoomsDialog = (props) => {
  const inputHandle = useRef(null);
  const [files, setFiles] = useState([]);

  const onFileInput = (fileList) => {
    setFiles(fileList);
  };

  return (
    <Dialog
      open={props.open}
      onClose={props.handleClose}
      aria-labelledby="form-dialog-title"
    >
      <DialogTitle>Import z pliku CSV</DialogTitle>
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
