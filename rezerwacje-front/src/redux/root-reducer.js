import { combineReducers } from "redux";
import userReducer from "./user/user.reducer";
import routesReducer from "./routes/routes.reducer";

const rootReducer = combineReducers({
  user: userReducer,
  routes: routesReducer,
});

export default rootReducer;
