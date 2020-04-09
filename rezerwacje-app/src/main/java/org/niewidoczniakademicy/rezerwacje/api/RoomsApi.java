package org.niewidoczniakademicy.rezerwacje.api;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.api.exception.InvalidInputException;
import org.niewidoczniakademicy.rezerwacje.core.CSVService;
import org.niewidoczniakademicy.rezerwacje.core.model.Room;
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

    @GetMapping(path = "all")
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @PostMapping(path = "upload")
    @ResponseStatus(value = HttpStatus.OK)
    public void addRoom(@RequestParam MultipartFile file) {
        try {
            List<Room> rooms = csvService.parseRoomsFile(file);
            rooms.forEach(roomRepository::save);    // TODO: to single transaction
        } catch (ParseException e) {                // TODO: handle database errors
            throw new InvalidInputException();
        }
    }
}
