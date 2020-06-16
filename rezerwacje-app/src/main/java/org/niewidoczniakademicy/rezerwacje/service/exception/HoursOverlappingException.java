package org.niewidoczniakademicy.rezerwacje.service.exception;

public class HoursOverlappingException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Hours overlapping";

    public HoursOverlappingException() {
        super(EXCEPTION_MESSAGE);
    }
}
