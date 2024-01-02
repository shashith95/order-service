package com.learning.orderservice.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "ORDER", catalog = "postgres")
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ORDER_ID", nullable = false)
    private Long orderId;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Column(name = "QUANTITY", nullable = false)
    private Long quantity;

    @Column(name = "ORDER_DATE", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "ORDER_STATUS", nullable = false)
    private String orderStatus;

    @Column(name = "TOTAL_AMOUNT", nullable = false)
    private BigDecimal totalAmount;

    public Order() {
    }

    public Order(Long orderId, Long productId, Long quantity, LocalDateTime orderDate, String orderStatus, BigDecimal totalAmount) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return Objects.equals(orderId, order.orderId) && Objects.equals(productId, order.productId) && Objects.equals(quantity, order.quantity) && Objects.equals(orderDate, order.orderDate) && Objects.equals(orderStatus, order.orderStatus) && Objects.equals(totalAmount, order.totalAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, quantity, orderDate, orderStatus, totalAmount);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", orderDate=" + orderDate +
                ", orderStatus='" + orderStatus + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
