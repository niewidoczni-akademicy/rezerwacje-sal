import React from "react";
import SimpleTable from "../Basic/Table";
import UploadFileForm from "../UploadFile";

class Rooms extends React.Component {
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
      .catch((e) => alert(e));
  }

  roomsToHeader() {
    return ["Budynek", "Nazwa", "Pojemność"]; // TODO: no hardcoding?
  }

  roomsToRows() {
    return this.state.rooms.map((room, index) => {
      return {
        id: index,
        fields: [room.building, room.name, room.capacity],
      };
    });
  }

  render() {
    return (
      <div>
        <SimpleTable header={this.roomsToHeader()} rows={this.roomsToRows()} />
        <UploadFileForm></UploadFileForm>
      </div>
    );
  }
}

export default Rooms;
