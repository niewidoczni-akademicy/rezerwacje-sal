package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.AddSystemUserRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.AddSystemUserResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.GetSystemUserResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.GetSystemUsersResponse;
import org.niewidoczniakademicy.rezerwacje.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("system-user")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class SystemUserController {

    private final UserService userService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public AddSystemUserResponse addSystemUser(@RequestBody final AddSystemUserRequest request) {
        return userService.saveSystemUser(request);
    }

    @GetMapping(params = {"login"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetSystemUserResponse getSystemUserByUniqueUserId(@RequestParam final String login) {
        return userService.getSystemUserByLogin(login);
    }

    @GetMapping(params = {"firstName", "lastName"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetSystemUsersResponse getSystemUsersByFirstNameAndLastName(@RequestParam final String firstName,
                                                                       @RequestParam final String lastName) {

        return userService.getSystemUsersByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping(path = "all")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetSystemUsersResponse getAllSystemUsers() {
        return userService.getAllSystemUsers();
    }

}
