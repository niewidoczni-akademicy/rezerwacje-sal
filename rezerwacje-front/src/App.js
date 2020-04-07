import React from "react";
import { BrowserRouter, Switch, Route } from "react-router-dom";
import "./App.css";
import Home from "./components/Home";
import UploadFileForm from "./components/UploadFile";

function App() {
  return (
    <div>
      <div className="App">Hello, world!</div>
      <BrowserRouter>
        <Switch>
          <Route exact path="/" component={Home} />
          <Route path="/rooms/upload" component={UploadFileForm} />
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
