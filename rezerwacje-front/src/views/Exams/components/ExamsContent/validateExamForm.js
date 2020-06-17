export default function validateExamForm(
  courseId,
  roomId,
  selectedDate,
  startTime,
  endTime,
  periodDetails,
  room,
  dayOfWeek,
  seats
) {
  let errors = {};

  if (courseId == -1) {
    errors.course = "Kierunek jest wymagany.";
  }
  if (roomId == -1) {
    errors.room = "Sala jest wymagana";
  }

  if (Number.isNaN(seats)) {
    errors.seats = "Niepoprawna wartość dla liczby miejsc";
  } else if (room.room.capacity != null && seats > room.room.capacity) {
    errors.seats =
      "W sali jest dostępnych tylko " + room.room.capacity + " miejsc";
  }

  const createIntervalData = hour => {
    return {
      timeStart: hour.timeStart,
      timeEnd: hour.timeEnd
    };
  };

  var availabilityHours;

  if (room != -1) {
    availabilityHours = room.room.availabilityHours
      .filter(hour => hour.dayOfWeek === dayOfWeek)
      .map(hour => createIntervalData(hour));
  }

  const validateTime = hour => {
    if (availabilityHours.length === 0) return true;
    var interval;
    for (interval in availabilityHours) {
      if (hour >= interval.startTime && hour <= interval.endTime) return true;
    }
    return false;
  };

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
  } else if (room != -1 && !validateTime(startTime)) {
    errors.startTime = "Sala nie jest dostępna o tej godzinie";
  }

  if (endTime <= startTime) {
    errors.endTime = "Godzina końcowa musi być późniejsza od początkowej";
  } else if (room != -1 && !validateTime(endTime)) {
    errors.endTime = "Sala nie jest dostępna o tej godzinie";
  }

  return errors;
}
