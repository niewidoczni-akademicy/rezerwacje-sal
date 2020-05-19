import { UserActionTypes } from "../user/user.types"
import { getAvaibleRoutes } from "./routes.utils"

const INITIAL_STATE = {
  avaible: getAvaibleRoutes("ANON")
}

const routesReducer = (state = INITIAL_STATE, action) => {
  switch(action.type) {
    case UserActionTypes.SET_CURRENT_USER:
      return {
        ...state, 
        avaible: getAvaibleRoutes(action.payload.role)
      }
    default:
      return state
  }
}

export default routesReducer