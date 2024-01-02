package com.learning.orderservice.controller;


import com.learning.orderservice.mapper.EntityDtoMapper;
import com.learning.orderservice.model.common.ApiResponse;
import com.learning.orderservice.model.entity.Order;
import com.learning.orderservice.model.request.OrderRequest;
import com.learning.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.learning.orderservice.util.ResponseHandler.generateResponse;


@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final EntityDtoMapper entityDtoMapper;

    public OrderController(OrderService orderService,
                           EntityDtoMapper entityDtoMapper) {
        this.orderService = orderService;
        this.entityDtoMapper = entityDtoMapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getOrderById(@RequestParam Long orderId) {
        logger.info("Get order by ID: {} API triggered", orderId);
        Order order = orderService.getOrderById(orderId);

        return generateResponse(HttpStatus.OK, "Order Returned Successfully", "100",
                entityDtoMapper.orderEntityToDto(order));
    }

    @GetMapping("all-orders")
    public ResponseEntity<ApiResponse> getAllOrders(@RequestParam(defaultValue = "0") Integer page,
                                                    @RequestParam(defaultValue = "1000") Integer resultSize) {
        logger.info("Get all orders API triggered");
        Page<Order> orderList = orderService.getAllOrders(page, resultSize);

        return generateResponse(HttpStatus.OK, "Orders Returned Successfully", "100",
                entityDtoMapper.orderEntityListToDtoList(orderList));
    }

    @RequestMapping(value = "place-order", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<ApiResponse> placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
        logger.info("Save or Update employer API triggered with request body: {}", orderRequest);
        Order order = orderService.placeOrder(orderRequest);

        return generateResponse(orderRequest.orderId().isEmpty() ? HttpStatus.CREATED : HttpStatus.OK,
                "Order " + (orderRequest.orderId().isEmpty() ? "Created" : "Updated") + " Successfully",
                "100",
                entityDtoMapper.orderEntityToDto(order));
    }
}
