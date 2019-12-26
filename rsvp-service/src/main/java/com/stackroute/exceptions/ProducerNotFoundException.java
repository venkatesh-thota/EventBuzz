package com.stackroute.exceptions;

public class ProducerNotFoundException extends Exception{
    public String message;

    public ProducerNotFoundException(String message) {
        super(message);
    }
}
