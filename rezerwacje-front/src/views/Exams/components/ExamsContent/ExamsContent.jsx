import React from "react";
import { makeStyles } from "@material-ui/styles";
import { useState } from "react"
import {
    TextField,
    Grid,
    Typography,
    Button
}
from "@material-ui/core"
import ExamFormDialog from "./ExamFormDialog";


const ExamsContent = () => {

    const [values, setValues] = useState({
        space: '',
        period: 0
    });

    const [modalShow, setModalShow] = useState(false);

    const handleChange = event => {
        const { name, value } = event.target;
        setValues({
            ...values,
            [name]: value
        });
    };

    const recruitmentSpaces = ["lato 2020", "zima 2021"];

    const recruitmentPeriods = [1, 2];

    const handleClose = () => setModalShow(false)

    return (
        <React.Fragment>
        <Grid>
            <Grid item>
                <Typography variant="h2" gutterBottom>
                       Egzaminy
            </Typography>
            </Grid>
            <br/>
            <Grid item sm={6} xs={12}>
                <Typography variant="h4" gutterBottom>
                    Rekrutacja
            </Typography>
            </Grid>
            <Grid item sm={6} xs={12}>
                <TextField
                    fullWidth
                    margin="dense"
                    name="space"
                    onChange={handleChange}
                    required
                    select
                    SelectProps={{ native: true }}
                    value={values.space}
                    variant="outlined"
                >
                    {recruitmentSpaces.map(space => (
                        <option key={space} value={space}>{space}</option>
                    ))}
                </TextField>
            </Grid>
            <Grid>
                <Typography variant="h4" gutterBottom>
                    Cykl
            </Typography>
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
                    value={values.per}
                    variant="outlined"
                >
                    {recruitmentPeriods.map(period => (
                        <option key={period} value={period}>{period}</option>
                    ))}
                </TextField>
            </Grid>
            <Grid>
            <Button
                color="primary"
                variant="contained"
                onClick={() => 
                setModalShow(true)}
              >
                DODAJ EGZAMIN
            </Button>
            </Grid>
            </Grid>
            <ExamFormDialog open={modalShow} handleClose={handleClose} space={values.space} period={values.period}/>
           </React.Fragment>);
};

export default ExamsContent;