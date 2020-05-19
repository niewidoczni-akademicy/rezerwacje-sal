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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("recruitment-period")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class RecruitmentPeriodController {

    private final RecruitmentPeriodService recruitmentPeriodService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public AddRecruitmentPeriodResponse addRecruitmentPeriod(@RequestBody final AddRecruitmentPeriodRequest request) {
        return recruitmentPeriodService.saveRecruitmentPeriod(request);
    }

    @GetMapping(path = "dates", params = {"start-date", "end-date"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetRecruitmentPeriodsResponse getByStartDateAndEndDate(
            @RequestParam(value = "start-date") @DateTimeFormat(iso = ISO.DATE) final LocalDate startDate,
            @RequestParam(value = "end-date") @DateTimeFormat(iso = ISO.DATE) final LocalDate endDate) {
        return recruitmentPeriodService.getRecruitmentPeriods(startDate, endDate);
    }

    @GetMapping(path = "recruitment/{recruitmentId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetRecruitmentPeriodsResponse getByRecruitmentId(@PathVariable final Long recruitmentId) {
        return recruitmentPeriodService.getRecruitmentPeriods(recruitmentId);
    }

    @GetMapping(path = "{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetRecruitmentPeriodResponse getRecruitmentPeriod(@PathVariable final Long id) {
        return recruitmentPeriodService.getRecruitmentPeriod(id);
    }
}
