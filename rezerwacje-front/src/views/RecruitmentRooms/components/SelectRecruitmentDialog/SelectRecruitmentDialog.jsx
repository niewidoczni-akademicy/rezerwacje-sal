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
  TextField,
  CardHeader,
  Divider,
  List,
  ListItem,
  ListItemText
}
  from "@material-ui/core"
import { useEffect, useState } from "react";

export default function SelectRecruitmentDialog(props) {
  const [recruitment, setRecruitment] = useState(-1);
  const [recruitmentList, setRecruitmentList] = useState([]);
  const [rooms, setRooms] = useState([]);
  const [assignedRooms, setAssignedRooms] = useState([]);

  const handleSubmit = () => {
    const body = JSON.stringify({
      recruitmentId: props.recruitment,
      rooms: {
        roomsIds: rooms.map(room => room.id)
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
    setRecruitment(event.target.value);
  };

  const checkRoomAssignment = id => {
    if (assignedRooms.find(room => room === id) != undefined) return false;
    else return true;
  };

  const filterRecruitmentList = recruitmentList => {
    if (props.recruitment != undefined) {
      return recruitmentList.filter(recruitment => recruitment.id != props.recruitment);
    }
    return recruitmentList;
  };

  const getRooms = roomsList => roomsList.map(room => room.room);

  const getRoomsIds = roomsList => roomsList.map(room => room.room.id);

  const filterRooms = roomsList => roomsList.filter(room => checkRoomAssignment(room.id));

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
  }, [assignedRooms]);

  useEffect(() => {
    if (props.recruitment != undefined && props.recruitment != -1) {
      const url = `/api/recruitment/${props.recruitment}/rooms`;
      fetch(url)
        .then(res => res.json())
        .then(json => {
          console.log(json);
          const roomsIds = getRoomsIds(json["recruitmentRooms"]);
          setAssignedRooms(roomsIds);
        })
        .catch(e => console.log(e));
    }
  }, [recruitment]);

  return (
    <Dialog
      open={props.open}
      onClose={props.handleClose}
      aria-labelledby="form-dialog-title"
    >
      <DialogTitle>
        <Typography variant="h3">
          Wybór sal z innej rekrutacji
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
                <Grid item sm={12} xs={12}>
                  <Typography variant="h4" gutterBottom>
                    Rekrutacja
                                </Typography>
                </Grid>
                <Grid item sm={12} xs={12}>
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
                <Grid item xs={12} sm={12}>
                  <Card style={{ minHeight: 300 }}>
                    <CardHeader title="Lista sal do dodania" />
                    <Divider />
                    <CardContent>
                      <List>
                        {rooms
                          .map(room => (
                            <ListItem value={room.id}>
                              <ListItemText>{room.name + ", " + room.building}</ListItemText>
                            </ListItem>
                          ))}
                      </List>
                    </CardContent>
                  </Card>
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