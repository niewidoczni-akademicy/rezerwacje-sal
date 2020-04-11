import React from "react";
import SimpleTable from "../Basic/Table";
import UploadFileForm from "../UploadFile";

class Courses extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      courses: [],
    };
  }

  componentDidMount() {
    fetch("/api/course_of_study/all")
      .then((res) => res.json())
      .then((json) => this.setState({ courses: json }))
      .catch((e) => alert(e));
  }

  coursesToHeader() {
    return [
      "Nazwa",
      "Wydział",
      "Typ studiów",
      "Kontakt 1 - imię",
      "Kontakt 1 - nazwisko",
      "Łączony",
    ]; // TODO: hardcoding?
  }

  coursesToRows() {
    if (this.state.courses.length === 0) {
      return [];
    }

    // TODO: fix this crap
    return this.state.courses.map((course, index) => {
      return {
        id: index,
        fields: [
          course.name,
          course.faculty.name,
          course.courseType,
          course.contactPerson1.firstName,
          course.contactPerson1.lastName,
          course.isJoined,
        ],
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
        <UploadFileForm to={"/api/course_of_study/upload"} />
      </div>
    );
  }
}

export default Courses;
