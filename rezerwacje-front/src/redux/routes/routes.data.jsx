import React from 'react';
import MeetingRoomIcon from '@material-ui/icons/MeetingRoom';
import HomeIcon from '@material-ui/icons/Home';
import PersonIcon from '@material-ui/icons/Person';
import TodayIcon from '@material-ui/icons/Today';
import {
  Home as HomeView,
  Rooms as RoomsView,
  Users as UsersView,
  Exams as ExamsView
} from '../../views';

const ROUTES_DATA = [
  {
    title: 'Home',
    href: '/',
    icon: <HomeIcon />,
    view: <HomeView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: 'Rooms',
    href: '/rooms',
    icon: <MeetingRoomIcon />,
    view: <RoomsView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: 'Exams',
    href: '/exams',
    icon: <TodayIcon/>,
    view: <ExamsView/>,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: 'Users',
    href: '/users/add',
    icon: <PersonIcon />,
    view: <UsersView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: 'Login',
    href: '/login',
    icon: <PersonIcon />, //TODO
    view: <UsersView />,
    roles: ["ANON"],
  }
];

export default ROUTES_DATA;