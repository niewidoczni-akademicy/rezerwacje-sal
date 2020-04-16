import React from "react";
import UploadFileForm from "./UploadFile";
import "./RoomsForm.scss";

function Room(props) {
  return (
    <tr>
      <td>{props.data.building}</td>
      <td>{props.data.name}</td>
      <td>{props.data.capacity}</td>
    </tr>
  );
}

function RoomList(props) {
  let rooms = [];
  for (let i = 0; i < props.rooms.length; i++) {
    rooms.push(<Room data={props.rooms[i]} />);
  }
  return <table>{rooms}</table>;
}

class RoomsForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      rooms: [],
    };
  }

  componentDidMount() {
    fetch("/api/rooms/all")
      .then((res) => res.json())
      .then((json) => this.setState({ rooms: json }))
      .catch((e) => console.log(e));
  }

  render() {
    return (
        <div class="main">
            <RoomList rooms={this.state.rooms} />
            <UploadFileForm />
        </div>
    );
  }
}

export default RoomsForm;
