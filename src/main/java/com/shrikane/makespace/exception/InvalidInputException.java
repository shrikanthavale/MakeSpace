package com.shrikane.makespace.exception;

public class InvalidInputException extends Exception {

    public InvalidInputException() {
    }

    public InvalidInputException(final String message) {
        super(message);
    }

    public InvalidInputException(final Exception exception) {
        super(exception);
    }
}
