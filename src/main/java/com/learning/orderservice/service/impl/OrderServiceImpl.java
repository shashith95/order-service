package com.learning.orderservice.service.impl;

import com.learning.orderservice.exception.DataNotFoundException;
import com.learning.orderservice.mapper.EntityDtoMapper;
import com.learning.orderservice.model.entity.Order;
import com.learning.orderservice.model.request.OrderRequest;
import com.learning.orderservice.repository.OrderRepository;
import com.learning.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderRepository orderRepository;
    private final EntityDtoMapper entityDtoMapper;

    public OrderServiceImpl(OrderRepository orderRepository, EntityDtoMapper entityDtoMapper) {
        this.orderRepository = orderRepository;
        this.entityDtoMapper = entityDtoMapper;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("No order found for id: " + orderId, "1000"));
    }

    @Override
    public Order placeOrder(OrderRequest orderRequest) {
        Order savedOrder = orderRepository.save(entityDtoMapper.orderDtoToEntity(orderRequest));
        logger.info("Order saved successfully with ID: {}", savedOrder.getOrderId());

        return savedOrder;
    }

    @Override
    public Page<Order> getAllOrders(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size));
    }
}
