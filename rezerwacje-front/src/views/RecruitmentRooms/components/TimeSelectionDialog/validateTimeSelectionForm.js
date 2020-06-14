export default function validateTimeSelectionForm(startTime, endTime) {
  let errors = {};

  if (!startTime) {
    errors.startTime = "Godzina początkowa jest wymagana";
  }

  if (!endTime) {
    errors.endTime = "Godzina końcowa jest wymagana";
  }

  if (endTime <= startTime) {
    errors.endTime = "Godzina końcowa musi być późniejsza od początkowej";
  }

  return errors;
}
