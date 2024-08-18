package com.client.ws.ws.exception;


public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }
}