package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitment.AddRecruitmentRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitment.AddRecruitmentResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitment.DeleteRecruitmentRoomsRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitment.DeleteRecruitmentRoomsResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitment.GetRecruitmentResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitment.GetRecruitmentRoomsResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitment.GetRecruitmentsResponse;
import org.niewidoczniakademicy.rezerwacje.service.RecruitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("recruitment")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @Secured({"ROLE_SUPERVISOR", "ROLE_ADMINISTRATOR"})
    @PostMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public AddRecruitmentResponse addRecruitment(@RequestBody final AddRecruitmentRequest request) {
        return recruitmentService.saveRecruitment(request);
    }

    @Secured({"ROLE_STANDARD", "ROLE_SUPERVISOR", "ROLE_ADMINISTRATOR"})
    @GetMapping(params = {"name"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetRecruitmentResponse getRecruitmentByName(@RequestParam final String name) {
        return recruitmentService.getRecruitmentByName(name);
    }

    @Secured({"ROLE_STANDARD", "ROLE_SUPERVISOR", "ROLE_ADMINISTRATOR"})
    @GetMapping(path = {"{id}"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetRecruitmentResponse getRecruitment(@PathVariable final Long id) {
        return recruitmentService.getRecruitment(id);
    }

    @Secured({"ROLE_STANDARD", "ROLE_SUPERVISOR", "ROLE_ADMINISTRATOR"})
    @DeleteMapping(path = {"{id}/rooms"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public DeleteRecruitmentRoomsResponse deleteRecruitmentRooms(
            @PathVariable final Long id,
            @RequestBody final DeleteRecruitmentRoomsRequest request) {
        return recruitmentService.deleteRecruitmentRooms(id, request);
    }

    @Secured({"ROLE_STANDARD", "ROLE_SUPERVISOR", "ROLE_ADMINISTRATOR"})
    @GetMapping(path = {"{id}/rooms"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetRecruitmentRoomsResponse getRecruitmentRooms(@PathVariable final Long id) {
        return recruitmentService.getRecruitmentRooms(id);
    }

    @Secured({"ROLE_STANDARD", "ROLE_SUPERVISOR", "ROLE_ADMINISTRATOR"})
    @GetMapping(path = "all")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetRecruitmentsResponse getRecruitments() {
        return recruitmentService.getAllRecruitments();
    }

}
