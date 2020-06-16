package org.niewidoczniakademicy.rezerwacje.service.exception;

public class ConnectionNotFoundException extends RuntimeException {

    public ConnectionNotFoundException(final String message) {
        super(message);
    }
}
