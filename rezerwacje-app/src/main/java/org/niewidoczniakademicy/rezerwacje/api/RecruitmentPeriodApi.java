package org.niewidoczniakademicy.rezerwacje.api;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.AddRecruitmentPeriodRequest;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.AddRecruitmentPeriodResponse;
import org.niewidoczniakademicy.rezerwacje.service.RecruitmentPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("recruitment-period")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RecruitmentPeriodApi {

    private final RecruitmentPeriodService recruitmentPeriodService;

    @PostMapping
    public ResponseEntity<AddRecruitmentPeriodResponse> addRoom(@RequestBody AddRecruitmentPeriodRequest request) {
        AddRecruitmentPeriodResponse response = recruitmentPeriodService.saveRecruitmentPeriod(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
