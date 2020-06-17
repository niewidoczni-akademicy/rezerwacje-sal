import { createSelector } from "reselect";

const selectUser = state => state.user;

export const selectCurrentUser = createSelector(
    [selectUser],
    user => user.currentUser
)

export const selectCurrentUserRole = createSelector(
    [selectCurrentUser],
    currentUser => currentUser.role
)

export const selectLoggedIn = createSelector(
    [selectCurrentUser],
    currentUser => currentUser.isLoggedIn
)