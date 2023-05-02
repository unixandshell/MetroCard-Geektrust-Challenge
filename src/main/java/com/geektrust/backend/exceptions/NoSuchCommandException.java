package com.geektrust.backend.exceptions;

public class NoSuchCommandException extends RuntimeException {
    public NoSuchCommandException(String message) {
        super(message);
    }

    public NoSuchCommandException() {
        super();
    }
}
