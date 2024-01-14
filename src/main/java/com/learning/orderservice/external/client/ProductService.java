package com.learning.orderservice.external.client;

import com.learning.commondataservice.exception.GeneralException;
import com.learning.commondataservice.model.common.ApiResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CircuitBreaker(name = "external-payment", fallbackMethod = "fallback")
@FeignClient(name = "product-service/api/v1/product")
public interface ProductService {

    @PutMapping("update-quantity")
    ResponseEntity<ApiResponse> updateProductQuantity(@Valid @RequestParam @NotNull Long productId,
                                                      @RequestParam @NotNull Long quantity);

    default void fallback(Exception e) throws GeneralException {
        throw new GeneralException("Payment Service is not available", "UNAVAILABLE");
    }
}
