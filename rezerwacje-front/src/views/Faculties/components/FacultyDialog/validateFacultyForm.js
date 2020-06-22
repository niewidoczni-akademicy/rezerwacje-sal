export default async function validateFacultyForm(values) {
  let errors = {};
  if (!values.name) {
    errors.name = 'Podaj nazwę wydziału.';
  }

  return errors;
}
