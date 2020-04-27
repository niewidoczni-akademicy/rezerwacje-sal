package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.AddSystemUserRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.AddSystemUserResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.GetSystemUserResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.GetSystemUsersResponse;
import org.niewidoczniakademicy.rezerwacje.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("system-user")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SystemUserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<AddSystemUserResponse> addSystemUser(@RequestBody AddSystemUserRequest request) {
        AddSystemUserResponse response = userService.saveSystemUser(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(params = {"login"})
    public ResponseEntity<GetSystemUserResponse> getSystemUserByUniqueUserId(@RequestParam String login) {
        GetSystemUserResponse response = userService.getSystemUserByLogin(login);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(params = {"firstName", "lastName"})
    public ResponseEntity<GetSystemUsersResponse> getSystemUsersByFirstNameAndLastName(@RequestParam String firstName,
                                                                                       @RequestParam String lastName) {

        GetSystemUsersResponse response = userService.getSystemUsersByFirstNameAndLastName(firstName, lastName);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "all")
    public ResponseEntity<GetSystemUsersResponse> getAllSystemUsers() {
        GetSystemUsersResponse response = userService.getAllSystemUsers();
        return ResponseEntity.ok().body(response);
    }

}
