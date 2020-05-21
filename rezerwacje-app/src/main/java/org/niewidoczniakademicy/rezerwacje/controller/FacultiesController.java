package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.rest.faculty.room.GetFacultiesResponse;
import org.niewidoczniakademicy.rezerwacje.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "faculties")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FacultiesController {

    private final FacultyService facultyService;

    @Secured({"ROLE_STANDARD", "ROLE_SUPERVISOR", "ROLE_ADMINISTRATOR"})
    @GetMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetFacultiesResponse getAll() {
        return facultyService.getAllResponse();
    }
}
