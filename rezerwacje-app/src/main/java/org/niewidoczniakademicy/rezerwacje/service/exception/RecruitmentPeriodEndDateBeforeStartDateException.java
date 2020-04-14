package org.niewidoczniakademicy.rezerwacje.service.exception;

public class RecruitmentPeriodEndDateBeforeStartDateException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Recruitment period end date cannot be before start date.";

    public RecruitmentPeriodEndDateBeforeStartDateException() {
        super(EXCEPTION_MESSAGE);
    }
}
