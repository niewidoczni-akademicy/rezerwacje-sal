import React from "react";
import { makeStyles } from "@material-ui/styles";
import { Grid } from "@material-ui/core";
import { useState } from "react";
import { ExamsForm } from "./components";

const ExamsContent = () => {
  const classes = useStyles();
  const [values, setValues] = useState({
    space: "",
    period: ""
  });

  const recruitmentSpaces = ["lato 2021", "zima 2021"];

  const recruitmentPeriods = [1, 2];

  return (
    <Grid>
      <Grid item xs={6}>
          Rekrutacja
      </Grid>
      <Grid item xs={6}>
        <TextField
          fullWidth
          margin="dense"
          name="state"
          onChange={handleChange}
          required
          select
          SelectProps={{ native: true }}
          value={values.space}
          variant="outlined"
        >
          {recruitmentSpaces.map(space => (
            <option key={space} value={space}></option>
          ))}
        </TextField>
      </Grid>
      <Grid item xs={6}>
      <TextField
          fullWidth
          margin="dense"
          name="state"
          onChange={handleChange}
          required
          select
          SelectProps={{ native: true }}
          value={values.space}
          variant="outlined"
        >
          {recruitmentSpaces.map(space => (
            <option key={space} value={space}></option>
          ))}
        </TextField> 
      </Grid>
    </Grid>
  );
};

export default ExamsContent;
