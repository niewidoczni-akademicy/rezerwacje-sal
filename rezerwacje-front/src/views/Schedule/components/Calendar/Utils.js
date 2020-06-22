import { MONTHS_PER_YEAR, RELATION_COLOR } from './constants'

export const hexToRgb = hex => {
  const v = parseInt(hex, 16)
  const r = (v >> 16) & 255
  const g = (v >> 8) & 255
  const b = v & 255
  return [r, g, b]
}

export const colourIsLight = (r, g, b) => {
  const a = 1 - (0.299 * r + 0.587 * g + 0.114 * b) / 255
  return a < 0.5
}

export const mapRelationToColor = relation => {
  switch (relation) {
    case 'closely_related':
      return RELATION_COLOR.OWNER
    case 'related':
      return RELATION_COLOR.RELATED
    case 'unrelated':
      return RELATION_COLOR.UNRELATED
  }
}

export const dateMinutesWithLeadingZeros = date => { 
  const minutes = date.getMinutes()
  return (minutes < 10 ? '0' : '') + minutes;
}