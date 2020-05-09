package org.niewidoczniakademicy.rezerwacje.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.niewidoczniakademicy.rezerwacje.model.rest.error.ErrorResponse;
import org.niewidoczniakademicy.rezerwacje.service.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

@Slf4j
@ControllerAdvice
public final class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(final UserNotFoundException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .build();

        log.warn(e.getMessage());
        log.warn(Arrays.toString(e.getStackTrace()));

        return errorResponse;
    }

    @ExceptionHandler({InvalidEmailAddressException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidEmailAddressException(final InvalidEmailAddressException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .build();

        log.warn(e.getMessage());
        log.warn(Arrays.toString(e.getStackTrace()));

        return errorResponse;

    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDataIntegrityViolationException(final DataIntegrityViolationException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMostSpecificCause().getMessage())
                .build();

        log.warn(e.getMessage());
        log.warn(Arrays.toString(e.getStackTrace()));

        return errorResponse;
    }

    @ExceptionHandler({ExamTermNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse examTermNotFoundException(final ExamTermNotFoundException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .build();

        log.warn(e.getMessage());
        log.warn(Arrays.toString(e.getStackTrace()));

        return errorResponse;
    }

    @ExceptionHandler({RoomNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse roomNotFoundException(final RoomNotFoundException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .build();

        log.warn(e.getMessage());
        log.warn(Arrays.toString(e.getStackTrace()));

        return errorResponse;
    }

    @ExceptionHandler({CourseOfStudyNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse courseOfStudyNotFoundException(final CourseOfStudyNotFoundException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .build();

        log.warn(e.getMessage());
        log.warn(Arrays.toString(e.getStackTrace()));

        return errorResponse;
    }

    @ExceptionHandler({ExamTermTimeEndBeforeTimeStartException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse examTermTimeEndBeforeTimeStart(final ExamTermTimeEndBeforeTimeStartException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .build();

        log.warn(e.getMessage());
        log.warn(Arrays.toString(e.getStackTrace()));

        return errorResponse;
    }

    @ExceptionHandler({RecruitmentPeriodStartDateBeforeCurrentDateException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse recruitmentPeriodStartDateBeforeCurrentDateException(
            final RecruitmentPeriodStartDateBeforeCurrentDateException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .build();

        log.warn(e.getMessage());
        log.warn(Arrays.toString(e.getStackTrace()));

        return errorResponse;
    }

    @ExceptionHandler({RecruitmentPeriodEndDateBeforeStartDateException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse recruitmentPeriodEndDateBeforeStartDateException(
            final RecruitmentPeriodEndDateBeforeStartDateException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .build();

        log.warn(e.getMessage());
        log.warn(Arrays.toString(e.getStackTrace()));

        return errorResponse;
    }

    @ExceptionHandler({InvalidInputException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidInputException(final InvalidInputException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .build();

        log.warn(e.getMessage());
        log.warn(Arrays.toString(e.getStackTrace()));

        return errorResponse;
    }

    @ExceptionHandler({RecruitmentPeriodNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse recruitmentPeriodNotFoundException(final RecruitmentPeriodNotFoundException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .build();

        log.warn(e.getMessage());
        log.warn(Arrays.toString(e.getStackTrace()));

        return errorResponse;
    }

}
