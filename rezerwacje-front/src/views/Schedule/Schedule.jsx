import React, { useState, useEffect } from "react";
import { makeStyles } from "@material-ui/styles";
import { Grid } from "@material-ui/core";
import { connect } from "react-redux";
import {
  Calendar,
  RecruitmentSelection,
  CoursesSelection,
  RoomsSelection,
  TimeSelection,
} from "./components";
import { selectCurrentUser } from "../../redux/user/user.selectors";

const useStyles = makeStyles((theme) => ({
  root: {
    padding: theme.spacing(4),
  },
  formControl: {
    width: "100%",
  },
  chips: {
    display: "flex",
    flexWrap: "wrap",
  },
  chip: {
    margin: 2,
  },
}));

const Schedule = ({ user, ...rest }) => {
  const classes = useStyles();

  const fetchWithParameters = async (url, responseKey, setter, formatter) => {
    var tmp = [];
    await fetch(url)
      .then((res) => res.json())
      .then((json) => {
        console.log(json[responseKey]);
        if (json["message"] == undefined)
          tmp = json[responseKey].reduce((accumulator, element, i) => {
            accumulator.push(formatter(element));
            return accumulator;
          }, []);
        setter(tmp);
      })
      .catch((e) => console.log(e));
    return tmp;
  };

  const cycles = ["1", "2"];

  const [rooms, setRooms] = useState([]);

  const loadRooms = () => {
    let fun = async () => {

      const isRecruitmentSet = values.recruitment != undefined
      const roomFormatter = x => ({ id: x.id, text: `${x.building} ${x.name}`, exams: [] })

      const tmpRooms = await fetchWithParameters(
        isRecruitmentSet ? `/api/recruitment/${values.recruitment.id}/rooms` : "/api/rooms",
        isRecruitmentSet ? "recruitmentRooms" : "rooms",
        setRooms,
        isRecruitmentSet ? x => roomFormatter(x.room) : roomFormatter
      );

      tmpRooms.map((x) =>
        fetchWithParameters(
          "/api/exam-terms/room/" + x.id,
          "examTerms",
          (e) => (x.exams = e),
          (r) => r
        )
      );
    };
    fun();
  }

  useEffect(() => {
    loadRooms()
  }, []);

  const [recruitments, setRecruitments] = useState([]);

  useEffect(() => {
    let fun = async () => {
      const tmpRecruitments = await fetchWithParameters(
        "/api/recruitment/all",
        "recruitments",
        setRecruitments,
        (x) => ({ id: x.id, text: `${x.name}`, cycles: [] })
      );

      tmpRecruitments.map((x) =>
        fetchWithParameters(
          "/api/recruitment-period/recruitment/" + x.id,
          "recruitmentPeriods",
          (r) => (x.cycles = r),
          (r) => r
        )
      );
    };
    fun();
  }, []);

  const [courses, setCourses] = useState([]);

  const [userCoursesIds, setUserCoursesIds] = useState([]);

  const [userCoursesOfStudies, setUserCoursesOfStudies] = useState([]);

  useEffect(() => {
    let fun = async () => {
      const tmpCourses = await fetchWithParameters(
        "/api/course-of-study",
        "courseOfStudies",
        setCourses,
        (x) => ({
          id: x.id,
          text: `${x.faculty.name} ${
            x.name
          } (${x.courseType.toLowerCase().replace("_", " ")})`,
        })
      );

      const tmpCoursesIds = await fetchWithParameters(
        "/api/course-of-study/courses?userId=" + user.id,
        "coursesOfStudiesIdsForUser",
        setUserCoursesIds,
        (x) => x
      );

      setUserCoursesOfStudies(
        tmpCourses.reduce((accumulator, element, i) => {
          if (tmpCoursesIds.includes(element.id)) accumulator.push(element);
          return accumulator;
        }, [])
      );
    };
    fun();
  }, []);

  const [values, setValues] = useState({
    recruitment: recruitments.length > 0 ? recruitments[0] : undefined,
    cycle: cycles.length > 0 ? cycles[0] : undefined,
    dateFrom: new Date(Date.now() - 7 * 24 * 60 * 60 * 1000),
    dateTo: new Date(Date.now()),
    courses: courses.slice(0),
    rooms: rooms.slice(0),
    recruitments: recruitments.slice(0),
    cycles: cycles.slice(0),
  });

  const handleChange = (event) => {
    const { name, value } = event.target;
    setValues({
      ...values,
      [name]: value,
    });
  };

  const updateRecruitment = (value) => {
    handleChange({ target: { name: "recruitment", value: value } });
    loadRooms()
  }

  const updateCycle = (value) =>
    handleChange({ target: { name: "cycle", value: value } });

  const updateCourses = (value) =>
    handleChange({ target: { name: "courses", value: value } });

  const updateRooms = (value) =>
    handleChange({ target: { name: "rooms", value: value } });

  const updateDateFrom = (value) =>
    handleChange({ target: { name: "dateFrom", value: value } });

  const updateDateTo = (value) =>
    handleChange({ target: { name: "dateTo", value: value } });

  const getFilterValues = () => {
    return {
      from: values.dateFrom,
      to: values.dateTo,
      rooms: values.rooms,
      courses: values.courses,
      recruitment: values.recruitment,
      cycle: values.cycle,
    };
  };

  return (
    <div className={classes.root}>
      <Grid container spacing={4}>
        <Grid item xs={3}>
          <RecruitmentSelection
            updateRecruitment={updateRecruitment}
            updateCycle={updateCycle}
            recruitments={recruitments}
          />
        </Grid>
        <Grid item xs={3}>
          <CoursesSelection updateCourses={updateCourses} courses={courses} />
        </Grid>
        <Grid item xs={3}>
          <RoomsSelection updateRooms={updateRooms} rooms={rooms} />
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

const mapStateToProps = (state) => ({
  user: selectCurrentUser(state),
});

export default connect(mapStateToProps)(Schedule);
