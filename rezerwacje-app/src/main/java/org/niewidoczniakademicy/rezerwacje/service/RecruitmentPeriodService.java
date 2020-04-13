package org.niewidoczniakademicy.rezerwacje.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.niewidoczniakademicy.rezerwacje.core.model.database.RecruitmentPeriod;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.AddRecruitmentPeriodRequest;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.AddRecruitmentPeriodResponse;
import org.niewidoczniakademicy.rezerwacje.repository.RecruitmentPeriodRepository;
import org.niewidoczniakademicy.rezerwacje.service.exception.RecruitmentPeriodEndDateBeforeStartDateException;
import org.niewidoczniakademicy.rezerwacje.service.exception.RecruitmentPeriodStartDateBeforeCurrentDateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RecruitmentPeriodService {

    private final RecruitmentPeriodRepository recruitmentPeriodRepository;

    public AddRecruitmentPeriodResponse saveRecruitmentPeriod(AddRecruitmentPeriodRequest request) {
        validateRecruitmentPeriod(request);

        RecruitmentPeriod recruitmentPeriod = RecruitmentPeriod.builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
        recruitmentPeriodRepository.save(recruitmentPeriod);

        return AddRecruitmentPeriodResponse.builder()
                .recruitmentPeriodId(recruitmentPeriod.getId())
                .build();
    }

    private void validateRecruitmentPeriod(AddRecruitmentPeriodRequest request) {
        validateEndDateNotBeforeStartDate(request);
        validateStartDateNotBeforeCurrentDate(request);
    }

    private void validateEndDateNotBeforeStartDate(AddRecruitmentPeriodRequest request) {
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        if (endDate.isBefore(startDate))
            throw new RecruitmentPeriodEndDateBeforeStartDateException();
    }

    private void validateStartDateNotBeforeCurrentDate(AddRecruitmentPeriodRequest request) {
        LocalDate startDate = request.getStartDate();
        LocalDate currentDate = LocalDate.now();
        if (startDate.isBefore(currentDate))
            throw new RecruitmentPeriodStartDateBeforeCurrentDateException();
    }
}
