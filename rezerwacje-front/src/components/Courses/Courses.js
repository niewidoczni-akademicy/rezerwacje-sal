import React from "react";
import SimpleTable from "../Basic/Table";
import UploadFileForm from "../UploadFile";

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

class Courses extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      courses: [],
    };
  }

  componentDidMount() {
    fetch("/api/cos/all")
      .then((res) => res.json())
      .then((json) => this.setState({ courses: [{ name: "Infa" }] }))
      .catch((e) => alert(e));
  }

  coursesToHeader() {
    return ["Nazwa"]; // TODO: hardcoding?
  }

  coursesToRows() {
    return this.state.courses.map((course, index) => {
      return {
        id: index,
        fields: [course.name],
      };
    });
  }

  render() {
    return (
      <div>
        <SimpleTable
          header={this.coursesToHeader()}
          rows={this.coursesToRows()}
        />
        <UploadFileForm></UploadFileForm>
      </div>
    );
  }
}

export default Courses;
