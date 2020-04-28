package org.niewidoczniakademicy.rezerwacje.service;

import org.junit.jupiter.api.Test;
import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentPeriod;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod.AddRecruitmentPeriodRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod.AddRecruitmentPeriodResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod.GetRecruitmentPeriodResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod.GetRecruitmentPeriodsResponse;
import org.niewidoczniakademicy.rezerwacje.service.exception.RecruitmentPeriodEndDateBeforeStartDateException;
import org.niewidoczniakademicy.rezerwacje.service.exception.RecruitmentPeriodStartDateBeforeCurrentDateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = "classpath:database/recruitment_period_service_test_inserts.sql")
public class RecruitmentPeriodServiceTest {

    @Autowired
    private RecruitmentPeriodService recruitmentPeriodService;

    @Test
    public void shouldSaveRecruitmentPeriod_andReturnItCorrectly() {
        final LocalDate recruitmentPeriodStartDate = LocalDate.now();
        final LocalDate recruitmentPeriodEndDate = LocalDate.now().plusDays(10);

        AddRecruitmentPeriodRequest request = AddRecruitmentPeriodRequest.builder()
                .startDate(recruitmentPeriodStartDate)
                .endDate(recruitmentPeriodEndDate)
                .build();

        AddRecruitmentPeriodResponse addingResponse = recruitmentPeriodService.saveRecruitmentPeriod(request);
        Long recruitmentPeriodId = addingResponse.getRecruitmentPeriodId();
        GetRecruitmentPeriodResponse gettingResponse = recruitmentPeriodService
                .getRecruitmentPeriod(recruitmentPeriodId);
        RecruitmentPeriod result = gettingResponse.getRecruitmentPeriod();

        assertEquals(recruitmentPeriodStartDate, result.getStartDate());
        assertEquals(recruitmentPeriodEndDate, result.getEndDate());
    }

    @Test
    public void shouldFindTwoRecruitmentPeriods_whenTheyExistInDatabase() {
        final LocalDate filterStartDate = LocalDate.parse("2020-04-01");
        final LocalDate filterEndDate = LocalDate.parse("2020-04-30");
        final int expectedPeriods = 2;

        GetRecruitmentPeriodsResponse response = recruitmentPeriodService
                .getRecruitmentPeriods(filterStartDate, filterEndDate);

        assertEquals(expectedPeriods, response.getRecruitmentPeriods().size());
    }

    @Test
    public void shouldThrowRecruitmentPeriodEndDateBeforeStartDateException_whenEndDateBeforeStartDate() {
        final LocalDate recruitmentPeriodStartDate = LocalDate.now().plusDays(5);
        final LocalDate recruitmentPeriodEndDate = LocalDate.now().plusDays(2);
        AddRecruitmentPeriodRequest request = AddRecruitmentPeriodRequest.builder()
                .startDate(recruitmentPeriodStartDate)
                .endDate(recruitmentPeriodEndDate)
                .build();

        assertThrows(
                RecruitmentPeriodEndDateBeforeStartDateException.class,
                () -> recruitmentPeriodService.saveRecruitmentPeriod(request));
    }

    @Test
    public void shouldThrowRecruitmentPeriodStartDateBeforeCurrentDateException_whenStartDateAfterCurrentDate() {
        AddRecruitmentPeriodRequest request = AddRecruitmentPeriodRequest.builder()
                .startDate(LocalDate.now().minusDays(1))
                .endDate(LocalDate.now().plusDays(1))
                .build();

        assertThrows(
                RecruitmentPeriodStartDateBeforeCurrentDateException.class,
                () -> recruitmentPeriodService.saveRecruitmentPeriod(request));
    }
}
