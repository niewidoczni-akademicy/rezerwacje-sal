package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.rest.hours.AddHoursRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.hours.AddHoursResponse;
import org.niewidoczniakademicy.rezerwacje.service.HoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hours")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class HoursController {

    private final HoursService hoursService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public AddHoursResponse addHours(@RequestBody final AddHoursRequest request) {
        return AddHoursResponse.builder()
                .availabilityHours(hoursService.saveHours(request))
                .build();
    }
}
