package com.prgms.kdt.danawa.order;

import com.prgms.kdt.danawa.order.domain.OrderItem;
import com.prgms.kdt.danawa.order.dto.OrderItemDetailsResponse;
import com.prgms.kdt.danawa.order.dto.OrderItemsResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDetailsResponse {

    private long orderId;
    private long customerId;
    private long sellerId;

    @NotEmpty
    private OrderItemsResponse orderItems;

    @NotNull
    private String email;

    @NotNull
    private String address;

    @NotNull
    private String postcode;

    @NotNull
    private String orderStatus;

    @NotNull
    private LocalDateTime createdAt;

    public OrderDetailsResponse(long orderId, long customerId, long sellerId, OrderItemsResponse orderItems, String email, String address, String postcode, String orderStatus, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.sellerId = sellerId;
        this.orderItems = orderItems;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public OrderItemsResponse getOrderItems() {
        return orderItems;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
