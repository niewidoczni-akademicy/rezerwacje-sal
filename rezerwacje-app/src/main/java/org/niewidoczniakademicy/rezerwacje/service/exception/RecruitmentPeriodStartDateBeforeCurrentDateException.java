package org.niewidoczniakademicy.rezerwacje.service.exception;

public class RecruitmentPeriodStartDateBeforeCurrentDateException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Recruitment period start date cannot be before current date.";

    public RecruitmentPeriodStartDateBeforeCurrentDateException() {
        super(EXCEPTION_MESSAGE);
    }
}
