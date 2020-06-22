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

const RoomsSelection = props => {
  const { className, ...rest } = props;

  const classes = useStyles();

  const rooms = props.rooms;

  const [selectedRooms, setSelectedRooms] = React.useState(props.rooms.slice(0));

  const handleSelectedRoomsChange = event => {
    const value = event.target.value
    setSelectedRooms(value)
    props.updateRooms(value)
  };

  return (
    <Card>
      <CardContent>
        <Typography variant="h4" gutterBottom>
          Sale
        </Typography>
        <Grid item xs={12}>
          <FormControl className={classes.formControl}>
            <InputLabel id="multiple_selected_rooms">Wybrane sale</InputLabel>
            <Select
              labelId="multiple_selected_rooms"
              id="rooms_selection_panel"
              multiple
              value={selectedRooms}
              onChange={handleSelectedRoomsChange}
              input={<Input id="rooms_selection" />}
              renderValue={(selected) => (
                <div className={classes.chips}>
                  {selected.map((value) => (
                    <Chip key={value.id} label={value.text} className={classes.chip} />
                  ))}
                </div>
              )}
            >
              {rooms.map((room) => (
                <MenuItem key={room.id} value={room}>
                  {room.text}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
      </CardContent>
    </Card>
  );
};

RoomsSelection.propTypes = {
  className: PropTypes.string
};

export default RoomsSelection;
