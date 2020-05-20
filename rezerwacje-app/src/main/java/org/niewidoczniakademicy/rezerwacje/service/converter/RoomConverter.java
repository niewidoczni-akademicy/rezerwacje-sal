package org.niewidoczniakademicy.rezerwacje.service.converter;

import org.niewidoczniakademicy.rezerwacje.model.database.Room;
import org.niewidoczniakademicy.rezerwacje.model.rest.room.AddRoomRequest;
import org.springframework.stereotype.Component;


@Component
public final class RoomConverter
        implements GenericConverter<AddRoomRequest, Room> {

    @Override
    public Room createFrom(final AddRoomRequest dto) {
        return Room.builder()
                .building(dto.getBuilding())
                .name(dto.getName())
                .capacity(dto.getCapacity())
                .build();
    }
}
