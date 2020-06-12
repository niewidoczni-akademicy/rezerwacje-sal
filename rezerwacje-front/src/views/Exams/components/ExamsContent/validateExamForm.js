export default function validateExamForm(
  courseId,
  roomId,
  selectedDate,
  startTime,
  endTime,
  periodDetails,
  room
) {
  let errors = {};
  console.log(startTime);
  console.log(endTime);

  if (courseId == -1) {
    errors.course = "Kierunek jest wymagany.";
  }
  if (roomId == -1) {
    errors.room = "Sala jest wymagana";
  }
  if (!selectedDate) {
    errors.date = "Data jest wymagana.";
  } else if (
    selectedDate < periodDetails.startDate ||
    selectedDate > periodDetails.endDate
  ) {
    errors.date = `Cykl rekrutacyjny trwa od ${periodDetails.startDate} do ${periodDetails.endDate}`;
  }

  if (!startTime) {
    errors.startTime = "Godzina początkowa jest wymagana";
  } else if (room.availableFrom != null && startTime < room.availableFrom) {
    console.log(startTime);
    console.log(room.availableFrom);
    errors.startTime = "Sala jest dostępna od godziny " + room.availableFrom;
  } else if (room.availableTo != null && startTime >= room.availableTo) {
    errors.startTime = "Sala jest dostępna do godziny " + room.availableTo;
  }

  if (!endTime) {
    errors.endTime = "Godzina końcowa jest wymagana";
  } else if (room.availableFrom != null && endTime <= room.availableFrom) {
    errors.endTime = "Sala jest dostępna od godziny " + room.availableFrom;
  } else if (room.availableTo != null && endTime > room.availableTo) {
    errors.endTime = "Sala jest dostępna do godziny " + room.availableTo;
  }

  if (endTime <= startTime) {
    errors.endTime = "Godzina końcowa musi być późniejsza od początkowej";
  }

  return errors;
}
