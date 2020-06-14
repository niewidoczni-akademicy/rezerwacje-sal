import React from 'react';
import MeetingRoomIcon from '@material-ui/icons/MeetingRoom';
import FormatListBulletedIcon from '@material-ui/icons/FormatListBulleted';
import AccountBalanceIcon from '@material-ui/icons/AccountBalance';
import HomeIcon from '@material-ui/icons/Home';
import PersonIcon from '@material-ui/icons/Person';
import TodayIcon from '@material-ui/icons/Today';
import SchoolIcon from '@material-ui/icons/School';
import ScheduleIcon from '@material-ui/icons/CalendarToday';
import {
  Home as HomeView,
  Rooms as RoomsView,
  Courses as CoursesView,
  Users as UsersView,
  Exams as ExamsView,
  Faculties as FacultiesView,
  Recruitments as RecruitmentsView,
  UserCourses as UserCoursesView,
  Schedule as ScheduleView,
  RecruitmentRooms as RecruitmentRoomsView
} from 'views';

const Routes = [
  {
    title: 'Strona Główna',
    href: '/',
    icon: <HomeIcon />,
    view: <HomeView />,
  },
  {
    title: 'Kalendarz',
    href: '/schedule',
    icon: <ScheduleIcon />,
    view: <ScheduleView />,
  },
  {
    title: 'Egzaminy',
    href: '/exams',
    icon: <TodayIcon />,
    view: <ExamsView />,
  },
  {
    title: 'Wydziały',
    href: '/faculties',
    icon: <AccountBalanceIcon />,
    view: <FacultiesView />,
  },
  {
    title: 'Kierunki',
    href: '/courses',
    icon: <FormatListBulletedIcon />,
    view: <CoursesView />,
  },
  {
    title: 'Sale',
    href: '/rooms',
    icon: <MeetingRoomIcon />,
    view: <RoomsView />,
  },
  {
    title: 'Rekrutacje',
    href: '/recruitments',
    icon: <SchoolIcon />,
    view: <RecruitmentsView />,
  },
  {
    title: 'Użytkownicy',
    href: '/users/add',
    icon: <PersonIcon />,
    view: <UsersView />,
  },
  {
    title: 'Lista użytkowników',
    href: '/users/standard',
    icon: <PersonIcon />,
    view: <UserCoursesView />,
  },
  {
    title: 'Sale w rekrutacji',
    href: '/recruitment/rooms',
    icon: <MeetingRoomIcon/>,
    view: <RecruitmentRoomsView/>
  }
];

export default Routes;
