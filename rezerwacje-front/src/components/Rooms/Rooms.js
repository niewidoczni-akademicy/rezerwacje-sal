import React from "react";
import UploadFileForm from "../UploadFile";
import Sidebar from "../Sidebar";

class Rooms extends React.Component {
  render() {
    return (
      <div>
        <Sidebar />
        <div class="main">
          <div>Cośtam</div>
          <UploadFileForm></UploadFileForm>
        </div>
      </div>
    );
  }
}

export default Rooms;
