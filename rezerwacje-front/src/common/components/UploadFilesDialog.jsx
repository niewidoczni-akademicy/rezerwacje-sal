import React from 'react';
import { useRef, useState } from 'react';
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Divider,
  IconButton,
  List,
  ListItem,
  ListItemText,
  Typography,
} from '@material-ui/core';
import { makeStyles, useTheme } from '@material-ui/styles';
import CloseIcon from '@material-ui/icons/Close';

const uploadFiles = (url, callback) => (files) => {
  for (let i = 0; i < files.length; i++) {
    const data = new FormData();
    data.append('file', files[i]);

    fetch(url, {
      method: 'POST',
      body: data,
    }).then(
      function (res) {
        if (res.ok) {
          alert('Zapisano.');
          callback();
        } else {
          alert('Wystąpił błąd.');
        }
      },
      function (e) {
        alert('Wystąpił błąd.');
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
    position: 'absolute',
    right: theme.spacing(1),
    top: theme.spacing(1),
    color: theme.palette.grey[500],
  },
}));

function UploadFilesDialog(props) {
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

  const upload = uploadFiles(props.url, props.onUploaded);

  return (
    <Dialog open={props.open} onClose={onClose}>
      <DialogTitle className={classes.root}>
        <Typography variant="h3">{props.title}</Typography>
        <IconButton className={classes.closeButton} onClick={props.handleClose}>
          <CloseIcon />
        </IconButton>
      </DialogTitle>
      <Divider />
      <DialogContent>
        <List>
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
          style={{ display: 'none' }}
        />
      </DialogContent>
      <DialogActions>
        <Button
          color="primary"
          variant="contained"
          onClick={() => inputHandle.current.click()}
        >
          Wybierz pliki
        </Button>
        <Button
          color="primary"
          variant="contained"
          onClick={() => upload(files)}
        >
          Importuj
        </Button>
      </DialogActions>
    </Dialog>
  );
}

export default UploadFilesDialog;
