import React, { useState } from 'react';
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

  const recruitments = props.recruitments;

  const [selectedRecruitment, setRecruitment] = useState(
    recruitments.length > 0 ? recruitments[0] : '');

  const handleSelectedRecruitmentChange = event => {
    const value = event.target.value
    setRecruitment(value)
    props.updateRecruitment(value)
  };

  const cycles = props.cycles;

  const [selectedCycle, setCycle] = useState(
    cycles.length > 0 ? cycles[0] : '');

  const handleSelectedCycleChange = event => {
    const value = event.target.value
    setCycle(value)
    props.updateCycle(value)
  };

  return (
    <Card>
      <CardContent>
        <Typography variant="h4" gutterBottom>
          Rekrutacja
        </Typography>
        <Grid item xs={12}>
          <TextField
            fullWidth
            helperText="rekrutacja"
            margin="dense"
            name="recruitment"
            onChange={handleSelectedRecruitmentChange}
            required
            select
            SelectProps={{ native: true }}
            value={selectedRecruitment}
            variant="outlined"
          >
            {recruitments.map(recruitment => (
              <option key={recruitment} value={recruitment}>{recruitment}</option>
            ))}
          </TextField>
        </Grid>
        <Grid item xs={12}>
          <TextField
            fullWidth
            helperText="cykl"
            margin="dense"
            name="cycle"
            onChange={handleSelectedCycleChange}
            required
            select
            SelectProps={{ native: true }}
            value={selectedCycle}
            variant="outlined"
          >
            {cycles.map(cycle => (
              <option key={cycle} value={cycle}>{cycle}</option>
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
