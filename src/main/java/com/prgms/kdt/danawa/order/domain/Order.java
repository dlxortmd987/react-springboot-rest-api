package com.prgms.kdt.danawa.order.domain;

import com.prgms.kdt.danawa.generic.domain.Address;
import com.prgms.kdt.danawa.generic.domain.Email;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private final long orderId;
    private final long userId;
    private final long productId;
    private final Email email;
    private final Address address;
    private final List<OrderItems> orderItems;
    private OrderStatus orderStatus;
    private final LocalDateTime createdAt;

    public Order(long orderId, long userId, long productId, Email email, Address address, List<OrderItems> orderItems, OrderStatus orderStatus, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.email = email;
        this.address = address;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }
}
