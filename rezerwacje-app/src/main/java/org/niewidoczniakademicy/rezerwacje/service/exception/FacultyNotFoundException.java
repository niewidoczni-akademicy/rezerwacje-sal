package org.niewidoczniakademicy.rezerwacje.service.exception;

public class FacultyNotFoundException extends RuntimeException {

    public FacultyNotFoundException(final String message) {
        super(message);
    }
}
