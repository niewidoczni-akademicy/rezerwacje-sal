import React from "react";
import SimpleTable from "../Basic/Table";
import UploadFileForm from "../UploadFile";
import "./Rooms.css";

// function Room(props) {
//   return (
//     <ListItem button>
//       <ListItemText>{props.data.building}</ListItemText>
//       <ListItemText>{props.data.name}</ListItemText>
//       <ListItemText>{props.data.capacity}</ListItemText>
//     </ListItem>
//   );
// }

// function RoomList(props) {
//   let rooms = [];
//   for (let i = 0; i < props.rooms.length; i++) {
//     rooms.push(<Room data={props.rooms[i]} />);
//   }

//   return <List component="nav">{rooms}</List>;
// }

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
