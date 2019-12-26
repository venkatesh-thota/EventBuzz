package com.stackroute.exceptions;

public class EventAlreadyExistException extends Exception {
    public String message;

    public EventAlreadyExistException(String message) {
        super(message);
    }
}
