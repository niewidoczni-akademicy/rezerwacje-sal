package org.niewidoczniakademicy.rezerwacje.service.csv;

import org.niewidoczniakademicy.rezerwacje.model.csv.CsvRoom;
import org.niewidoczniakademicy.rezerwacje.model.database.Room;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoomMapper {
    public final Room convert(final CsvRoom csvRoom) {
        return Room.builder()
                .building(csvRoom.getBuilding())
                .name(csvRoom.getName())
                .capacity(csvRoom.getCapacity())
                .build();
    }

    public final Set<Room> convert(final List<CsvRoom> csvRooms) {
        return csvRooms.stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }
}
