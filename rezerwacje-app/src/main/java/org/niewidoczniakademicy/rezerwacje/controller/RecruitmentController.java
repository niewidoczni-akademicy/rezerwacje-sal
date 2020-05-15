package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitment.AddRecruitmentRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitment.AddRecruitmentResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitment.GetRecruitmentResponse;
import org.niewidoczniakademicy.rezerwacje.service.RecruitmentRoomService;
import org.niewidoczniakademicy.rezerwacje.service.RecruitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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
public final class RecruitmentController {

    private final RecruitmentService recruitmentService;
    private final RecruitmentRoomService recruitmentRoomService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public AddRecruitmentResponse addSystemUser(@RequestBody final AddRecruitmentRequest request) {
        return recruitmentService.saveRecruitment(request);
    }

    @GetMapping(params = {"name"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetRecruitmentResponse getSystemUserByUniqueUserId(@RequestParam final String name) {
        return recruitmentService.getRecruitmentByName(name);
    }

}
