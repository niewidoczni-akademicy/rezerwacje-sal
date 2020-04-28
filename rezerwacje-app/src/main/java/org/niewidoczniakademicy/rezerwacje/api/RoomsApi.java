package org.niewidoczniakademicy.rezerwacje.api;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.api.exception.InvalidInputException;
import org.niewidoczniakademicy.rezerwacje.core.csv.CSVService;
import org.niewidoczniakademicy.rezerwacje.core.csv.RoomMapper;
import org.niewidoczniakademicy.rezerwacje.core.model.database.Room;
import org.niewidoczniakademicy.rezerwacje.dao.RoomDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(path = "rooms")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class RoomsApi {

    private final RoomDAO roomDAO;
    private final CSVService csvService;
    private final RoomMapper roomMapper;

    @GetMapping
    public List<Room> getAll() {
        return roomDAO.findAll();
    }

    @PostMapping(path = "upload")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Room> addRoom(@RequestParam MultipartFile file) {
        try {
            List<org.niewidoczniakademicy.rezerwacje.core.model.csv.Room> csvRooms =
                    csvService.parseRoomsFile(file);
            List<Room> rooms = roomMapper.convert(csvRooms);
            return roomDAO.save(rooms);
        } catch (ParseException e) {                // TODO: handle database errors
            throw new InvalidInputException("");
        }
    }
}
