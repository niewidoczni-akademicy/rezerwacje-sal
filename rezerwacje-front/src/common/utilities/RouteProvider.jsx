import React from 'react';
import MeetingRoomIcon from '@material-ui/icons/MeetingRoom';
import FormatListBulletedIcon from '@material-ui/icons/FormatListBulleted';
import HomeIcon from '@material-ui/icons/Home';
import PersonIcon from '@material-ui/icons/Person';
import TodayIcon from '@material-ui/icons/Today';
import SchoolIcon from '@material-ui/icons/School';
import {
  Home as HomeView,
  Rooms as RoomsView,
  Courses as CoursesView,
  Users as UsersView,
  Exams as ExamsView,
  Recruitments as RecruitmentsView
} from 'views';

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
    title: 'Courses',
    href: '/courses',
    icon: <FormatListBulletedIcon />,
    view: <CoursesView />,
  },
  {
    title: 'Exams',
    href: '/exams',
    icon: <TodayIcon />,
    view: <ExamsView />,
  },
  {
    title: 'Recruitments',
    href: '/recruitments',
    icon: <SchoolIcon />,
    view: <RecruitmentsView />
  },
  {
    title: 'Users',
    href: '/users/add',
    icon: <PersonIcon />,
    view: <UsersView />,
  },
];

export default Routes;
