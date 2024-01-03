package com.learning.orderservice.external.client;

import com.learning.commondataservice.model.common.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service/api/v1/product")
public interface ProductService {

    @PutMapping("update-quantity")
    ResponseEntity<ApiResponse> updateProductQuantity(@Valid @RequestParam @NotNull Long productId,
                                                      @RequestParam @NotNull Long quantity);
}
