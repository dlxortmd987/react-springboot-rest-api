package com.prgms.kdt.danawa.order.domain;

import com.prgms.kdt.danawa.generic.domain.Address;
import com.prgms.kdt.danawa.generic.domain.Email;
import com.prgms.kdt.danawa.generic.domain.PostCode;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private final long orderId;
    private final long customerId;
    private final long sellerId;
    private final long productId;
    private final Email email;
    private final Address address;
    private final PostCode postcode;
    private final List<OrderItem> orderItems;
    private OrderStatus orderStatus;
    private final LocalDateTime createdAt;

    public Order(long orderId, long customerId, long sellerId, long productId, Email email, Address address, PostCode postcode, List<OrderItem> orderItems, OrderStatus orderStatus, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.sellerId = sellerId;
        this.productId = productId;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }
}
