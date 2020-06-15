import React, {useEffect, useState} from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogTitle from '@material-ui/core/DialogTitle';
import {Grid, Typography} from "@material-ui/core"
import MultiSelect from "react-multi-select-component";


export default function ReportDialog(props) {

    const [selectedPeriods, setSelectedPeriods] = useState([]);

    let periods = [];
    const fetchDataForDialog = (id) => {
        fetch("/api/recruitment-period/" + id)
            .then(res => res.json())
            .then(json => {
                console.log(json);
            })
            .catch(e => console.log(e));
    }

    return (
        <div>
            <Dialog
                open={props.open}
                onEnter={fetchDataForDialog(props.id)}
                onClose={props.handleClose}
                aria-labelledby="form-dialog-title"
            >
                <Grid
                    item
                    xs={6}
                    md={12}>
                </Grid>
                <h1>Select Fruits</h1>
                <DialogTitle>
                    <Typography variant="h3">
                        Szczegółowy raport
                    </Typography>
                </DialogTitle>

                <MultiSelect
                    options={periods}
                    value={selectedPeriods}
                    onChange={setSelectedPeriods}
                    labelledBy={"Select"}
                />

                <DialogActions>
                    <Button onClick={props.handleClose} color="secondary" variant="contained">
                        ZAMKNIJ
                    </Button>

                </DialogActions>
            </Dialog>
        </div>
    );
}