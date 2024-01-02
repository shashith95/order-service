package com.learning.orderservice.util;


import com.learning.orderservice.exception.DataNotFoundException;
import com.learning.orderservice.exception.GeneralException;
import com.learning.orderservice.model.common.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static com.learning.orderservice.util.ResponseHandler.generateErrorResponse;


@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Exception handler for various types of exceptions, including custom exceptions.
     *
     * @param ex The exception to handle.
     * @return ResponseEntity containing error details.
     */
    @ExceptionHandler({
            ConstraintViolationException.class,
            DataNotFoundException.class,
            NotReadablePropertyException.class,
            Exception.class
    })
    public ResponseEntity<ApiResponse> handleException(Exception ex) {
        logger.error("Exception occurred: {}", ex.getMessage(), ex);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorCode;
        List<String> errorMessages = new ArrayList<>();

        if (ex instanceof ConstraintViolationException cve) {
            status = HttpStatus.BAD_REQUEST;
            errorCode = "REQ_MISSING_REQUIRED_FIELD.name()";
            cve.getConstraintViolations().forEach(
                    constraintViolation -> errorMessages.add(
                            constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage()
                    )
            );
        } else if (ex instanceof DataNotFoundException dataNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            errorCode = dataNotFoundException.getErrorCode();
            errorMessages.add(ex.getMessage());
        } else if (ex instanceof GeneralException generalException) {
            errorCode = generalException.getErrorCode();
            errorMessages.add(ex.getMessage());
        } else if (ex instanceof NotReadablePropertyException) {
            status = HttpStatus.BAD_REQUEST;
            errorCode = " REQ_INVALID_INPUT_FORMAT.getCode()";
            errorMessages.add(ex.getLocalizedMessage());
        } else {
            errorCode = "REQ_MISSING_REQUIRED_FIELD.getCode()";
            errorMessages.add(ex.getMessage());
        }

        return generateExceptionResponse(status, status.getReasonPhrase(), errorCode, errorMessages);
    }

    /**
     * Generates a ResponseEntity containing error details.
     *
     * @param status    The HTTP status code.
     * @param message   The reason phrase.
     * @param errorCode The error code.
     * @param errorList The list of error messages.
     * @return ResponseEntity containing error details.
     */
    private ResponseEntity<ApiResponse> generateExceptionResponse(HttpStatus status, String message, String errorCode, List<String> errorList) {
        logger.error("{} - {}: {}", status, errorCode, errorList);
        return generateErrorResponse(status, message, errorCode, errorList);
    }
}
