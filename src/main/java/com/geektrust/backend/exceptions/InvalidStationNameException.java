package com.geektrust.backend.exceptions;

import java.io.IOException;

public class InvalidStationNameException extends IOException {
    public InvalidStationNameException(String message) {
        super(message);
    }

    public InvalidStationNameException() {
        super();
    }
}
