package com.prgms.kdt.danawa.order.domain;

import com.prgms.kdt.danawa.exception.InvalidStatusException;
import com.prgms.kdt.danawa.generic.domain.Address;
import com.prgms.kdt.danawa.generic.domain.Email;
import com.prgms.kdt.danawa.generic.domain.PostCode;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Order {
    private final long orderId;
    private final long customerId;
    private final long sellerId;

    private final List<OrderItem> orderItems = new ArrayList<>();

    @NotNull
    private final Email email;

    @NotNull
    private final Address address;

    @NotNull
    private final PostCode postcode;

    @NotNull
    private OrderStatus orderStatus;

    @NotNull
    private final LocalDateTime createdAt;

    public Order(long orderId, long customerId, long sellerId, List<OrderItem> orderItems, Email email, Address address, PostCode postcode, OrderStatus orderStatus, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.sellerId = sellerId;
        this.orderItems.addAll(orderItems);
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }

    public Order(long customerId, long sellerId, List<OrderItem> orderItems, Email email, Address address, PostCode postcode, OrderStatus orderStatus, LocalDateTime createdAt) {
        this(0, customerId, sellerId, orderItems, email, address, postcode, orderStatus, createdAt);
    }

    public Order(long orderId, long customerId, long sellerId, Email email, Address address, PostCode postcode, OrderStatus orderStatus, LocalDateTime createdAt) {
        this(orderId, customerId, sellerId, Collections.emptyList(), email, address, postcode, orderStatus, createdAt);
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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public PostCode getPostcode() {
        return postcode;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && customerId == order.customerId && sellerId == order.sellerId && Objects.equals(orderItems, order.orderItems) && Objects.equals(email, order.email) && Objects.equals(address, order.address) && Objects.equals(postcode, order.postcode) && orderStatus == order.orderStatus && Objects.equals(createdAt, order.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customerId, sellerId, orderItems, email, address, postcode, orderStatus, createdAt);
    }

    public void pay() {
        if (this.orderStatus != OrderStatus.ORDERED) {
            throw new InvalidStatusException(this.orderStatus.name());
        }
        this.orderStatus = OrderStatus.PAYED;
    }

    public void cancel() {
        if (this.orderStatus != OrderStatus.PAYED) {
            throw new InvalidStatusException(this.orderStatus.name());
        }
        this.orderStatus = OrderStatus.CANCELLED;
    }

    public void deliver() {
        if (this.orderStatus != OrderStatus.PAYED) {
            throw new InvalidStatusException(this.orderStatus.name());
        }
        this.orderStatus = OrderStatus.DELIVERED;
    }

    public void addItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }
}
