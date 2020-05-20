package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.AddSystemUserRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.OperationOnSystemUserResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.GetSystemUserResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.GetSystemUsersResponse;
import org.niewidoczniakademicy.rezerwacje.model.shared.UserType;
import org.niewidoczniakademicy.rezerwacje.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("system-user")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SystemUserController {

    private final UserService userService;

    @Secured({"ROLE_ADMINISTRATOR"})
    @PostMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public OperationOnSystemUserResponse addSystemUser(@RequestBody final AddSystemUserRequest request) {
        return userService.saveSystemUser(request);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @GetMapping(params = {"login"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetSystemUserResponse getSystemUserByUniqueUserId(@RequestParam final String login) {
        return userService.getSystemUserResponseByLogin(login);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @GetMapping(params = {"firstName", "lastName"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetSystemUsersResponse getSystemUsersByFirstNameAndLastName(@RequestParam final String firstName,
                                                                       @RequestParam final String lastName) {

        return userService.getSystemUsersByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping(params = {"type"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetSystemUsersResponse getSystemUsersByType(@RequestParam final UserType type) {
        return userService.getSystemUsersByType(type);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @GetMapping(path = "all")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetSystemUsersResponse getAllSystemUsers() {
        return userService.getAllSystemUsers();
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @DeleteMapping(params = {"userId"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public OperationOnSystemUserResponse deleteSystemUserById(@RequestParam final Long userId) {
        return userService.deleteSystemUserById(userId);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @DeleteMapping(params = {"login"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public OperationOnSystemUserResponse deleteSystemUserByLogin(@RequestParam final String login) {
        return userService.deleteSystemUserByLogin(login);
    }

    @Secured({"ROLE_ANON", "ROLE_STANDARD", "ROLE_SUPERVISOR", "ROLE_ADMINISTRATOR"})
    @GetMapping(path = "me")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Object getMe() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth);

        return auth;
    }
}
