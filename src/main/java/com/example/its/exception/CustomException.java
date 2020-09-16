package com.example.its.exception;

public class CustomException extends RuntimeException {

    public CustomException() {}

    public CustomException(String message) {
        super(message);
    }
}
