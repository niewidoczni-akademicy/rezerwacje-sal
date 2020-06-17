package org.niewidoczniakademicy.rezerwacje.service.converter;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.Hours;
import org.niewidoczniakademicy.rezerwacje.model.database.Room;
import org.niewidoczniakademicy.rezerwacje.model.rest.hours.AddHoursRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.hours.TimeInterval;
import org.niewidoczniakademicy.rezerwacje.model.shared.DayOfWeek;
import org.niewidoczniakademicy.rezerwacje.service.exception.RoomNotFoundException;
import org.niewidoczniakademicy.rezerwacje.service.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class HoursConverter
        implements GenericConverter<AddHoursRequest, List<Hours>> {

    private final RoomRepository roomRepository;

    @Override
    public List<Hours> createFrom(final AddHoursRequest dto) {
        Room room = roomRepository
                .findById(dto.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("No room with id " + dto.getRoomId()));
        List<Hours> result = new ArrayList<>();
        for (Map.Entry<DayOfWeek, List<TimeInterval>> hoursForWeekDay : dto.getAvailabilityDetails().entrySet()) {
            for (TimeInterval timeInterval : hoursForWeekDay.getValue()) {
                result.add(Hours.builder()
                        .dayOfWeek(hoursForWeekDay.getKey())
                        .timeStart(timeInterval.getTimeStart())
                        .timeEnd(timeInterval.getTimeEnd())
                        .room(room)
                        .build());
            }
        }
        return result;
    }
}
