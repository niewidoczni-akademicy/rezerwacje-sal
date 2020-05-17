package org.niewidoczniakademicy.rezerwacje.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentPeriod;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod.AddRecruitmentPeriodRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod.AddRecruitmentPeriodResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod.GetRecruitmentPeriodResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod.GetRecruitmentPeriodsResponse;
import org.niewidoczniakademicy.rezerwacje.service.converter.ConversionService;
import org.niewidoczniakademicy.rezerwacje.service.exception.RecruitmentPeriodEndDateBeforeStartDateException;
import org.niewidoczniakademicy.rezerwacje.service.exception.RecruitmentPeriodNotFoundException;
import org.niewidoczniakademicy.rezerwacje.service.exception.RecruitmentPeriodStartDateBeforeCurrentDateException;
import org.niewidoczniakademicy.rezerwacje.service.repository.RecruitmentPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class RecruitmentPeriodService {

    private final RecruitmentPeriodRepository recruitmentPeriodRepository;
    private final ConversionService conversionService;

    public AddRecruitmentPeriodResponse saveRecruitmentPeriod(final AddRecruitmentPeriodRequest request) {
        validateRecruitmentPeriod(request);

        RecruitmentPeriod recruitmentPeriod = conversionService.convert(request);
        recruitmentPeriodRepository.save(recruitmentPeriod);

        return AddRecruitmentPeriodResponse.builder()
                .recruitmentPeriodId(recruitmentPeriod.getId())
                .build();
    }

    public GetRecruitmentPeriodsResponse getRecruitmentPeriods(final LocalDate startDate, final LocalDate endDate) {
        List<RecruitmentPeriod> recruitmentPeriods = recruitmentPeriodRepository
                .findByStartDateGreaterThanEqualAndEndDateLessThanEqual(startDate, endDate);

        return GetRecruitmentPeriodsResponse.builder()
                .recruitmentPeriods(recruitmentPeriods)
                .build();
    }

    public GetRecruitmentPeriodResponse getRecruitmentPeriod(final Long id) {
        RecruitmentPeriod recruitmentPeriod = getRecruitmentPeriodFromDatabaseById(id);

        return GetRecruitmentPeriodResponse.builder()
                .recruitmentPeriod(recruitmentPeriod)
                .build();
    }

    public RecruitmentPeriod getRecruitmentPeriodFromDatabaseById(final Long id) {
        return recruitmentPeriodRepository
                .findById(id)
                .orElseThrow(() -> new RecruitmentPeriodNotFoundException("No recruitment period with id " + id));
    }

    private void validateRecruitmentPeriod(final AddRecruitmentPeriodRequest request) {
        validateEndDateNotBeforeStartDate(request);
        validateStartDateNotBeforeCurrentDate(request);
    }

    private void validateEndDateNotBeforeStartDate(final AddRecruitmentPeriodRequest request) {
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        if (endDate.isBefore(startDate)) {
            throw new RecruitmentPeriodEndDateBeforeStartDateException();
        }

    }

    private void validateStartDateNotBeforeCurrentDate(final AddRecruitmentPeriodRequest request) {
        LocalDate startDate = request.getStartDate();
        LocalDate currentDate = LocalDate.now();
        if (startDate.isBefore(currentDate)) {
            throw new RecruitmentPeriodStartDateBeforeCurrentDateException();
        }
    }
}
