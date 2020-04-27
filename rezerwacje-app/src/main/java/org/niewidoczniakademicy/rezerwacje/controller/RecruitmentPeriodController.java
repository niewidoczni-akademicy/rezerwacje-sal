package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod.AddRecruitmentPeriodRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod.AddRecruitmentPeriodResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod.GetRecruitmentPeriodResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod.GetRecruitmentPeriodsResponse;
import org.niewidoczniakademicy.rezerwacje.service.RecruitmentPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("recruitment-period")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RecruitmentPeriodController {

    private final RecruitmentPeriodService recruitmentPeriodService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public AddRecruitmentPeriodResponse addRecruitmentPeriod(@RequestBody AddRecruitmentPeriodRequest request) {
        return recruitmentPeriodService.saveRecruitmentPeriod(request);
    }

    @GetMapping(params = {"start-date", "end-date"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetRecruitmentPeriodsResponse getRecruitmentPeriods(
            @RequestParam(value = "start-date") @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
            @RequestParam(value = "end-date") @DateTimeFormat(iso = ISO.DATE) LocalDate endDate) {
        return recruitmentPeriodService.getRecruitmentPeriods(startDate, endDate);
    }

    @GetMapping(params = {"id"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetRecruitmentPeriodResponse getRecruitmentPeriod(@RequestParam String id) {
        return recruitmentPeriodService.getRecruitmentPeriod(id);
    }
}
