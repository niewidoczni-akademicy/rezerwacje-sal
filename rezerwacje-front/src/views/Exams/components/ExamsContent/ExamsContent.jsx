import React from "react";
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
import { convertDegree, convertType } from "./conversion";


const ExamsContent = () => {

    const [recruitments, setRecruitments] = useState([])
    const [periods, setPeriods] = useState([])

    const [values, setValues] = useState({
        recruitment: -1,
        period: -1
    });

    const [modalShow, setModalShow] = useState(false);

    const handleChange = event => {
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
                const recruitmentList = json["recruitments"];
                setRecruitments(recruitmentList);
                if (recruitmentList.length > 0)
                    setValues({
                        ...values,
                        ['recruitment']: recruitmentList[0].id
                    });
                else
                    setValues({
                        ...values,
                        ['recruitment']: -1
                    })
            })
            .catch(e => console.log(e));
    }, []);

    useEffect(() => {
        if (values.recruitment != -1)
            fetch(`/api/recruitment-period/recruitment/${values.recruitment}`)
                .then(res => res.json())
                .then(json => {
                    const periodsList = json["recruitmentPeriods"];
                    setPeriods(periodsList);
                    if (periodsList.length > 0)
                        setValues({
                            ...values,
                            ['period']: periodsList[0].id
                        })
                    else setValues({
                        ...values,
                        ['period']: -1
                    })
                })
                .catch(e => console.log(e));
    }, [values.recruitment]);

    const handleClose = () => setModalShow(false);

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
                                value={values.recruitment}
                                variant="outlined"
                            >
                                {recruitments.map(recruitment => (
                                    <option key={recruitment.id} value={recruitment.id}>{recruitment.name}</option>
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
                                required
                                SelectProps={{ native: true }}
                                value={values.period}
                                variant="outlined"
                            >
                                {periods.map(period => (
                                    <option key={period.id} value={period.id}>{`${period.startDate} - ${period.endDate}, ${convertType(period.studyType)}, ${convertDegree(period.studyDegree)}`}</option>
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
                        disabled={values.recruitment === -1 || values.period === -1}
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