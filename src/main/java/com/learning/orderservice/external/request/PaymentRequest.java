package com.learning.orderservice.external.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.Optional;

public record PaymentRequest(Optional<Long> paymentId,
                             @NotNull Long orderId,
                             @NotBlank String referenceNumber,
                             @NotBlank String paymentMode,
                             @NotNull @PositiveOrZero BigDecimal amount) {
}
