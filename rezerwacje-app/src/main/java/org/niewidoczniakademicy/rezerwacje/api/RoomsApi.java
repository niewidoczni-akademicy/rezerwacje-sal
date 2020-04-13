package org.niewidoczniakademicy.rezerwacje.api;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.api.exception.InvalidInputException;
import org.niewidoczniakademicy.rezerwacje.core.csv.CSVService;
import org.niewidoczniakademicy.rezerwacje.core.model.database.Room;
import org.niewidoczniakademicy.rezerwacje.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(path = "rooms")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RoomsApi {

    private final RoomRepository roomRepository;
    private final CSVService csvService;

    @GetMapping
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @PostMapping(path = "upload")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Room> addRoom(@RequestParam MultipartFile file) {
        try {
            List<Room> rooms = csvService.parseRoomsFile(file);
            return roomRepository.saveAll(rooms);
        } catch (ParseException e) {                // TODO: handle database errors
            throw new InvalidInputException();
        }
    }
}
