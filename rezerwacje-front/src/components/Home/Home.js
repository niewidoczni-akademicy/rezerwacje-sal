import React from "react";
import "./Home.css";
import Sidebar from "../Sidebar";

class Home extends React.Component {
  render() {
    return (
      <div>
        <Sidebar />
        <div class="main">Treść...</div>
      </div>
    );
  }
}

export default Home;
