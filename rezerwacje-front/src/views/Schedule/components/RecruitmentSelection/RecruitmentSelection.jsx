import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { 
  Card, 
  CardContent,
  Grid, 
  Typography, 
  TextField,
} from '@material-ui/core';

const RecruitmentSelection = props => {
  const { className, ...rest } = props;

  const [recruitments, setRecruitments] = useState(props.recruitments);

  const [selectedRecruitment, setRecruitment] = useState(undefined)
  console.log(selectedRecruitment)

  useEffect(() => {
    if (recruitments != props.recruitments) {
      setRecruitments(props.recruitments);
      setRecruitment(props.recruitments[0])
      setCycle(props.recruitments[0] ? props.recruitments[0].cycles[0] : undefined)
    }
  })

  const handleSelectedRecruitmentChange = event => {
    const value = event.target.value
    const recruitment = recruitments.find(x => x.id == value)
    console.log(recruitment)
    setRecruitment(recruitment)
    props.updateRecruitment(recruitment)
  };

  const [selectedCycle, setCycle] = useState(undefined)

  const handleSelectedCycleChange = event => {
    const value = event.target.value
    const cycle = selectedRecruitment.cycles.find(x => x.id == value)
    setCycle(cycle)
    props.updateCycle(cycle)
  };

  const studiesTypes = {
    FULL_TIME : "studia dzienne",
    PART_TIME : "studia zaoczne",
  }

  const studiesLevels = {
    FIRST_DEGREE : "pierwszy stopień",
    SECOND_DEGREE : "drugi stopień",
  }

  return (
    <Card>
      <CardContent>
        <Typography variant="h4" gutterBottom>
          Rekrutacja
        </Typography>
        <Grid item xs={12}>
          <TextField
            fullWidth
            helperText="Rekrutacja"
            margin="dense"
            name="recruitment"
            onChange={handleSelectedRecruitmentChange}
            required
            select
            SelectProps={{ native: true }}
            variant="outlined"
          >
            {recruitments.map(recruitment => (
              <option key={recruitment.id} value={recruitment.id}>
                {recruitment.text}
              </option>
            ))}
          </TextField>
        </Grid>
        <Grid item xs={12}>
          <TextField
            fullWidth
            helperText="Cykl rekrutacyjny"
            margin="dense"
            name="cycle"
            onChange={handleSelectedCycleChange}
            required
            select
            SelectProps={{ native: true }}
            variant="outlined"
          >
            {(selectedRecruitment ? selectedRecruitment.cycles : []).map(cycle => (
              <option key={cycle.id} value={cycle.id}>
                {`${cycle.id} (${studiesTypes[cycle.studyType]}, ${studiesLevels[cycle.studyDegree]})`}
              </option>
            ))}
          </TextField>
        </Grid>
      </CardContent>
    </Card>
  );
};

RecruitmentSelection.propTypes = {
  className: PropTypes.string
};

export default RecruitmentSelection;
