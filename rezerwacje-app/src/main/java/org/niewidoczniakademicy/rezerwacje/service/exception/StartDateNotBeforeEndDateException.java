package org.niewidoczniakademicy.rezerwacje.service.exception;

public class StartDateNotBeforeEndDateException extends RuntimeException {

    public StartDateNotBeforeEndDateException(final String message) {
        super(message);
    }
}
