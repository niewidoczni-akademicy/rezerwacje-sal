package org.niewidoczniakademicy.rezerwacje.service.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final String message) {
        super(message);
    }
}
