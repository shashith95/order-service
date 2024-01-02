package com.learning.orderservice.exception;

public class ExternalClientException extends RuntimeException {

    private final String errorCode;

    public ExternalClientException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
