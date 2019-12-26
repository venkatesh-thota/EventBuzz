package com.stackroute.exceptions;

public class CityAlreadyExistException extends Exception {
    public String message;

    public CityAlreadyExistException(String message) {
        super(message);
    }
}
