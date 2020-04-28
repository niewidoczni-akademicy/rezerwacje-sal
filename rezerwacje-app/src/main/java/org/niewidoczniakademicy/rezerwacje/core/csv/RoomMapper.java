package org.niewidoczniakademicy.rezerwacje.core.csv;

import org.niewidoczniakademicy.rezerwacje.core.model.csv.CsvRoom;
import org.niewidoczniakademicy.rezerwacje.core.model.database.Room;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomMapper {
    public final Room convert(CsvRoom csvRoom) {
        return Room.builder()
                .building(csvRoom.getBuilding())
                .name(csvRoom.getName())
                .capacity(csvRoom.getCapacity())
                .build();
    }

    public final List<Room> convert(List<CsvRoom> csvRooms) {
        return csvRooms.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
