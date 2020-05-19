import ROUTES_DATA from "./routes.data";

export const getAvaibleRoutes = (role) => ROUTES_DATA.filter(route => route.roles.includes(role))