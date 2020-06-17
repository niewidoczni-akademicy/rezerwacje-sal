export default async function validateRoomForm(values) {
  let errors = {};
  if (!values.building) {
    errors.building = "Podaj nazwę budynku."; // TODO: db table with available buildings?
  }
  if (!values.name) {
    errors.name = "Podaj nazwę sali.";
  }
  if (!values.capacity) {
    errors.capacity = "Podaj maksymalną pojemność.";
  } else if (isNaN(parseInt(values.capacity))) {
    errors.capacity = "Pojemność musi być liczbą całkowitą.";
  }

  return errors;
}
