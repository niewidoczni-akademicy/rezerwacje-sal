import React from 'react';
import MeetingRoomIcon from '@material-ui/icons/MeetingRoom';
import HomeIcon from '@material-ui/icons/Home';
import TodayIcon from '@material-ui/icons/Today';
import {
  Home as HomeView,
  Rooms as RoomsView,
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
  }
];

export default Routes;