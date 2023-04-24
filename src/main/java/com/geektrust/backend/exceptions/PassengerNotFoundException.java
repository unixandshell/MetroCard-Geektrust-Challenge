package com.geektrust.backend.exceptions;

import java.io.IOException;

public class PassengerNotFoundException extends IOException {
    public PassengerNotFoundException(String message) {
        super(message);
    }

    public PassengerNotFoundException() {
        super();
    }
}
