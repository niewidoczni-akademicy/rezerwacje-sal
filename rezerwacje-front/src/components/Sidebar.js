import React from "react";

class Sidebar extends React.Component {
  render() {
    return (
      <div class="sidenav">
        <a href="/">Strona główna</a>
        <a href="/rooms">Sale</a>
      </div>
    );
  }
}

export default Sidebar;