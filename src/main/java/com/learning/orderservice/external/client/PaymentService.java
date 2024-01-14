package com.learning.orderservice.external.client;

import com.learning.commondataservice.exception.GeneralException;
import com.learning.commondataservice.model.common.ApiResponse;
import com.learning.orderservice.external.request.PaymentRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CircuitBreaker(name = "external-payment", fallbackMethod = "fallback")
@FeignClient(name = "payment-service/api/v1/payment")
public interface PaymentService {

    @PostMapping
    ResponseEntity<ApiResponse> doPayment(@Valid @RequestBody PaymentRequest paymentRequest);

    default void fallback(Exception e) throws GeneralException {
        throw new GeneralException("Payment Service is not available", "UNAVAILABLE");
    }
}
