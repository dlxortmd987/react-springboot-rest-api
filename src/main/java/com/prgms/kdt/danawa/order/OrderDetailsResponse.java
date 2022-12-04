package com.prgms.kdt.danawa.order;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class OrderDetailsResponse {

    private long orderId;
    private long customerId;
    private long sellerId;

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

    public OrderDetailsResponse(long orderId, long customerId, long sellerId, String email, String address, String postcode, String orderStatus, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.sellerId = sellerId;
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
