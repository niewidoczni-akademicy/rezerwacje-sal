export default function validateRecruitmentForm(values) {
  let errors = {};
  if (!values.name) {
    errors.name = "Nazwa jest wymagana.";
  }

  if (!values.status) {
    errors.status = "Wybierz status rekrutacji.";
  }

  return errors;
}
