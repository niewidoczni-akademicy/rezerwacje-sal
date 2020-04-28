package org.niewidoczniakademicy.rezerwacje.service.exception;

public class ExamTermTimeEndBeforeTimeStartException extends RuntimeException {

    public ExamTermTimeEndBeforeTimeStartException(String message) {
        super(message);
    }
}
