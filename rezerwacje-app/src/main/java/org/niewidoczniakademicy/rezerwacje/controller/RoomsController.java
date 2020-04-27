package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.rest.room.GetRoomsResponse;
import org.niewidoczniakademicy.rezerwacje.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "rooms")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RoomsController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<GetRoomsResponse> getAll() {
        GetRoomsResponse response = roomService.getAllResponse();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "upload")
    public ResponseEntity<GetRoomsResponse> uploadRooms(@RequestParam MultipartFile file) {
        GetRoomsResponse response = roomService.uploadRoomsResponse(file);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
