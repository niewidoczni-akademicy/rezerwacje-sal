import React from 'react';
import MeetingRoomIcon from '@material-ui/icons/MeetingRoom';
import FormatListBulletedIcon from '@material-ui/icons/FormatListBulleted';
import HomeIcon from '@material-ui/icons/Home';
import PersonIcon from '@material-ui/icons/Person';
import TodayIcon from '@material-ui/icons/Today';
import ScheduleIcon from '@material-ui/icons/CalendarToday';
import {
  Home as HomeView,
  Rooms as RoomsView,
  Courses as CoursesView,
  Users as UsersView,
  Exams as ExamsView,
  UserCourses as UserCoursesView,
  Schedule as ScheduleView
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
    title: 'Users',
    href: '/users/add',
    icon: <PersonIcon />,
    view: <UsersView />
  },
  {
    title: 'Schedule',
    href: '/schedule',
    icon: <ScheduleIcon />,
    view: <ScheduleView />,
  },
  {
    title: 'Standard Users',
    href: '/users/standard',
    icon: <PersonIcon />,
    view: <UserCoursesView />
  }
];

export default Routes;
