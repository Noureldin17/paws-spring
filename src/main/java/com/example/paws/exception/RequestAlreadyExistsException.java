package com.example.paws.exception;

public class RequestAlreadyExistsException extends  RuntimeException{
    public RequestAlreadyExistsException(String message) {
        super(message);
    }

}
