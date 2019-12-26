package com.stackroute.exceptions;

//                          Exception class for exception when the same Theatre is being added

    public class TheatreAlreadyExistException extends Exception {

        public TheatreAlreadyExistException(String message) {
            super(message);
        }
    }