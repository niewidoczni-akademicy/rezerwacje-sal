import React from "react";
import { makeStyles } from "@material-ui/styles";
import { useState } from "react"
import {
    TextField,
    Grid,
    Typography,
    Button,
    Card,
    CardContent,
    CardActions,
    CardHeader,
    Divider
}
    from "@material-ui/core"
import ExamFormDialog from "./ExamFormDialog";


const ExamsContent = () => {

    const [values, setValues] = useState({
        space: '',
        period: ''
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

    const recruitmentPeriods = ['1', '2'];

    const handleClose = () => setModalShow(false)

    return (
        <React.Fragment>
            <Card>
                <CardHeader title="Egzaminy" />
                <Divider />
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
                                name="period"
                                onChange={handleChange}
                                required
                                select
                                SelectProps={{ native: true }}
                                value={values.period}
                                variant="outlined"
                            >
                                {recruitmentPeriods.map(period => (
                                    <option key={period} value={period}>{period}</option>
                                ))}
                            </TextField>
                        </Grid>
                    </Grid>
                </CardContent>
                <Divider />
                <CardActions>
                    <Button
                        color="primary"
                        variant="contained"
                        onClick={() =>
                            setModalShow(true)}
                    >
                        DODAJ EGZAMIN
            </Button>
                </CardActions>
            </Card>
            <ExamFormDialog open={modalShow} handleClose={handleClose} space={values.space} period={values.period} />
        </React.Fragment>);
};

export default ExamsContent;