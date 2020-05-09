package org.niewidoczniakademicy.rezerwacje.service.exception;

public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException(final String message) {
        super(message);
    }
}
