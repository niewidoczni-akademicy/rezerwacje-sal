const loginExists = async login => {
    const res = await fetch(`/api/system-user?login=${login}`)
    console.log(res.status)
    if (res.status == 404) return false;
    else return true;
}

export default async function validateUserForm(values) {
    let errors = {};
    if (!values.firstName) {
        errors.firstName = "Imię jest wymagane."
    }
    if (!values.lastName) {
        errors.lastName = "Nazwisko jest wymagane."
    }
    if (!values.email) {
        errors.email = "Adres email jest wymagany."
    } else if (!/\S+@\S+\.\S+/.test(values.email)) {
      errors.email = "Adres email jest nieprawidłowy.";
    }

    if (!values.phone) {
        errors.phone = "Numer telefonu jest wymagany."
    } else if (values.phone.length != 9) {
        errors.phone = "Numer telefonu musi mieć dokładnie 9 cyfr."
    }

    if (!values.login) {
        errors.login = "Login jest wymagany."
    } else {
        const exists = await loginExists(values.login)
        console.log(exists)
        if (exists) errors.login = "Podany login już istnieje."
    }

    if (!values.password) {
      errors.password = "Hasło jest wymagane.";
    } 

    if (!values.confirmPassword) {
        errors.confirmPassword = "Potwierdź hasło."
    } else if (values.password != values.confirmPassword) {
        errors.confirmPassword = "Hasła nie są takie same."
    }

    if (!values.role) {
        errors.role = "Wybierz rodzaj uprawnień."
    }

   return errors;
}
