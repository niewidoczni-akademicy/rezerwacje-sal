package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.AddSystemUserRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.AddSystemUserResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.GetSystemUserResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.GetSystemUsersResponse;
import org.niewidoczniakademicy.rezerwacje.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("system-user")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SystemUserController {

    private final UserService userService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<AddSystemUserResponse> addSystemUser(@RequestBody AddSystemUserRequest request) {
        AddSystemUserResponse response = userService.saveSystemUser(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(params = {"login"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetSystemUserResponse getSystemUserByUniqueUserId(@RequestParam String login) {
        return userService.getSystemUserByLogin(login);
    }

    @GetMapping(params = {"firstName", "lastName"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetSystemUsersResponse getSystemUsersByFirstNameAndLastName(@RequestParam String firstName,
                                                                       @RequestParam String lastName) {

        return userService.getSystemUsersByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping(path = "all")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetSystemUsersResponse getAllSystemUsers() {
        return userService.getAllSystemUsers();
    }

}
