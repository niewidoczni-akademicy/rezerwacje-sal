package org.niewidoczniakademicy.rezerwacje.service.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
