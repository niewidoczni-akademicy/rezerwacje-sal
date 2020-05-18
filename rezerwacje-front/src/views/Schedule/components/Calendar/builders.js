import { MONTH_NAMES, WEEKDAY_NAMES } from './constants'
import { 
  hexToRgb, 
  colourIsLight, 
  mapRelationToColor, 
  dateMinutesWithLeadingZeros 
} from './Utils'

export const buildExamTimebar = (startDate, endDate) => [
  {
    id: 'days',
    title: 'Days',
    cells: buildDayCells(startDate, endDate),
    style: {'text-align': 'left'},
  },
  {
    id: 'hours',
    title: 'Hours',
    cells: buildHourCells(startDate, endDate),
    useAsGrid: true,
    style: {},
  },
]

export const updateExamTimebar = (timebar, startDate, endDate) => {
  const newDayCells = buildDayCells(startDate, endDate)
  const newHourCells = buildHourCells(startDate, endDate)
  replaceArrayElements(timebar[0].cells, newDayCells)
  replaceArrayElements(timebar[1].cells, newHourCells)
}

const replaceArrayElements = (array, newElements) => {
  for (var x in array) {
    array.pop()
  }
  for (var x in newElements) {
    array.push(x)
  }
}

export const buildDayCells = (startDate, endDate) => {
  const cells = []
  const endDayDate = new Date(endDate.getTime())
  endDayDate.setDate(endDayDate.getDate() + 1)
  for (let date = new Date(startDate.getTime()); date <= endDayDate; date.setDate(date.getDate() + 1)) {
    const day = date.getDate()
    const month = date.getMonth()
    const year = date.getFullYear()
    const start = new Date(date.getTime())
    start.setHours(0,0,0,0)
    const end = new Date(start.getTime())
    end.setDate(start.getDate() + 1)
    cells.push({
      id: `${year}-${month + 1}-${day}`,
      title: `${MONTH_NAMES[month]} ${day}`,
      start,
      end,
    })
  }
  return cells
}

export const buildHourCells = (startDate, endDate) => {
  const cells = []
  const i = 0
  const date = new Date(startDate.getTime())
  date.setHours(0,0,0,0)
  const endHourDate = new Date(endDate.getTime())
  endHourDate.setDate(endDate.getDate() + 1)
  endHourDate.setHours(0,0,0,0)
  for (; date < endHourDate; date.setHours(date.getHours() + 1)) {
    const hour = date.getHours()
    const end = new Date(date.getTime())
    end.setHours(date.getHours() + 1)
    cells.push({
      id: `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}T${hour}`,
      title: `${hour}:00`,
      start: new Date(date.getTime()),
      end,
    })
  }
  return cells
}

export const buildRoomTrack = (id, name, exams) => {
  const trackId = `room-${id}`
  return {
    id: trackId,
    title: name,
    elements: buildRoomElements(trackId, exams),
  }
}

export const buildRoomElements = (trackId, exams) => {
  const elements = []
  for (let i = 0; i < exams.length; i++) {
    const exam = exams[i]
    elements.push(
      buildExamElement({
        trackId,
        start: new Date(exam.start),
        end: new Date(exam.end),
        examName: exam.name,
        backgroundColor: mapRelationToColor(exam.relation),
        id: i,
      })
    )
  }
  return elements
}

export const buildExamElement = ({ trackId, start, end, examName, backgroundColor, id }) => {
  return {
    id: `${trackId}-exam-${id}`,
    title: examName,
    start,
    end,
    style: {
      backgroundColor: `#${backgroundColor}`,
      color: colourIsLight(...hexToRgb(backgroundColor)) ? '#000000' : '#ffffff',
      borderRadius: '4px',
      boxShadow: '1px 1px 0px rgba(0, 0, 0, 0.25)',
      textTransform: 'capitalize',
    },
    tooltip: `<div><strong>${examName}</strong></div>
                <div>
                  ${MONTH_NAMES[start.getMonth()]} ${start.getDate()}, ${WEEKDAY_NAMES[start.getDay()]}
                </div>
                <div>
                  ${start.getHours()}:${dateMinutesWithLeadingZeros(start)} - ${end.getHours()}:${dateMinutesWithLeadingZeros(end)}
                </div>
              </div>`.split('\n').join('')
    }
}