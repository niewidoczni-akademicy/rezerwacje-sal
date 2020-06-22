export const getAuthentication = async () => {
    const res = await fetch("/api/system-user/me")
    const json = await res.json()
    if (!(json.principal instanceof Object)) {
        return {
            isLoggedIn: false,
            id: -1,
            name: "",
            role: "ANON"
        }
    } else {
        const user = json.principal.user
        return {
            isLoggedIn: true,
            id: user.id,
            name: user.firstName + user.lastName,
            role: user.userType,
        }
    }
}