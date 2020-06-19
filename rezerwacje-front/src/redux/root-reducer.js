import { combineReducers } from "redux";
import { persistReducer } from "redux-persist";
import sessionStorage from "redux-persist/lib/storage/session";

import userReducer from "./user/user.reducer";
import routesReducer from "./routes/routes.reducer";

const persistConfig = {
  key: "root",
  storage: sessionStorage,
  whitelist: ["user"],
};

const rootReducer = combineReducers({
  user: userReducer,
  routes: routesReducer,
});

export default persistReducer(persistConfig, rootReducer);
