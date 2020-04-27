package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.rest.room.GetRoomsResponse;
import org.niewidoczniakademicy.rezerwacje.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "rooms")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RoomsController {

    private final RoomService roomService;

    @GetMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetRoomsResponse getAll() {
        return roomService.getAllResponse();
    }

    @PostMapping(path = "upload")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public GetRoomsResponse uploadRooms(@RequestParam MultipartFile file) {
        return roomService.uploadRoomsResponse(file);

    }
}
