package org.niewidoczniakademicy.rezerwacje.service.exception;

public class HoursTimeEndBeforeTimeStartException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Time end before or equal to time start";

    public HoursTimeEndBeforeTimeStartException() {
        super(EXCEPTION_MESSAGE);
    }
}
