import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import {
  Grid,
  Typography,
  Card,
  CardContent,
  TextField
}
  from "@material-ui/core"
import { useEffect, useState } from "react";
import './SelectRecruitmentDialog.scss'

export default function SelectRecruitmentDialog(props) {
  const [recruitment, setRecruitment] = useState(-1);
  const [recruitmentList, setRecruitmentList] = useState([]);
  const [rooms, setRooms] = useState([]);
  const [assignedRooms, setAssignedRooms] = useState([]);

  const handleSubmit = () => {
    console.log(rooms);
    console.log(assignedRooms);
    const body = JSON.stringify({
      recruitmentId: props.recruitment,
      rooms: {
        roomsIds: rooms
      }
    });

    fetch("/api/connection/connect-recruitment-and-rooms", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: body
    }).then(
      function (res) {
        if (res.ok) {
          props.handleClose();
        } else {
          alert("Wystąpił błąd.");
          console.log(res.status);
        }
      },
      function (e) {
        alert("Wystąpił błąd.");
        console.log(e);
      }
    );
  };

  const handleRecruitmentChange = event => {
    console.log(event.target.value);
    setRecruitment(event.target.value);
  };

  const checkRoomAssignment = id => {
    if (assignedRooms.find(room => room === id) != undefined) return true;
    else return false;
  };

  const filterRecruitmentList = recruitmentList => {
    if (props.recruitment != undefined) {
      return recruitmentList.filter(recruitment => recruitment.id != props.recruitment);
    }
    return recruitmentList;
  };

  const getRooms = roomsList => roomsList.map(room => room.room.id);

  const filterRooms = roomsList => roomsList.filter(room => checkRoomAssignment(room));

  useEffect(() => {
    const url = "/api/recruitment/all";
    fetch(url)
      .then(res => res.json())
      .then(json => {
        const recruitmentList = filterRecruitmentList(json["recruitments"]);
        setRecruitmentList(recruitmentList);
        if (recruitmentList.length > 0)
          setRecruitment(recruitmentList[0].id);
      })
      .catch(e => console.log(e));
  }, [props.recruitment]);

  useEffect(() => {
    if (recruitment === -1) return;
    const url = `/api/recruitment/${recruitment}/rooms`;
    fetch(url)
      .then(res => res.json())
      .then(json => {
        console.log(json);
        const roomsIds = filterRooms(getRooms(json["recruitmentRooms"]));
        setRooms(roomsIds);
      })
      .catch(e => console.log(e));
  }, [recruitment]);

  useEffect(() => {
    if (props.recruitment != undefined && props.recruitment != -1) {
      const url = `/api/recruitment/${props.recruitment}/rooms`;
      fetch(url)
        .then(res => res.json())
        .then(json => {
          console.log(json);
          const roomsIds = getRooms(json["recruitmentRooms"]);
          setAssignedRooms(roomsIds);
        })
        .catch(e => console.log(e));
    }
  }, [props.recruitment]);

  return (
    <Dialog
      open={props.open}
      onClose={props.handleClose}
      aria-labelledby="form-dialog-title"
    >
      <DialogTitle>
        <Typography variant="h3">
          Wybór sal z istniejącej rekrutacji
          </Typography>
      </DialogTitle>
      <DialogContent dividers>
        <form
          autoComplete="off"
          noValidate
        >

          <Card>
            <CardContent>
              <Grid>
                <Grid item sm={6} xs={12}>
                  <Typography variant="h4" gutterBottom>
                    Rekrutacja
                                </Typography>
                </Grid>
                <Grid item sm={6} xs={12}>
                  <TextField
                    fullWidth
                    margin="dense"
                    name="recruitment"
                    onChange={handleRecruitmentChange}
                    required
                    select
                    SelectProps={{ native: true }}
                    variant="outlined"
                  >
                    {recruitmentList.map(recruitment => (
                      <option value={recruitment.id}>{recruitment.name}</option>
                    ))}
                  </TextField>
                </Grid>
              </Grid>
            </CardContent>
          </Card>
        </form>
      </DialogContent>
      <DialogActions>
        <Button onClick={props.handleClose} color="secondary" variant="contained">
          ZAMKNIJ
          </Button>
        <Button onClick={handleSubmit} color="primary" variant="contained" disabled={recruitment === -1}>
          DODAJ
          </Button>
      </DialogActions>
    </Dialog>
  );
}