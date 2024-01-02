package com.learning.orderservice.model.response;

import java.math.BigDecimal;

public record OrderResponse(Long orderId,
                            Long productId,
                            Long quantity,
                            String orderDate,
                            String orderStatus,
                            BigDecimal orderAmount) {
}
