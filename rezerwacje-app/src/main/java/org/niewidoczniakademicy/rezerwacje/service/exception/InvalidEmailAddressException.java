package org.niewidoczniakademicy.rezerwacje.service.exception;

public class InvalidEmailAddressException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Invalid email address";

    public InvalidEmailAddressException() {
        super(EXCEPTION_MESSAGE);
    }
}
