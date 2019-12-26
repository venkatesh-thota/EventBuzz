package com.stackroute.exceptions;

public class CityNotFoundException extends Exception {
    public String message;

    public CityNotFoundException(String message) {
        super(message);
    }
}
