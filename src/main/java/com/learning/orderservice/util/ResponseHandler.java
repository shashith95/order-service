package com.learning.orderservice.util;

import com.learning.orderservice.model.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class ResponseHandler {

    private ResponseHandler() {
    }

    public static ResponseEntity<ApiResponse> generateResponse(HttpStatus httpStatus,
                                                               String responseMessage,
                                                               String responseCode,
                                                               Object data) {

        return ResponseEntity
                .status(httpStatus)
                .body(new ApiResponse(
                        responseMessage,
                        responseCode,
                        data,
                        new ArrayList<>()));
    }

    public static ResponseEntity<ApiResponse> generateErrorResponse(HttpStatus httpStatus,
                                                                    String responseMessage,
                                                                    String responseCode,
                                                                    List<?> errorList) {

        return ResponseEntity
                .status(httpStatus)
                .body(new ApiResponse(
                        responseMessage,
                        responseCode,
                        new ArrayList<>(),
                        errorList));
    }
}
