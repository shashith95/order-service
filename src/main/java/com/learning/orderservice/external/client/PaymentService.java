package com.learning.orderservice.external.client;

import com.learning.commondataservice.model.common.ApiResponse;
import com.learning.orderservice.external.request.PaymentRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service/api/v1/payment")
public interface PaymentService {

    @PostMapping
    ResponseEntity<ApiResponse> doPayment(@Valid @RequestBody PaymentRequest paymentRequest);
}
