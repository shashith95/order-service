package com.learning.orderservice.exception;

public class DataNotFoundException extends RuntimeException {
    private final String errorCode;

    public DataNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
