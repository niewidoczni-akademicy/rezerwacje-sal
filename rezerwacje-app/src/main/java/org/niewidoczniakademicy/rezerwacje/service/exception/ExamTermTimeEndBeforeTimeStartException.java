package org.niewidoczniakademicy.rezerwacje.service.exception;

public class ExamTermTimeEndBeforeTimeStartException extends RuntimeException {

    public ExamTermTimeEndBeforeTimeStartException(final String message) {
        super(message);
    }
}
