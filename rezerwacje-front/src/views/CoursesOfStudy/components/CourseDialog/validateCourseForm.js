export default async function validateCourseForm(values) {
  let errors = {};
  if (!values.name) {
    errors.name = "Podaj nazwę kierunku.";
  }
  if (!values.faculty) {
    errors.faculty = "Podaj nazwę wydziału.";
  }
  if (!values.courseType) {
    errors.courseType = "Podaj typ kierunku.";
  }
  if (!values.contactPerson1) {
    errors.contactPerson1 = "Podaj login pierwszej osoby kontaktowej.";
  }

  return errors;
}
