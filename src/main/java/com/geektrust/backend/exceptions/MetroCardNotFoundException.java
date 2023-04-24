package com.geektrust.backend.exceptions;

import java.io.IOException;

public class MetroCardNotFoundException extends IOException {
    public MetroCardNotFoundException(String message) {
        super(message);
    }

    public MetroCardNotFoundException() {
        super();
    }
}
