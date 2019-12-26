package com.stackroute.exceptions;

public class EventNotFoundException extends Exception {
    public String message;

    public EventNotFoundException(String message) {
        super(message);
    }
}
