package org.niewidoczniakademicy.rezerwacje.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.niewidoczniakademicy.rezerwacje.core.model.database.RecruitmentPeriod;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.recruitment.AddRecruitmentPeriodRequest;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.recruitment.AddRecruitmentPeriodResponse;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.recruitment.GetRecruitmentPeriodResponse;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.recruitment.GetRecruitmentPeriodsResponse;
import org.niewidoczniakademicy.rezerwacje.dao.repository.RecruitmentPeriodRepository;
import org.niewidoczniakademicy.rezerwacje.service.converter.ConversionService;
import org.niewidoczniakademicy.rezerwacje.service.exception.RecruitmentPeriodEndDateBeforeStartDateException;
import org.niewidoczniakademicy.rezerwacje.service.exception.RecruitmentPeriodStartDateBeforeCurrentDateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RecruitmentPeriodService {

    private final RecruitmentPeriodRepository recruitmentPeriodRepository;
    private final ConversionService conversionService;

    public AddRecruitmentPeriodResponse saveRecruitmentPeriod(AddRecruitmentPeriodRequest request) {
        validateRecruitmentPeriod(request);

        RecruitmentPeriod recruitmentPeriod = conversionService.convert(request);
        recruitmentPeriodRepository.save(recruitmentPeriod);

        return AddRecruitmentPeriodResponse.builder()
                .recruitmentPeriodId(recruitmentPeriod.getId())
                .build();
    }

    public GetRecruitmentPeriodsResponse getRecruitmentPeriods(LocalDate startDate, LocalDate endDate) {
        List<RecruitmentPeriod> recruitmentPeriods = recruitmentPeriodRepository
                .findByStartDateGreaterThanEqualAndEndDateLessThanEqual(startDate, endDate);

        return GetRecruitmentPeriodsResponse.builder()
                .recruitmentPeriods(recruitmentPeriods)
                .build();
    }

    public GetRecruitmentPeriodResponse getRecruitmentPeriod(String id) {
        Optional<RecruitmentPeriod> result = recruitmentPeriodRepository.findById(Long.parseLong(id));
        if (result.isEmpty())
            return null;

        return GetRecruitmentPeriodResponse.builder()
                .recruitmentPeriod(result.get())
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
