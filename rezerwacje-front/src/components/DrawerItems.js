import React from "react";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";
import { SidebarIcons } from "../Const";

function DrawerItems(props) {
  return (
    <List>
      {props.items.map((item, index) => {
        const icon = SidebarIcons[item];

        return (
          <ListItem button key={item}>
            <ListItemIcon>{icon}</ListItemIcon>
            <ListItemText primary={item} />
          </ListItem>
        );
      })}
    </List>
  );
}

export default DrawerItems;
