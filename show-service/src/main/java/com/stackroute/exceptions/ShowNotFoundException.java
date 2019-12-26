package com.stackroute.exceptions;

public class ShowNotFoundException extends Exception {

    private String mesage;

    public ShowNotFoundException(){

    }

    public ShowNotFoundException(String message){
        super(message);
        this.mesage=message;
    }
}
