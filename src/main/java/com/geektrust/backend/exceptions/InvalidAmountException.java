package com.geektrust.backend.exceptions;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(String message) {
        super(message);
    }

    public InvalidAmountException() {
        super();
    }    
}
