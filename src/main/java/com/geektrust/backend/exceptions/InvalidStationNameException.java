package com.geektrust.backend.exceptions;

public class InvalidStationNameException extends RuntimeException {
    public InvalidStationNameException(String message) {
        super(message);
    }

    public InvalidStationNameException() {
        super();
    }
}
