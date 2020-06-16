package org.niewidoczniakademicy.rezerwacje.service.exception;

public class FacultyAlreadyExistsException extends RuntimeException {

    public FacultyAlreadyExistsException(final String message) {
        super(message);
    }
}
