package org.niewidoczniakademicy.rezerwacje.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.niewidoczniakademicy.rezerwacje.model.rest.error.ErrorResponse;
import org.niewidoczniakademicy.rezerwacje.service.exception.InvalidEmailAddressException;
import org.niewidoczniakademicy.rezerwacje.service.exception.UserNotFoundException;
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
    public ErrorResponse handleUserNotFoundException(UserNotFoundException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();

        log.warn(e.getMessage());
        log.warn(Arrays.toString(e.getStackTrace()));

        return errorResponse;
    }

    @ExceptionHandler({InvalidEmailAddressException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidEmailAddressException(InvalidEmailAddressException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();

        log.warn(e.getMessage());
        log.warn(Arrays.toString(e.getStackTrace()));

        return errorResponse;

    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMostSpecificCause().getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();

        log.warn(e.getMessage());
        log.warn(Arrays.toString(e.getStackTrace()));

        return errorResponse;
    }

}
