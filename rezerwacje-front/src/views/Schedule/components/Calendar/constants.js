export const START_YEAR = 2020
export const NUM_OF_YEARS = 3
export const MONTHS_PER_YEAR = 12
export const QUARTERS_PER_YEAR = 4
export const MONTHS_PER_QUARTER = 3
export const NUM_OF_MONTHS = NUM_OF_YEARS * MONTHS_PER_YEAR
export const MAX_TRACK_START_GAP = 10
export const MAX_ELEMENT_GAP = 5
export const MAX_MONTH_SPAN = 8
export const MIN_MONTH_SPAN = 2
export const NUM_OF_TRACKS = 5
export const MAX_NUM_OF_SUBTRACKS = 2


export const START_DATE = '2020-04-28'
export const END_DATE = '2020-05-03'
export const WEEKDAY_NAMES = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']
export const MONTH_NAMES = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
export const RELATION_COLOR = {
  OWNER: 'D81E5B',
  RELATED: 'EB5E55',
  UNRELATED: '3A3335',
}
export const ROOMS = [
  {name: 'D17 2.41', exams: [
    {name: 'Fizyka 1', start: '2020-04-29T08:00', end: '2020-04-29T10:00', relation: 'owner'},
    {name: 'ASD', start: '2020-04-29T12:30', end: '2020-04-29T14:00', relation: 'related'},
    {name: 'ĄĘąęŁł!@#->_[]+', start: '2020-04-29T15:00', end: '2020-04-29T20:00', relation: 'related'},
    {name: 'Cokolwiek', start: '2020-04-30T09:00', end: '2020-04-30T10:30', relation: 'unrelated'},
  ]},
  {name: 'D17 1.38', exams: [
    {name: 'WDI', start: '2020-04-29T08:30', end: '2020-04-29T10:00', relation: 'unrelated'},
    {name: 'KOMPETENCJE INTERPERSONALNE', start: '2020-04-29T12:45', end: '2020-04-29T14:45', relation: 'related'},
    {name: 'PSI', start: '2020-04-29T14:45', end: '2020-04-29T17:00', relation: 'unrelated'},
    {name: 'Fizyka 2', start: '2020-04-30T8:00', end: '2020-04-30T12:00', relation: 'unrelated'},
  ]},
  {name: 'Hehe online', exams: [
    {name: 'MOWNiT 1', start: '2020-04-27T22:00', end: '2020-04-28T06:00', relation: 'unrelated'},
    {name: 'MOWNiT 2', start: '2020-04-29T12:45', end: '2020-04-29T15:00', relation: 'related'},
    {name: 'Niezdanko sorry', start: '2020-04-29T15:00', end: '2020-04-29T21:00', relation: 'unrelated'},
    {name: 'Hehehe', start: '2020-05-03T20:00', end: '2020-05-04T04:30', relation: 'owner'},
  ]},
]