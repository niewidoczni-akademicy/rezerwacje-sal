package org.niewidoczniakademicy.rezerwacje.service.exception;

public class CourseOfStudyNotFoundException extends RuntimeException {

    public CourseOfStudyNotFoundException(final String message) {
        super(message);
    }
}
