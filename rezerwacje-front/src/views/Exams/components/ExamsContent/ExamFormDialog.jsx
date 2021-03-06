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
  from "@material-ui/core";
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
  const [periodDetails, setPeriodDetails] = useState({});
  const [seats, setSeats] = useState(0);
  const [assignedCourses, setAssignedCourses] = useState([]);

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
      recruitmentRoomId: roomId,
      seats: parseInt(seats)
    });

    fetch("/api/exam-terms", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: body
    }).then(
      function (res) {
        if (res.ok) {
          alert("Egzamin został dodany do bazy.");
          props.handleClose();
        } else if (res.status === 404) {
          res.json().then(function (object) {
            if (object.message.includes("exams already found in provided term"))
              alert("Wybrany termin jest już zajęty");
          });
        }
      },
      function (e) {
        alert("Wystąpił błąd.");
      }
    );
  };

  const days = ["SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "SATURDAY"];

  const handleSubmit = async event => {
    event.preventDefault();
    let room = -1;
    if (roomId != -1)
      room = getRoom();
    setErrors(validateExamForm(courseId, roomId, getDate(selectedDate), getTime(startTime), getTime(endTime), periodDetails, room, getDay(selectedDate), parseInt(seats)));
    setIsSubmitting(true);
  };

  const handleCourseChange = event => setCourseId(event.target.value);

  const handleRoomChange = event => setRoomId(event.target.value);

  const handleStartTimeChange = (selectedTime) => setStartTime(selectedTime);

  const handleEndTimeChange = (selectedTime) => setEndTime(selectedTime);

  const handleSeatsChange = event => setSeats(event.target.value);

  const createRoomData = room => {
    return {
      "id": room.id,
      "room": room.room,
    }
  };

  const getRooms = rooms => rooms.map(room => createRoomData(room));

  const getTime = time => time.toLocaleString('sv', { timeZoneName: 'short' }).split(" ")[1].slice(0, 6) + "00";

  const getDate = date => date.toISOString().split("T")[0].slice(0, 19);

  const getRoom = () => rooms.filter(room => room.id == roomId)[0];

  const getDay = date => days[date.getDay()];

  useEffect(() => {
    if (Object.keys(errors).length === 0 && isSubmitting) {
      submit();
    }
  }, [errors]);

  useEffect(() => {
    if (props.recruitment === -1) return;
    fetch(`/api/recruitment/${props.recruitment}`)
      .then(res => res.json())
      .then(json => {
        setRecruitmentName(json["recruitment"]["name"]);
      })
      .catch(e => console.log(e));
  }, [props.recruitment]);

  const checkCourseAccess = id => {
    if (assignedCourses.find(course => course === id) != undefined) return true;
    else return false;
  };

  const filterCourses = () => {
    const coursesList = courses.filter((course) => {
      const cType = 
        course.courseType === 'FULL_TIME' ? course.courseType : 'PART_TIME';
      return cType === studyType;
    });
    if (props.user.role === "STANDARD")
      return coursesList.filter(course => checkCourseAccess(course.id))
    return coursesList;
  }

  useEffect(() => {
    if (props.period === -1) return;
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
    if (props.user.role != "STANDARD") return;
    var url = `/api/course-of-study/courses?userId=${props.user.id}`;
    fetch(url)
      .then(res => res.json())
      .then(json => {
        const coursesList = json["coursesOfStudiesIdsForUser"];
        setAssignedCourses(coursesList);
      })
      .catch(e => console.log(e));
  }, [courses]);

  useEffect(() => {
    fetch("/api/course-of-study")
      .then(res => res.json())
      .then(json => {
        const coursesList = json["courseOfStudies"];
        setCourses(coursesList);
        if (coursesList.length > 0)
          setCourseId(coursesList[0].id);

      })
      .catch(e => console.log(e));
  }, [props.user]);



  useEffect(() => {
    if (props.recruitment === -1) return;
    fetch(`/api/recruitment/${props.recruitment}/rooms`)
      .then(res => res.json()
      )
      .then(json => {
        const rooms = getRooms(json['recruitmentRooms']);
        setRooms(rooms);
        if (rooms.length > 0)
          setRoomId(rooms[0].id);
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
                {
                  filterCourses(courses).map(course => (
                    <option key={course.id} value={course.id}>{course.name}</option>
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
                  <option key={room.id} value={room.id}>{`${room.room.name}, ${room.room.building}`}</option>
                ))}
              </TextField>
              <p className="error">{errors.room}</p>
            </Grid>
            <Grid
              item
              xs={12}
            >
              <TextField
                id="seats"
                label="Liczba miejsc"
                margin="dense"
                type="number"
                value={seats}
                onChange={handleSeatsChange}
              />
              <p className="error">{errors.seats}</p>
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