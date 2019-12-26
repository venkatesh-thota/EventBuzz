package com.stackroute.exception;

public class NoSuchUserException extends Exception {
    private String message;

    public NoSuchUserException(String message) {
        super(message);
        this.message = message;
    }
}
