package com.stackroute.exceptions;

public class MovieNotFoundException extends Exception {
    public String message;

    public MovieNotFoundException(String message) {
        super(message);
    }
}
