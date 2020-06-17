import React, { useState } from 'react';
import { makeStyles } from '@material-ui/styles';
import PropTypes from 'prop-types';
import { 
  Card, 
  CardContent,
  Grid, 
  Typography, 
  FormControl,
  Select,
  InputLabel,
  Chip,
  Input,
  MenuItem,
} from '@material-ui/core';

const useStyles = makeStyles(theme => ({
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

const CoursesSelection = props => {
  const { className, ...rest } = props;

  const classes = useStyles();

  const courses = props.courses;

  const [selectedCourses, setSelectedCourses] = useState(courses.slice(0));

  const handleSelectedCoursesChange = event => {
    const value = event.target.value
    setSelectedCourses(value)
    props.updateCourses(value)
  };

  return (
    <Card>
      <CardContent>
        <Typography variant="h4" gutterBottom>
          Kierunki
        </Typography>
        <Grid item xs={12}>
          <FormControl className={classes.formControl}>
            <InputLabel id="multiple_selected_courses">wybrane kierunki</InputLabel>
            <Select
              labelId="multiple_selected_courses"
              id="courses_selection_panel"
              multiple
              value={selectedCourses}
              onChange={handleSelectedCoursesChange}
              input={<Input id="courses_selection" />}
              renderValue={(selected) => (
                <div className={classes.chips}>
                  {selected.map((value) => (
                    <Chip key={value} label={value} className={classes.chip} />
                  ))}
                </div>
              )}
            >
              {courses.map((course) => (
                <MenuItem key={course} value={course}>
                  {course}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
      </CardContent>
    </Card>
  );
};

CoursesSelection.propTypes = {
  className: PropTypes.string
};

export default CoursesSelection;
