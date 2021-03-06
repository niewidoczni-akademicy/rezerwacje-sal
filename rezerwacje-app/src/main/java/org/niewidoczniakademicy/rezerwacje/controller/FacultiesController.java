package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.rest.faculty.AddFacultyRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.faculty.EditFacultyRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.faculty.GetFacultiesResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.faculty.GetFacultyResponse;
import org.niewidoczniakademicy.rezerwacje.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Secured({"ROLE_STANDARD", "ROLE_SUPERVISOR", "ROLE_ADMINISTRATOR"})
    @PostMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public GetFacultyResponse addFaculty(@RequestBody final AddFacultyRequest request) {
        return facultyService.saveFaculty(request);
    }

    @Secured({"ROLE_STANDARD", "ROLE_SUPERVISOR", "ROLE_ADMINISTRATOR"})
    @PutMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetFacultyResponse editFaculty(@RequestBody final EditFacultyRequest request) {
        return facultyService.editFaculty(request);
    }

    @Secured({"ROLE_STANDARD", "ROLE_SUPERVISOR", "ROLE_ADMINISTRATOR"})
    @GetMapping(path = "history/{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetFacultiesResponse getHistory(@PathVariable Long id) {
        return facultyService.getHistory(id);
    }
}
