package org.niewidoczniakademicy.rezerwacje.service;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.Hours;
import org.niewidoczniakademicy.rezerwacje.model.database.Room;
import org.niewidoczniakademicy.rezerwacje.model.rest.hours.AddHoursRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.hours.TimeInterval;
import org.niewidoczniakademicy.rezerwacje.model.shared.DayOfWeek;
import org.niewidoczniakademicy.rezerwacje.service.converter.ConversionService;
import org.niewidoczniakademicy.rezerwacje.service.exception.HoursOverlappingException;
import org.niewidoczniakademicy.rezerwacje.service.exception.HoursTimeEndBeforeTimeStartException;
import org.niewidoczniakademicy.rezerwacje.service.exception.RoomNotFoundException;
import org.niewidoczniakademicy.rezerwacje.service.repository.HoursRepository;
import org.niewidoczniakademicy.rezerwacje.service.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class HoursService {

    private final RoomRepository roomRepository;
    private final HoursRepository hoursRepository;
    private final ConversionService conversionService;

    public List<Hours> saveHours(final Map<DayOfWeek, List<TimeInterval>> availabilityDetails, final Long roomId) {
        AddHoursRequest request = AddHoursRequest.builder()
                .availabilityDetails(availabilityDetails)
                .roomId(roomId)
                .build();
        return saveHours(request);
    }

    public List<Hours> saveHours(final AddHoursRequest request) {
        final Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("No room with id " + request.getRoomId()));

        validateTimeStartAndTimeEnd(request);
        validateIfOverlapping(request, room.getAvailabilityHours());

        List<Hours> availabilityHours = conversionService.convert(request);
        hoursRepository.saveAll(availabilityHours);
        return availabilityHours;
    }

    public List<Hours> editHours(final Map<DayOfWeek, List<TimeInterval>> availabilityDetails, final Long roomId) {
        AddHoursRequest request = AddHoursRequest.builder()
                .availabilityDetails(availabilityDetails)
                .roomId(roomId)
                .build();
        return editHours(request);
    }

    public List<Hours> editHours(final AddHoursRequest request) {
        final Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("No room with id " + request.getRoomId()));

        validateTimeStartAndTimeEnd(request);
        validateIfOverlapping(request, new HashSet<>());

        List<Hours> availabilityHours = conversionService.convert(request);
        hoursRepository.deleteAll(room.getAvailabilityHours());
        hoursRepository.saveAll(availabilityHours);
        return availabilityHours;
    }

    private void validateTimeStartAndTimeEnd(AddHoursRequest request) {
        for (Map.Entry<DayOfWeek, List<TimeInterval>> hoursForWeekDay : request.getAvailabilityDetails().entrySet()) {
            for (TimeInterval timeInterval : hoursForWeekDay.getValue()) {
                checkIfTimeStartBeforeTimeEnd(timeInterval.getTimeStart(), timeInterval.getTimeEnd());
            }
        }
    }

    private void checkIfTimeStartBeforeTimeEnd(LocalTime timeStart, LocalTime timeEnd) {
        if (timeStart.equals(timeEnd) || timeStart.isAfter(timeEnd)) {
            throw new HoursTimeEndBeforeTimeStartException();
        }
    }

    private void validateIfOverlapping(AddHoursRequest request, Set<Hours> currentHours) {
        for (Map.Entry<DayOfWeek, List<TimeInterval>> hoursForWeekDay : request.getAvailabilityDetails().entrySet()) {
            validateProvidedHours(hoursForWeekDay.getValue());
            validateCurrentHours(hoursForWeekDay.getValue(), currentHours, hoursForWeekDay.getKey());
        }
    }

    private void validateProvidedHours(List<TimeInterval> timeIntervals) {
        for (int i = 0; i < timeIntervals.size() - 1; i++) {
            for (int j = i + 1; j < timeIntervals.size(); j++) {
                TimeInterval t1 = timeIntervals.get(i);
                TimeInterval t2 = timeIntervals.get(j);
                if (t1.getTimeStart().isBefore(t2.getTimeEnd())
                        && t1.getTimeEnd().isAfter(t2.getTimeStart())) {
                    throw new HoursOverlappingException();
                }
            }
        }
    }

    private void validateCurrentHours(List<TimeInterval> newTimeIntervals,
                                      Set<Hours> currentHours,
                                      DayOfWeek dayOfWeek) {
        for (TimeInterval timeInterval : newTimeIntervals) {
            for (Hours hours : currentHours) {
                if (!dayOfWeek.equals(hours.getDayOfWeek())) {
                    continue;
                }

                if (timeInterval.getTimeStart().isBefore(hours.getTimeEnd())
                        && timeInterval.getTimeEnd().isAfter(hours.getTimeStart())) {
                    throw new HoursOverlappingException();
                }
            }
        }
    }
}
