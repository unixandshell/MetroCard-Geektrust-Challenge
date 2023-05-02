package com.geektrust.backend.exceptions;

import java.io.IOException;

public class InvalidPassengerException extends IOException {
    public InvalidPassengerException(String message) {
        super(message);
    }

    public InvalidPassengerException() {
        super();
    }
}
