package com.stackroute.exceptions;

public class DistributorNotFoundException extends Exception {
    public String message;

    public DistributorNotFoundException(String message) {
      super(message);
    }
}
