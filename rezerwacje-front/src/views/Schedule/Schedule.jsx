import React, { useState, useEffect } from 'react';
import { makeStyles } from '@material-ui/styles';
import { Grid } from '@material-ui/core';
import {
  Calendar,
  RecruitmentSelection,
  CoursesSelection,
  RoomsSelection,
  TimeSelection,
} from './components';

const useStyles = makeStyles(theme => ({
  root: {
    padding: theme.spacing(4)
  },
  formControl: {
    width: '100%',
  },
  chips: {
    display: 'flex',
    flexWrap: 'wrap',
  },
  chip: {
    margin: 2,
  },
}));

const Schedule = () => {
  const classes = useStyles();

  const useEffectsWithParameters = (url, responseKey, setter, formatter) => {
    useEffect(() => {
      fetch(url)
        .then(res => res.json())
        .then(json => {
          console.log(json[responseKey]);
          if (json["message"] == undefined)
            setter(json[responseKey].reduce((accumulator, element, i) => {
              accumulator.push(formatter(element))
              return accumulator
            }, []));
        })
        .catch(e => console.log(e));
    }, []);
  }

  const cycles = ['1', '2'];

  const [rooms, setRooms] = useState([]);

  useEffectsWithParameters(
    '/api/rooms', 
    'rooms', 
    setRooms, 
    x => `${x.building} ${x.name}`,
  );

  console.log(rooms)

  const [recruitments, setRecruitments] = useState([]);

  useEffectsWithParameters(
    '/api/recruitment/all', 
    'recruitments', 
    setRecruitments, 
    x => `${x.name}`,
  );

  const [courses, setCourses] = useState([]);

  useEffectsWithParameters(
    '/api/course-of-study', 
    'courseOfStudies', 
    setCourses, 
    x => `${x.faculty_id} ${x.name} (${x.course_type})`,
  );

  const [values, setValues] = useState({
    recruitment: recruitments.length > 0 ? recruitments[0] : '',
    cycle: cycles.length > 0 ? cycles[0] : '',
    dateFrom: new Date(Date.now() - (7 * 24 * 60 * 60 * 1000)),
    dateTo: new Date(Date.now()),
    courses: courses.slice(0),
    rooms: rooms.slice(0),
    recruitments: recruitments.slice(0),
    cycles: cycles.slice(0),
  });

  const handleChange = event => {
    const { name, value } = event.target;
    setValues({
        ...values,
        [name]: value
    });
  };

  const updateRecruitment = value => {
    setValues({
      ...values,
      ['recruitment']: value
    });
  }

  const updateCycle = value => handleChange({target: {name: 'cycle', value: value }})

  const updateCourses = value => handleChange({target: {name: 'courses', value: value }})

  const updateRooms = value => handleChange({target: {name: 'rooms', value: value }})

  const updateDateFrom = value => handleChange({target: {name: 'dateFrom', value: value }})

  const updateDateTo = value => handleChange({target: {name: 'dateTo', value: value }})

  const getFilterValues = () => {
    return {
      from: values.dateFrom, 
      to: values.dateTo,
      rooms: values.rooms,
      courses: values.courses,
    }
  }

  return (
    <div className={classes.root}>
      <Grid
        container
        spacing={4}
      >
        <Grid item xs={3}>
          <RecruitmentSelection 
            updateRecruitment={updateRecruitment}   
            updateCycle={updateCycle}
            recruitments={recruitments}
            cycles={cycles}
          />
        </Grid>
        <Grid item xs={3}>
          <CoursesSelection 
            updateCourses={updateCourses} 
            courses={courses}
          />
        </Grid>
        <Grid item xs={3}>
          <RoomsSelection 
            updateRooms={updateRooms} 
            rooms={rooms}
          />
        </Grid>
        <Grid item xs={3}>
          <TimeSelection 
            updateStart={updateDateFrom} 
            updateEnd={updateDateTo} 
            start={values.dateFrom}
            end={values.dateTo}
          />
        </Grid>
        <Grid item xs={12}>
          <Calendar 
            getFilterValues={getFilterValues}
            defaults={getFilterValues()}
          />
        </Grid>
      </Grid>
    </div>
  );
};

export default Schedule;
