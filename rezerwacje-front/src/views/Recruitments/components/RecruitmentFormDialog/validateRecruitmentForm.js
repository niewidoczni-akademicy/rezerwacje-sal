export default async function validateRecruitmentForm(values) {
  let errors = {};
  if (!values.name) {
    errors.name = "Nazwa jest wymagana.";
  }

  if (!values.startDate) {
    errors.startDate = "Ustaw datę początkową.";
  }

  if (!values.endDate) {
    errors.endDate = "Ustaw datę końcową.";
  }

  if (values.startDate >= values.endDate) {
    errors.endDate = "Data końcowa powinna być późniejsza niż data początkowa."
  }

  return errors;
}
