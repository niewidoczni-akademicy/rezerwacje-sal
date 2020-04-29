import React from 'react';
import MeetingRoomIcon from '@material-ui/icons/MeetingRoom';
import HomeIcon from '@material-ui/icons/Home';
import PersonIcon from '@material-ui/icons/Person';
import {
  Home as HomeView,
  Rooms as RoomsView,
  Users as UsersView,
  Exams as ExamsView
} from '../views';

const Routes = [
  {
    title: 'Home',
    href: '/',
    icon: <HomeIcon />,
    view: <HomeView />,
  },
  {
    title: 'Rooms',
    href: '/rooms',
    icon: <MeetingRoomIcon />,
    view: <RoomsView />,
  },
  {
    title: 'Exams',
    href: '/exams',
    icon: <TodayIcon/>,
    view: <ExamsView/>
  },
  {
    title: 'Users',
    href: '/users/add',
    icon: <PersonIcon />,
    view: <UsersView />
  }
];

export default Routes;