package org.niewidoczniakademicy.rezerwacje.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.niewidoczniakademicy.rezerwacje.model.csv.CsvRoom;
import org.niewidoczniakademicy.rezerwacje.model.database.Room;
import org.niewidoczniakademicy.rezerwacje.model.rest.room.GetRoomsResponse;
import org.niewidoczniakademicy.rezerwacje.service.csv.CSVService;
import org.niewidoczniakademicy.rezerwacje.service.csv.RoomMapper;
import org.niewidoczniakademicy.rezerwacje.service.exception.InvalidInputException;
import org.niewidoczniakademicy.rezerwacje.service.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class RoomService {
    private final CSVService csvService;
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public GetRoomsResponse getAllResponse() {
        Set<Room> rooms = new HashSet<>(this.roomRepository.findAll());

        return GetRoomsResponse.builder()
                .rooms(rooms)
                .build();
    }

    public GetRoomsResponse uploadRoomsResponse(final MultipartFile file) {
        try {
            List<CsvRoom> csvRooms = csvService.parseRoomsFile(file);
            Set<Room> rooms = roomMapper.convert(csvRooms);
            roomRepository.saveAll(rooms);

            return GetRoomsResponse.builder()
                    .rooms(rooms)
                    .build();
        } catch (ParseException e) {                // TODO: handle database errors
            // TODO: extract more details from CSV parser
            throw new InvalidInputException("Error occurred while parsing CSV file!");
        }
    }
}
