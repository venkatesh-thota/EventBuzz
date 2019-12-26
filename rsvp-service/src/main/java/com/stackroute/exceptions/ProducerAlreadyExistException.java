package com.stackroute.exceptions;

public class ProducerAlreadyExistException extends Exception {
    public String message;

    public ProducerAlreadyExistException(String message) {
        super(message);
    }
}
