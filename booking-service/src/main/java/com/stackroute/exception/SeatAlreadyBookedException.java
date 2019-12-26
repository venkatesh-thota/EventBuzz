package com.stackroute.exception;

public class SeatAlreadyBookedException extends Exception {

        private String message;

        public SeatAlreadyBookedException(){}

        public SeatAlreadyBookedException(String message){
            super(message);
            this.message = message;
        }
    }