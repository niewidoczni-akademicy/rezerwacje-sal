/* eslint-disable import/no-unresolved */
import React, { Component } from 'react'
import { 
  Card, 
  CardContent, 
  Grid, 
  Typography, 
  Avatar, 
  CardActions, 
  Button 
} from '@material-ui/core';

import Timeline from 'react-timelines'
import { START_DATE, END_DATE, ROOMS } from './constants'
import { buildExamTimebar, buildRoomTrack } from './builders'

import './style.scss'
import './Calendar.scss'

const now = new Date('2021-01-01')

const timebar = buildExamTimebar(new Date(START_DATE), new Date(END_DATE))

// eslint-disable-next-line no-alert
const clickElement = element => alert(`Clicked element\n${JSON.stringify(element, null, 2)}`)

const MIN_ZOOM = 1300
const MAX_ZOOM = 1300

class Calendar extends Component {
  constructor(props) {
    super(props)

    const tracksById = ROOMS.reduce((accumulator, element, i) => {
      const track = buildRoomTrack(i, element.name, element.exams)
      accumulator[track.id] = track
      return accumulator
    }, {})

    this.state = {
      open: false,
      zoom: 1300,
      // eslint-disable-next-line react/no-unused-state
      tracksById,
      tracks: Object.values(tracksById),
      start: this.prepareStartDate(START_DATE),
      end: this.prepareEndDate(END_DATE),
      // timebar: buildExamTimebar(start, end),
      // startDateRaw: this.prepareRawDate(props),
    }
  }

  prepareRawDate = raw => {
    raw.map(x => alert(x))
    return raw
  }

  handleZoomIn = () => {
    this.setState(({ zoom }) => ({ zoom: Math.min(zoom + 200, MAX_ZOOM) }))
  }

  handleZoomOut = () => {
    this.setState(({ zoom }) => ({ zoom: Math.max(zoom - 200, MIN_ZOOM) }))
  }

  prepareStartDate = date => {
    const start = new Date(date)
    start.setHours(0,0,0,0)
    return start
  }

  prepareEndDate = date => {
    const end = new Date(date)
    end.setDate(end.getDate() + 1)
    end.setHours(0,0,0,0)
    return end
  }

  applyFilters = () => {
    console.log(this.props)
    console.log(this.props.getFilterValues)
    this.props.getFilterValues()
  }

  render() {
    const { open, zoom, tracks, start, end } = this.state

    return (
      <Card>
        <CardContent>
          <CardActions>
            <Button
              color="primary"
              variant="contained"
              onClick={this.applyFilters}
            >
              APPLY FILTERS
            </Button>
          </CardActions>
        </CardContent>
        <Timeline
          scale={{
            start,
            end,
            zoom,
            zoomMin: MIN_ZOOM,
            zoomMax: MAX_ZOOM,
          }}
          isOpen={open}
          zoomIn={this.handleZoomIn}
          zoomOut={this.handleZoomOut}
          clickElement={clickElement}
          clickTrackButton={track => {
            alert(JSON.stringify(track))
          }}
          timebar={timebar}
          tracks={tracks}
          now={now}
          enableSticky
          scrollToNow
        />
      </Card>
    )
  }
}

export default Calendar