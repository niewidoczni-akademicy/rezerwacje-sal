import React from "react";

class UploadFileForm extends React.Component {
  uploadFileAction() {
    var data = new FormData();
    var fileData = document.querySelector('input[type="file"]').files[0];
    data.append("file", fileData);

    fetch("/api/rooms/upload", {
      mode: "no-cors",
      method: "POST",
      body: data,
    }).then(
      function (res) {
        if (res.ok) {
          alert("Perfect! ");
        } else if (res.status === 400) {
          alert("Baaad request.");
        }
      },
      function (e) {
        alert("Error submitting form!");
      }
    );
  }

  render() {
    return (
      <form>
        <input type="file" name="file" />
        <input
          type="button"
          value="upload"
          onClick={this.uploadFileAction.bind(this)}
        ></input>
      </form>
    );
  }
}

export default UploadFileForm;
