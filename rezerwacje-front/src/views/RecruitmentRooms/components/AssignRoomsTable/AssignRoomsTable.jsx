import React, { useState, useEffect } from "react";
import {
    Card,
    CardHeader,
    CardContent,
    Divider,
    Grid,
    List,
    ListItem,
    ListItemText,
    ListItemSecondaryAction,
    IconButton,
    Typography,
    TextField,
    Button
} from "@material-ui/core";
import RemoveIcon from "@material-ui/icons/RemoveCircle";
import AddIcon from "@material-ui/icons/AddCircle";
import SelectRecruitmentDialog from '../SelectRecruitmentDialog';

const AssignRoomsTable = props => {
    const [rooms, setRooms] = useState([]);
    const [assignedRooms, setAssignedRooms] = useState([]);
    const [currentRoom, setCurrentRoom] = useState(-1);
    const [currentRecruitment, setCurrentRecruitment] = useState(-1);
    const [recruitmentList, setRecruitmentList] = useState([]);
    const [modalShow, setModalShow] = useState(false);

    const checkRoomAssignment = id => {
        if (assignedRooms.find(room => room.room.id === id) != undefined) return false;
        else return true;
    };

    const createRoomData = room => {
        return {
            "room": room.room,
            "id": room.id,
        }
    }
    const getRooms = rooms => rooms.map(room => createRoomData(room));
    const filterRooms = rooms => rooms.filter(room => checkRoomAssignment(room.id));

    useEffect(() => {
        const api = "/api/recruitment/all";
        fetch(api)
            .then(res => res.json())
            .then(json => {
                const recruitmentList = json["recruitments"];
                setRecruitmentList(recruitmentList);
                if (recruitmentList.length > 0)
                    setCurrentRecruitment(recruitmentList[0].id);
            })
            .catch(e => console.log(api, e));
    }, []);


    useEffect(() => {
        const api = "/api/rooms"
        fetch(api)
            .then(res => res.json()
            )
            .then(json => {
                const rooms = filterRooms(json['rooms']);
                setRooms(rooms);
            })
            .catch(e =>
                console.log(e));
    }, [assignedRooms]);

    useEffect(() => {
        if (currentRecruitment === -1) return;
        fetch(`/api/recruitment/${currentRecruitment}/rooms`)
            .then(res => res.json()
            )
            .then(json => {
                const rooms = getRooms(json['recruitmentRooms']);
                setAssignedRooms(rooms);
            })
            .catch(e =>
                console.log(e));
    }, [currentRecruitment, currentRoom]);

    const handleRecruitmentChange = event => {
        setCurrentRecruitment(event.target.value);
    }

    const handleAddRoom = room => {
        const body = JSON.stringify({
            recruitmentId: currentRecruitment,
            roomId: room.id,
        });

        fetch("/api/connection/connect-recruitment-and-room", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: body
        }).then(
            function (res) {
                if (res.ok) {
                    setCurrentRoom(room.id);
                } else {
                    alert("Wystąpił błąd.");
                }
            },
            function (e) {
                alert("Wystąpił błąd.");
            }
        );
    };

    const handleRemoveRoom = room => {
        const body = JSON.stringify({
            recruitmentRoomsIds: [room.id]
        });

        fetch(`/api/recruitment/${currentRecruitment}/rooms`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
            body: body
        }).then(
            function (res) {
                if (res.ok) {
                    setCurrentRoom(room.id);
                } else {
                    alert("Wystąpił błąd.");
                }
            },
            function (e) {
                alert("Wystąpił błąd.");
            }
        );

    }

    const handleClose = () => setModalShow(false);
    const handleAddRooms = () => setModalShow(true);

    return (
        <React.Fragment>
            <Grid container direction={"row"} style={{ width: 700 }}>
                <Grid item xs={12}>
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
                    <Grid container xs={12} style={{ marginTop: 5 }}>
                        <Grid item xs={12} sm={6}>
                            <Card style={{ minHeight: 300 }}>
                                <CardHeader title="Sale przypisane do rekrutacji" />
                                <Divider />
                                <CardContent>
                                    <List>
                                        {assignedRooms.map(room => (
                                            <ListItem value={room.id}>
                                                <ListItemText>{room.room.name + ", " + room.room.building}</ListItemText>
                                                <ListItemSecondaryAction>
                                                    <IconButton
                                                        edge="end"
                                                        aria-label="delete"
                                                        onClick={() => {
                                                            handleRemoveRoom(room);
                                                        }}>
                                                        <RemoveIcon />
                                                    </IconButton>
                                                </ListItemSecondaryAction>
                                            </ListItem>
                                        ))}
                                    </List>
                                </CardContent>
                            </Card>
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <Card style={{ minHeight: 300 }}>
                                <CardHeader title="Sale dostępne w bazie" />
                                <Divider />
                                <CardContent>
                                    <List>
                                        {rooms
                                            .map(room => (
                                                <ListItem value={room.id}>
                                                    <ListItemText>{room.name + ", " + room.building}</ListItemText>
                                                    <ListItemSecondaryAction
                                                        value={room.id}
                                                        key={room.id}
                                                    >
                                                        <IconButton
                                                            edge="end"
                                                            aria-label="delete"
                                                            onClick={() => {
                                                                handleAddRoom(room);
                                                            }}
                                                        >
                                                            <AddIcon />
                                                        </IconButton>
                                                    </ListItemSecondaryAction>
                                                </ListItem>
                                            ))}
                                    </List>
                                </CardContent>
                            </Card>
                        </Grid>
                    </Grid>
                    <Grid item xs={12} style={{ marginTop: 5 }}>
                        <Card>
                            <CardContent>
                                <Button color="primary" variant="contained" disabled={currentRecruitment == -1} onClick={handleAddRooms}>
                                    DODAJ SALE Z INNEJ REKRUTACJI
          </Button>
                            </CardContent>
                        </Card>
                    </Grid>
                </Grid>
            </Grid>
            <SelectRecruitmentDialog open={modalShow} handleClose={handleClose} recruitment={currentRecruitment} />
        </React.Fragment>
    );
};

export default AssignRoomsTable;
