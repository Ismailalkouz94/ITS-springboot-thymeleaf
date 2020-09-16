package com.example.its.exception;

public class IssueTrackingException extends RuntimeException {

    public IssueTrackingException() {}

    public IssueTrackingException(String message) {
        super(message);
    }
}
