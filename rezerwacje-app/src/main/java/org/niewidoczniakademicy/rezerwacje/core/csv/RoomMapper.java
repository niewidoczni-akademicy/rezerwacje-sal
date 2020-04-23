package org.niewidoczniakademicy.rezerwacje.core.csv;

import org.niewidoczniakademicy.rezerwacje.core.model.database.Room;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomMapper {
    public Room convert(org.niewidoczniakademicy.rezerwacje.core.model.csv.Room room) {
        return Room.builder()
                .building(room.getBuilding())
                .name(room.getName())
                .capacity(room.getCapacity())
                .build();
    }

    public List<Room> convert(List<org.niewidoczniakademicy.rezerwacje.core.model.csv.Room> rooms) {
        return rooms.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
