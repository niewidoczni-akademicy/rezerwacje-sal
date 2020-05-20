import React from "react";
import MeetingRoomIcon from '@material-ui/icons/MeetingRoom';	
import FormatListBulletedIcon from '@material-ui/icons/FormatListBulleted';	
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
  Recruitments as RecruitmentsView,	
  UserCourses as UserCoursesView,	
  Schedule as ScheduleView,
  Login as LoginView,
} from 'views';

const ROUTES_DATA = [
  {
    title: "Home",
    href: "/",
    exact: true,
    icon: <HomeIcon />,
    view: <HomeView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: "Rooms",
    href: "/rooms",
    exact: true,
    icon: <MeetingRoomIcon />,
    view: <RoomsView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: "Courses",
    href: "/courses",
    exact: true,
    icon: <FormatListBulletedIcon />,
    view: <CoursesView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: "Exams",
    href: "/exams",
    exact: true,
    icon: <TodayIcon />,
    view: <ExamsView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: "Recruitments",
    href: "/recruitments",
    exact: true,
    icon: <SchoolIcon />,
    view: <RecruitmentsView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: "Users",
    href: "/users/add",
    exact: true,
    icon: <PersonIcon />,
    view: <UsersView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: "Schedule",
    href: "/schedule",
    exact: true,
    icon: <ScheduleIcon />,
    view: <ScheduleView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: "Standard Users",
    href: "/users/standard",
    exact: true,
    icon: <PersonIcon />,
    view: <UserCoursesView />,
    roles: ["STANDARD", "SUPERVISOR", "ADMINISTRATOR"],
  },
  {
    title: "Login",
    href: "/",
    exact: false,
    icon: <PersonIcon />,
    view: <LoginView />,
    roles: ["ANON"],
  },
];

export default ROUTES_DATA;
