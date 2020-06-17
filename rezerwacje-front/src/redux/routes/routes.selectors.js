import { createSelector } from 'reselect'

import { selectCurrentUserRole } from '../user/user.selectors';

const selectRoutes = state => state.routes

export const selectAllRoutes = createSelector(
    [selectRoutes], 
    routes => routes.all
)

export const selectAvaibleRoutes = createSelector(
    [selectAllRoutes, selectCurrentUserRole],
    (allRoutes, role) => allRoutes.filter(route => route.roles.includes(role))
)