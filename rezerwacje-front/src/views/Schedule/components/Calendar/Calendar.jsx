import React, { Component, useEffect } from 'react'
import { 
  Card, 
  CardContent, 
  CardActions, 
  Button 
} from '@material-ui/core';
import Timeline from 'react-timelines'
import { ROOMS } from './constants'
import { buildExamTimebar, buildRoomTrack } from './builders'

import './style.scss'

const MIN_ZOOM = 1300
const MAX_ZOOM = 1300

class Calendar extends Component {
  constructor(props) {
    super(props)

    const tracksById = this.getRoomsTracks(props.defaults.rooms)

    this.defaults = {
      start: this.prepareStartDate(new Date(Date.now() - (7 * 24 * 60 * 60 * 1000))),
      end: this.prepareEndDate(new Date(Date.now())),
    }

    this.state = {
      zoom: 1300,
      tracksById,
      tracks: Object.values(tracksById),
      start: this.prepareStartDate(props.defaults.from),
      end: this.prepareEndDate(props.defaults.to),
      timebar: buildExamTimebar(props.defaults.from, props.defaults.to),
      now: new Date(Date.now()),
    }
  }

  useEffectsWithParameters = (url, responseKey, setter, formatter) => {
    useEffect(() => {
      fetch(url)
        .then(res => res.json())
        .then(json => {
          console.log(json[responseKey]);
          if (json["message"] == undefined)
            setter(json[responseKey].reduce((accumulator, element, i) => {
              accumulator.push({
                id: i,
                text: formatter(element),
              })
              return accumulator
            }, []));
        })
        .catch(e => console.log(e));
    }, []);
  }

  setExamRelation(exam) {
    var relation = "unrelated"
    if (this.props.cycle && exam.recruitmentPeriod.id == this.props.cycle.id) {
      if (this.props.courses && this.props.courses.filter(x => x.id == exam.courseOfStudy.id).length > 0) {
        relation = "closely_related"
      }
      relation = "related"
    } 

    exam.relation = relation
  }

  getRoomsTracks = rooms => {
    return rooms.reduce((accumulator, element, i) => {
                  element.exams.forEach(x => this.setExamRelation(x))
                  const track = buildRoomTrack(i, element.text, element.exams)
                  accumulator[track.id] = track
                  return accumulator
                }, {})
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
    const { from, to, rooms, courses } = this.props.getFilterValues()
    this.state.start = this.prepareStartDate(from)
    this.state.end = this.prepareEndDate(to)
    this.state.timebar = buildExamTimebar(this.state.start, this.state.end)
    this.state.tracksById = this.getRoomsTracks(rooms)
    this.state.tracks = Object.values(this.state.tracksById)
    this.forceUpdate()
  }

  render() {
    const { zoom, tracks, start, end, timebar, now } = this.state

    return (
      <Card>
        <CardContent>
          <CardActions>
            <Button
              color="primary"
              variant="contained"
              onClick={this.applyFilters}
            >
              ZASTOSUJ FILTRY
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