package com.geektrust.backend.exceptions;

import java.io.IOException;

public class InvalidAmountException extends IOException {
    public InvalidAmountException(String message) {
        super(message);
    }

    public InvalidAmountException() {
        super();
    }
      
}
