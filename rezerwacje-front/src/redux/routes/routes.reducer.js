import ROUTES_DATA from "./routes.data"

const INITIAL_STATE = {
  all: ROUTES_DATA,
}

const routesReducer = (state = INITIAL_STATE, action) => {
  switch(action.type) {
    default:
      return state
  }
}

export default routesReducer