package com.stackroute.exceptions;

//                    Exception class for exception when the Theatre does not exist and is not found

    public class TheatreNotFoundException extends Exception {

        public TheatreNotFoundException(String message) {
            super(message);
        }
    }
