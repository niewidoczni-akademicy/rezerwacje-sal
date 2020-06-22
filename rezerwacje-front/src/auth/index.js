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
        console.log(json);
        const user = json.principal.user
        if (user === undefined) { //Special first admin user
            return {
                isLoggedIn: true,
                id: -1,
                name: json.name,
                role: "ADMINISTRATOR",
            }
        }
        return {
            isLoggedIn: true,
            id: user.id,
            name: user.firstName + user.lastName,
            role: user.userType,
        }
    }
}