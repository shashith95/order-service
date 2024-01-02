package com.learning.orderservice.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Optional;

public record OrderRequest(Optional<Long> orderId,
                           @NotNull @Positive Long productId,
                           @NotNull @PositiveOrZero Long quantity,
                           @NotBlank String orderDate,
                           @NotNull PaymentMode paymentMode) {
}
