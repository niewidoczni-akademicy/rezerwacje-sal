import React from "react";
import { makeStyles } from "@material-ui/styles";
import { useState, useEffect } from "react"
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

    const [recruitments, setRecruitments] = useState([])
    const [periods, setPeriods] = useState([])

    const [values, setValues] = useState({
        recruitment: '',
        period: ''
    });

    const [modalShow, setModalShow] = useState(false);

    const handleChange = event => {
        console.log(event.target.name, event.target.value)
        const { name, value } = event.target;
        setValues({
            ...values,
            [name]: value
        });
    };

    useEffect(() => {
        fetch("/api/recruitment/all")
            .then(res => res.json())
            .then(json => {
                console.log(json);
                setRecruitments(json["recruitments"]);
                console.log(recruitments)
            })
            .catch(e => console.log(e));
    }, []);

    useEffect(() => {
        if (values.recruitment.length > 0)
            fetch(`/api/recruitment-period/recruitment/${values.recruitment}`)
                .then(res => res.json())
                .then(json => {
                    console.log(json);
                    setPeriods(json["recruitmentPeriods"]);
                })
                .catch(e => console.log(e));
    }, [values.recruitment]);

    const handleClose = () => setModalShow(false)

    const convertType = type => {
        if (type == "FULL_TYPE")
            return "STACJONARNE"
        else
            return "ZAOCZNE"
    }

    const convertDegree = degree => {
        if (degree == "FIRST_DEGREE")
            return "I STOPIEŃ"
        else
            return "II STOPIEŃ"
    }

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
                                name="recruitment"
                                onChange={handleChange}
                                required
                                select
                                SelectProps={{ native: true }}
                                variant="outlined"
                            >
                                {recruitments.map(recruitment => (
                                    <option value={recruitment.id}>{recruitment.name}</option>
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
                                select
                                SelectProps={{ native: true }}
                                value={values.period}
                                variant="outlined"
                            >
                                {periods.map(period => (
                                    <option value={period.id}>{`${period.startDate} - ${period.endDate}  ${convertType(period.studyType)}  ${convertDegree(period.studyDegree)}`}</option>
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
                        disabled={values.recruitment == '' || values.period == ''}
                        onClick={() =>
                            setModalShow(true)}
                    >
                        DODAJ EGZAMIN
            </Button>
                </CardActions>
            </Card>
            <ExamFormDialog open={modalShow} handleClose={handleClose} recruitment={values.recruitment} period={values.period} />
        </React.Fragment>);
};

export default ExamsContent;