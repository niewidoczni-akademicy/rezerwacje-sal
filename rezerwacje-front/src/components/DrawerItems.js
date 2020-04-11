import React from "react";
import { Link } from "react-router-dom";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";
import CalendarTodayIcon from "@material-ui/icons/CalendarToday";
import PeopleIcon from "@material-ui/icons/People";

import {
  AppBarLabel,
  UsersItem,
  RecruitmentPeriodsItem,
  RoomsItem,
} from "../Const";

const SidebarIcons = {
  Użytkownicy: <PeopleIcon />,
  Rekrutacje: <CalendarTodayIcon />,
  Sale: null,
  "Kierunki studiów": null,
};

const SidebarLinks = {
  Użytkownicy: "/users",
  Rekrutacje: "/recruitments",
  "Kierunki studiów": "/courses",
  Sale: "/rooms",
};

function DrawerItems(props) {
  return (
    <List>
      {props.items.map((item, index) => {
        const icon = SidebarIcons[item];

        return (
          <ListItem button key={item} component={Link} to={SidebarLinks[item]}>
            <ListItemIcon>{icon}</ListItemIcon>
            <ListItemText primary={item} />
          </ListItem>
        );
      })}
    </List>
  );
}

export default DrawerItems;
