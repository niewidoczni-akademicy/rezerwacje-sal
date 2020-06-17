import React from "react";
import MeetingRoomIcon from '@material-ui/icons/MeetingRoom';	
import FormatListBulletedIcon from '@material-ui/icons/FormatListBulleted';	
import HomeIcon from '@material-ui/icons/Home';	
import PersonIcon from '@material-ui/icons/Person';	
import TodayIcon from '@material-ui/icons/Today';	
import SchoolIcon from '@material-ui/icons/School';	
import ScheduleIcon from '@material-ui/icons/CalendarToday';
import AccountBalanceIcon from '@material-ui/icons/AccountBalance';
import {	
  Home as HomeView,	
  Rooms as RoomsView,	
  Courses as CoursesView,	
  Users as UsersView,	
  Exams as ExamsView,	
  Recruitments as RecruitmentsView,	
  UserCourses as UserCoursesView,	
  Schedule as ScheduleView,
  Faculties as FacultiesView
} from 'views';

const ROUTES_DATA = [
  {
    title: 'Strona Główna',
    href: '/',
    icon: <HomeIcon />,
    view: <HomeView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: 'Kalendarz',
    href: '/schedule',
    icon: <ScheduleIcon />,
    view: <ScheduleView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: 'Egzaminy',
    href: '/exams',
    icon: <TodayIcon />,
    view: <ExamsView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: 'Wydziały',
    href: '/faculties',
    icon: <AccountBalanceIcon />,
    view: <FacultiesView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: 'Kierunki',
    href: '/courses',
    icon: <FormatListBulletedIcon />,
    view: <CoursesView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: 'Sale',
    href: '/rooms',
    icon: <MeetingRoomIcon />,
    view: <RoomsView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: 'Rekrutacje',
    href: '/recruitments',
    icon: <SchoolIcon />,
    view: <RecruitmentsView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: 'Użytkownicy',
    href: '/users/add',
    icon: <PersonIcon />,
    view: <UsersView />,
    roles: ["ADMINISTRATOR"],
  },
  {
    title: 'Lista użytkowników',
    href: '/users/standard',
    icon: <PersonIcon />,
    view: <UserCoursesView />,
    roles: ["ADMINISTRATOR"],
  },
  {
    title: "Logowanie",
    href: "/login",
    icon: <PersonIcon />, //TODO
    view: <UsersView />,
    roles: ["ANON"],
  },
];

export default ROUTES_DATA;
