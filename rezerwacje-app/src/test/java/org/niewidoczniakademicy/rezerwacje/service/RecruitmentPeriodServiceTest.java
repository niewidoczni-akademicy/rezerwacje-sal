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
        AddRecruitmentPeriodRequest request = AddRecruitmentPeriodRequest.builder()
                .startDate(LocalDate.parse("2020-04-28"))
                .endDate(LocalDate.parse("2020-05-04"))
                .build();

        AddRecruitmentPeriodResponse addingResponse = recruitmentPeriodService.saveRecruitmentPeriod(request);
        String recruitmentPeriodId = addingResponse.getRecruitmentPeriodId().toString();
        GetRecruitmentPeriodResponse gettingResponse = recruitmentPeriodService
                .getRecruitmentPeriod(recruitmentPeriodId);
        RecruitmentPeriod result = gettingResponse.getRecruitmentPeriod();

        assertEquals(LocalDate.parse("2020-04-28"), result.getStartDate());
        assertEquals(LocalDate.parse("2020-05-04"), result.getEndDate());
    }

    @Test
    public void shouldFindTwoRecruitmentPeriods_whenTheyExistInDatabase() {
        GetRecruitmentPeriodsResponse response = recruitmentPeriodService
                .getRecruitmentPeriods(LocalDate.parse("2020-04-01"), LocalDate.parse("2020-04-30"));

        assertEquals(2, response.getRecruitmentPeriods().size());
    }

    @Test
    public void shouldThrowRecruitmentPeriodEndDateBeforeStartDateException_whenEndDateBeforeStartDate() {
        AddRecruitmentPeriodRequest request = AddRecruitmentPeriodRequest.builder()
                .startDate(LocalDate.parse("2020-04-02"))
                .endDate(LocalDate.parse("2020-04-01"))
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
