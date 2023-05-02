package com.geektrust.backend.exceptions;

public class InvalidPassengerException extends RuntimeException {
    public InvalidPassengerException(String message) {
        super(message);
    }

    public InvalidPassengerException() {
        super();
    }
}
