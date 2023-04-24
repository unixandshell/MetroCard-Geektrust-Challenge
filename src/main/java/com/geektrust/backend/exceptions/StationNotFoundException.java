package com.geektrust.backend.exceptions;

import java.io.IOException;

public class StationNotFoundException extends IOException {
    public StationNotFoundException(String message) {
        super(message);
    }

    public StationNotFoundException() {
        super();
    }
}
