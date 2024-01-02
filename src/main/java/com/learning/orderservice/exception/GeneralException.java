package com.learning.orderservice.exception;

public class GeneralException extends Exception {
    private final String errorCode;

    public GeneralException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
