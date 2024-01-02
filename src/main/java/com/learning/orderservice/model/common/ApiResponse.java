package com.learning.orderservice.model.common;

import java.util.List;

public record ApiResponse(String message,
                          String messageCode,
                          Object data,
                          List<?> errorList) {
}
