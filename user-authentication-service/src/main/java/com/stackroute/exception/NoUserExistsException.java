package com.stackroute.exception;

public class NoUserExistsException extends Exception {
    String message;

    public NoUserExistsException(String message) {
        this.message = message;
    }

    public NoUserExistsException(String message, String message1) {
        super(message);
        this.message = message1;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
