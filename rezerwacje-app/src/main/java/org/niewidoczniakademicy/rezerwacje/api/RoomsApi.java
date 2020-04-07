package org.niewidoczniakademicy.rezerwacje.api;

import org.niewidoczniakademicy.rezerwacje.api.exception.InvalidInputException;
import org.niewidoczniakademicy.rezerwacje.core.CSVService;
import org.niewidoczniakademicy.rezerwacje.core.model.Room;
import org.niewidoczniakademicy.rezerwacje.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(path = "rooms")
public class RoomsApi {

    private final RoomRepository roomRepository;
    private final CSVService csvService;
    private Logger logger = LoggerFactory.getLogger(RoomsApi.class);

    @Autowired
    public RoomsApi(RoomRepository roomRepository, CSVService csvService) {
        this.roomRepository = roomRepository;
        this.csvService = csvService;
    }

    @PostMapping(path = "upload")
    @ResponseStatus(value = HttpStatus.OK)
    public void addRoom(@RequestParam(name = "file") MultipartFile file) {
        try {
            List<Room> rooms = csvService.parseRoomsFile(file);
            rooms.forEach(roomRepository::save);

            for (Room r : roomRepository.findAll()) {
                logger.info(r.toString());
            }
        } catch (ParseException e) {
            throw new InvalidInputException();
        }
    }
}
