package org.niewidoczniakademicy.rezerwacje.api;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.recruitment.AddRecruitmentPeriodRequest;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.recruitment.AddRecruitmentPeriodResponse;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.recruitment.GetRecruitmentPeriodResponse;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.recruitment.GetRecruitmentPeriodsResponse;
import org.niewidoczniakademicy.rezerwacje.service.RecruitmentPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("recruitment-period")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class RecruitmentPeriodApi {

    private final RecruitmentPeriodService recruitmentPeriodService;

    @PostMapping
    public ResponseEntity<AddRecruitmentPeriodResponse> addRecruitmentPeriod(@RequestBody AddRecruitmentPeriodRequest request) {
        AddRecruitmentPeriodResponse response = recruitmentPeriodService.saveRecruitmentPeriod(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"start-date", "end-date"})
    public ResponseEntity<GetRecruitmentPeriodsResponse> getRecruitmentPeriods(
            @RequestParam(value = "start-date") @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
            @RequestParam(value = "end-date") @DateTimeFormat(iso = ISO.DATE) LocalDate endDate) {
        GetRecruitmentPeriodsResponse response = recruitmentPeriodService.getRecruitmentPeriods(startDate, endDate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"id"})
    public ResponseEntity<GetRecruitmentPeriodResponse> getRecruitmentPeriod(@RequestParam String id) {
        GetRecruitmentPeriodResponse response = recruitmentPeriodService.getRecruitmentPeriod(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
