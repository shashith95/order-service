package com.learning.orderservice.service;

import com.learning.orderservice.model.entity.Order;
import com.learning.orderservice.model.request.OrderRequest;
import org.springframework.data.domain.Page;

public interface OrderService {
    Order getOrderById(Long orderId);

    Order placeOrder(OrderRequest orderRequest);

    Page<Order> getAllOrders(int page, int size);
}
