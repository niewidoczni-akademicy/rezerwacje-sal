import React from "react";
import MeetingRoomIcon from '@material-ui/icons/MeetingRoom';
import FormatListBulletedIcon from '@material-ui/icons/FormatListBulleted';
import HomeIcon from '@material-ui/icons/Home';
import PersonIcon from '@material-ui/icons/Person';
import TodayIcon from '@material-ui/icons/Today';
import SchoolIcon from '@material-ui/icons/School';
import ScheduleIcon from '@material-ui/icons/CalendarToday';
import AccountBalanceIcon from "@material-ui/icons/AccountBalance"
import {
  Home as HomeView,
  Rooms as RoomsView,
  Courses as CoursesView,
  Faculties as FacultiesView,
  Users as UsersView,
  Exams as ExamsView,
  Recruitments as RecruitmentsView,
  UserCourses as UserCoursesView,
  Schedule as ScheduleView,
  Login as LoginView,
  RecruitmentRooms as RecruitmentRoomsView
} from 'views';

const ROUTES_DATA = [
  {
    title: 'Strona Główna',
    href: '/',
    exact: true,
    icon: <HomeIcon />,
    view: <HomeView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: 'Kalendarz',
    href: '/schedule',
    exact: true,
    icon: <ScheduleIcon />,
    view: <ScheduleView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: 'Egzaminy',
    href: '/exams',
    exact: true,
    icon: <TodayIcon />,
    view: <ExamsView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: 'Wydziały',
    href: '/faculties',
    exact: true,
    icon: <AccountBalanceIcon />,
    view: <FacultiesView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: 'Kierunki',
    href: '/courses',
    exact: true,
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
    exact: true,
    icon: <SchoolIcon />,
    view: <RecruitmentsView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: 'Użytkownicy',
    href: '/users/add',
    exact: true,
    icon: <PersonIcon />,
    view: <UsersView />,
    roles: ["ADMINISTRATOR"],
  },
  {
    title: 'Lista użytkowników',
    href: '/users/standard',
    exact: true,
    icon: <PersonIcon />,
    view: <UserCoursesView />,
    roles: ["SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: "Logowanie",
    href: "/",
    exact: false,
    icon: <PersonIcon />,
    view: <LoginView />,
    roles: ["ANON"],
  },
  {
    title: 'Sale w rekrutacji',
    href: '/recruitment/rooms',
    icon: <MeetingRoomIcon />,
    view: <RecruitmentRoomsView />,
    roles: ["ADMINISTRATOR", "SUPERVISOR"]
  }
];

export default ROUTES_DATA;
