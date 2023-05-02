package com.geektrust.backend.exceptions;

public class PassengerNotFoundException extends RuntimeException {
    public PassengerNotFoundException(String message) {
        super(message);
    }

    public PassengerNotFoundException() {
        super();
    }
}
