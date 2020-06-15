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
  TextField
}
  from "@material-ui/core"
import { useEffect, useState } from "react";
import './SelectRecruitmentDialog.scss'

export default function SelectRecruitmentDialog(props) {
  const [recruitment, setRecruitment] = useState(-1);
  const [recruitmentList, setRecruitmentList] = useState([]);

  const handleSubmit = () => {
    console.log("submit");
  };

  const handleRecruitmentChange = event => {
    console.log(event.target.value);
    setRecruitment(event.target.value);
  };

  const filterRecruitmentList = recruitmentList => {
    if (props.recruitment != undefined) {
      return recruitmentList.filter(recruitment => recruitment.id != props.recruitment);
    }
    return recruitmentList;
  };

  useEffect(() => {
    const api = "/api/recruitment/all";
    fetch(api)
      .then(res => res.json())
      .then(json => {
        const recruitmentList = filterRecruitmentList(json["recruitments"]);
        setRecruitmentList(recruitmentList);
        if (recruitmentList.length > 0)
          setRecruitment(recruitmentList[0].id);
      })
      .catch(e => console.log(api, e));
  }, [props.recruitment]);

  return (
    <Dialog
      open={props.open}
      onClose={props.handleClose}
      aria-labelledby="form-dialog-title"
    >
      <DialogTitle>
        <Typography variant="h3">
          Wybór sal z istniejącej rekrutacji
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