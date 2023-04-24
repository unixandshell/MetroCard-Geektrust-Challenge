package com.geektrust.backend.exceptions;

import java.io.IOException;

public class NoSuchCommandException extends IOException {
    public NoSuchCommandException(String message) {
        super(message);
    }

    public NoSuchCommandException() {
        super();
    }
}
