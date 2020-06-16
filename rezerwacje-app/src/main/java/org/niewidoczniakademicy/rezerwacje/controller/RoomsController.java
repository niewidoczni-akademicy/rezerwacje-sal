package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.rest.room.AddRoomRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.room.EditRoomRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.room.GetRoomResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.room.GetRoomsResponse;
import org.niewidoczniakademicy.rezerwacje.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "rooms")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class RoomsController {

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
    public GetRoomsResponse uploadRooms(@RequestParam final MultipartFile file) {
        return roomService.uploadRoomsResponse(file);
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public GetRoomResponse addRoom(@RequestBody final AddRoomRequest request) {
        return roomService.saveRoom(request);
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetRoomResponse editRoom(@RequestBody final EditRoomRequest request) {
        return roomService.editRoom(request);
    }
}
