package org.niewidoczniakademicy.rezerwacje.api;

import org.niewidoczniakademicy.rezerwacje.core.CSVService;
import org.niewidoczniakademicy.rezerwacje.core.model.Room;
import org.niewidoczniakademicy.rezerwacje.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(path = "rooms")
public class RoomsApi {

    private final RoomRepository roomRepository;
    private final CSVService csvService;

    @Autowired
    public RoomsApi(RoomRepository roomRepository, CSVService csvService) {
        this.roomRepository = roomRepository;
        this.csvService = csvService;
    }

    @PostMapping(path = "upload")
    public String addRoom(@RequestParam(name = "file") MultipartFile file) {
        try {
            List<Room> rooms = csvService.parseRoomsFile(file);
            rooms.forEach(roomRepository::save);
            for (Room r : roomRepository.findAll()) {
                System.out.println(r.toString());
            }
            return "ok";
        } catch (ParseException e) {
            return "crap";
        }
    }
}
