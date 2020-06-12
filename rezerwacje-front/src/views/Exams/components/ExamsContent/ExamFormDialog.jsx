import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import {
  Grid,
  Typography,
}
  from "@material-ui/core"
import DateFnsUtils from '@date-io/date-fns';
import {
  MuiPickersUtilsProvider,
  KeyboardTimePicker,
  KeyboardDatePicker,
} from '@material-ui/pickers';
import { useEffect, useState } from "react";
import { convertDegree, convertType } from "./conversion";
import validateExamForm from "./validateExamForm";
import './ExamFormDialog.scss'


export default function ExamFormDialog(props) {
  const [selectedDate, setSelectedDate] = React.useState(new Date());
  const [courses, setCourses] = useState([]);
  const [rooms, setRooms] = useState([]);
  const [startTime, setStartTime] = useState(new Date());
  const [endTime, setEndTime] = useState(new Date());
  const [roomId, setRoomId] = useState(-1);
  const [courseId, setCourseId] = useState(-1);
  const [errors, setErrors] = useState([]);
  const [recruitmentName, setRecruitmentName] = useState('');
  const [periodName, setPeriodName] = useState('');
  const [studyType, setStudyType] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [periodDetails, setPeriodDetails] = useState({
  });

  const handleDateChange = (date) => {
    setSelectedDate(date);
  };

  const submit = () => {
    const body = JSON.stringify({
      day: getDate(selectedDate),
      timeStart: getTime(startTime),
      timeEnd: getTime(endTime),
      recruitmentPeriodId: props.period,
      courseOfStudyId: courseId,
      recruitmentRoomId: roomId
    });
    console.log(body);

    fetch("/api/exam-terms", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: body
    }).then(
      function (res) {
        if (res.ok) {
          alert("Egzamin został dodany do bazy.");
          props.handleClose();
        } else {
          alert("Wystąpił błąd.");
        }
      },
      function (e) {
        alert("Wystąpił błąd.");
      }
    );
  };

  const handleSubmit = async event => {
    event.preventDefault();
    let room = -1;
    if (roomId != -1)
      room = getRoom();
    setErrors(validateExamForm(courseId, roomId, getDate(selectedDate), getTime(startTime), getTime(endTime), periodDetails, room));
    setIsSubmitting(true);
  };

  const handleCourseChange = event => setCourseId(event.target.value);

  const handleRoomChange = event => setRoomId(event.target.value);

  const handleStartTimeChange = (selectedTime) => setStartTime(selectedTime);

  const handleEndTimeChange = (selectedTime) => setEndTime(selectedTime);

  const createRoomData = room => {
    return {
      "id": room.id,
      "room": room.room,
      "availableFrom": room.availableFrom,
      "availableTo": room.availableTo
    }
  };

  const getRooms = rooms => rooms.map(room => createRoomData(room));

  const getTime = time => time.toLocaleString('sv', { timeZoneName: 'short' }).split(" ")[1].slice(0, 6) + "00";

  const getDate = date => date.toISOString().split("T")[0].slice(0, 19);

  const getRoom = () => rooms.filter(room => room.id == roomId)[0];

  useEffect(() => {
    if (Object.keys(errors).length === 0 && isSubmitting) {
      submit();
    } else {
      console.log(errors);
    }
  }, [errors]);

  useEffect(() => {
    if (props.recruitment === "") return;
    fetch(`/api/recruitment/${props.recruitment}`)
      .then(res => res.json())
      .then(json => {
        setRecruitmentName(json["recruitment"]["name"]);
      })
      .catch(e => console.log(e));
  }, [props.recruitment]);

  useEffect(() => {
    if (props.period === "") return;
    fetch(`/api/recruitment-period/${props.period}`)
      .then(res => res.json())
      .then(json => {
        const periodJson = json["recruitmentPeriod"];
        const start = periodJson["startDate"];
        const end = periodJson["endDate"];
        const type = periodJson["studyType"];
        const degree = periodJson["studyDegree"];
        const name = `${start} - ${end}, ${convertType(type)}, ${convertDegree(degree)}`
        setPeriodName(name);
        setStudyType(type);
        let details = {};
        details.startDate = start;
        details.endDate = end;
        setPeriodDetails(details);
      })
      .catch(e => console.log(e));
  }, [props.period]);

  useEffect(() => {
    if (Object.keys(errors).length === 0 && isSubmitting) {
      submit();
    } else {
      console.log(errors);
    }
  }, [errors]);

  useEffect(() => {
    fetch("/api/course-of-study")
      .then(res => res.json())
      .then(json => {
        setCourses(json["courseOfStudies"]);
      })
      .catch(e => console.log(e));
  }, []);

  useEffect(() => {
    if (props.recruitment === "") return;
    fetch(`/api/recruitment/${props.recruitment}/rooms`)
      .then(res => res.json()
      )
      .then(json => {
        const rooms = getRooms(json['recruitmentRooms']);
        setRooms(rooms);
      })
      .catch(e =>
        console.log(e));
  }, [props.recruitment]);

  return (
    <Dialog
      open={props.open}
      onClose={props.handleClose}
      aria-labelledby="form-dialog-title"
    >
      <DialogTitle>
        <Typography variant="h3">
          Nowy egzamin
          </Typography>
        <Typography variant="h5">
          Rekrutacja: {recruitmentName}
        </Typography>
        <Typography variant="h5">
          Cykl: {periodName}
        </Typography>
      </DialogTitle>
      <DialogContent dividers>
        <form
          autoComplete="off"
          noValidate
        >
          <Grid
            container
            spacing={1}
          >
            <Grid
              item
              xs={12}
            >
              <TextField
                fullWidth
                helperText="Kierunek"
                margin="dense"
                required
                select
                SelectProps={{ native: true }}
                variant="outlined"
                onChange={handleCourseChange}
              >
                {courses.filter(course => course.courseType == studyType).map(course => (
                  <option value={course.id}>{course.name}</option>
                ))}
              </TextField>
              <p className="error">{errors.course}</p>
            </Grid>
            <Grid
              item
              xs={12}
            >
              <TextField
                fullWidth
                margin="dense"
                helperText="Sala"
                required
                select
                SelectProps={{ native: true }}
                variant="outlined"
                onChange={handleRoomChange}
              >
                {rooms.map(room => (
                  <option value={room.id}>{`${room.room.name}, ${room.room.building}`}</option>
                ))}
              </TextField>
              <p className="error">{errors.room}</p>
            </Grid>
            <Grid
              item
              md={12}
              md={12}
            >
              <MuiPickersUtilsProvider utils={DateFnsUtils}>
                <KeyboardDatePicker
                  disableToolbar
                  variant="inline"
                  format="MM/dd/yyyy"
                  margin="normal"
                  label="Data"
                  value={selectedDate}
                  onChange={handleDateChange}
                  color="primary"
                  KeyboardButtonProps={{
                    'aria-label': 'change date',
                  }}
                />
              </MuiPickersUtilsProvider>
              <p className="error">{errors.date}</p>
            </Grid>
            <Grid
              item
              md={6}
              md={12}
            >
              <MuiPickersUtilsProvider utils={DateFnsUtils}>

                <KeyboardTimePicker
                  margin="normal"
                  label="Od godziny"
                  value={startTime}
                  onChange={handleStartTimeChange}
                  KeyboardButtonProps={{
                    'aria-label': 'change time',
                  }}
                />
              </MuiPickersUtilsProvider>
              <p className="error">{errors.startTime}</p>
            </Grid>
            <Grid
              item
              md={6}
              md={12}
            >
              <MuiPickersUtilsProvider utils={DateFnsUtils}>

                <KeyboardTimePicker
                  margin="normal"
                  label="Do godziny"
                  value={endTime}
                  onChange={handleEndTimeChange}
                  KeyboardButtonProps={{
                    'aria-label': 'change time',
                  }}
                />
              </MuiPickersUtilsProvider>
              <p className="error">{errors.endTime}</p>
            </Grid>
          </Grid>
        </form>
      </DialogContent>
      <DialogActions>
        <Button onClick={props.handleClose} color="secondary" variant="contained">
          ZAMKNIJ
          </Button>
        <Button onClick={handleSubmit} color="primary" variant="contained">
          DODAJ
          </Button>
      </DialogActions>
    </Dialog>
  );
}