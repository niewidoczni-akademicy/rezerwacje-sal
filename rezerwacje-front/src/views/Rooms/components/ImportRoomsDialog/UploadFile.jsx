import React from "react";

class UploadFileForm extends React.Component {
  uploadFileAction() {
    var data = new FormData();
    var fileData = document.querySelector('input[type="file"]').files[0];
    data.append("file", fileData);

    fetch("/api/rooms/upload", {
      method: "POST",
      body: data,
    }).then(
      function (res) {
        if (res.ok) {
          alert("Zapisano.");
        } else if (res.status === 400) {
          alert("Wystąpił błąd.");
        }
      },
      function (e) {
        alert("Wystąpił błąd.");
      }
    );
  }

  render() {
    return (
      <form>
        <input type="file" name="file" />
        <input
          type="button"
          value="Prześlij"
          onClick={this.uploadFileAction.bind(this)}
        />
        {/* <input
          id="contained-button-file"
          hidden
          type="file"
        />
        <label htmlFor="contained-button-file">
          <Button 
            variant="contained" 
            color="primary" 
            component="span" 
            startIcon={<CloudUploadIcon />}
            onClick={this.uploadFileAction.bind(this)}
          >
            upload .csv
          </Button>
        </label> */}
      </form>
    );
  }
}

export default UploadFileForm;
