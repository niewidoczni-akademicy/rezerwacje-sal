export default function validateRecruitmentCycleForm(values) {
  let errors = {};
  if (!values.type) {
    errors.type = "Wybierz typ studiów.";
  }

  if (!values.level) {
    errors.level = "Wybierz poziom studiów.";
  }

  return errors;
}
