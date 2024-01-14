package com.learning.orderservice.service.impl;

import com.learning.commondataservice.exception.DataNotFoundException;
import com.learning.commondataservice.model.common.ApiResponse;
import com.learning.orderservice.external.client.PaymentService;
import com.learning.orderservice.external.client.ProductService;
import com.learning.orderservice.external.request.PaymentRequest;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

import static com.learning.commondataservice.util.DateTimeUtils.getCurrentDateTimeUtc;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderRepository orderRepository;
    private final EntityDtoMapper entityDtoMapper;
    private final ProductService productService;
    private final PaymentService paymentService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductService productService,
                            EntityDtoMapper entityDtoMapper,
                            PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.entityDtoMapper = entityDtoMapper;
        this.productService = productService;
        this.paymentService = paymentService;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("No order found for id: " + orderId, "1000"));
    }

    @Override
    @Transactional
    public Order placeOrder(OrderRequest orderRequest) {
        logger.info("Update product service quantity for productId: {} with quantity: {}", orderRequest.productId(), orderRequest.quantity());
        productService.updateProductQuantity(orderRequest.productId(), orderRequest.quantity());

        Order savedOrder = orderRepository.save(new Order(null,
                orderRequest.productId(), orderRequest.quantity(),
                getCurrentDateTimeUtc(), "CREATED", BigDecimal.valueOf(1000L)));
        logger.info("Order saved successfully with ID: {}", savedOrder.getOrderId());

        logger.info("Calling payment service to complete the payment");
        ApiResponse paymentResponse = Objects.requireNonNull(paymentService.doPayment(
                        new PaymentRequest(Optional.empty(), savedOrder.getOrderId(),
                                "100-10", "CARD", BigDecimal.valueOf(1000L))))
                .getBody();

        String orderStatus = paymentResponse != null && paymentResponse.data() != null && (Integer) paymentResponse.data() > 0L
                ? "COMPLETED" : "FAILED";

        savedOrder.setOrderStatus(orderStatus);
        orderRepository.save(savedOrder);
        return savedOrder;
    }

    @Override
    public Page<Order> getAllOrders(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size));
    }
}
