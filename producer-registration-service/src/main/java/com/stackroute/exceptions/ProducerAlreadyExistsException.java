package com.stackroute.exceptions;

//                          Exception class for exception when the same producer is being added

public class ProducerAlreadyExistsException extends Exception {

    public ProducerAlreadyExistsException(String message) {
        super(message);
    }
}
