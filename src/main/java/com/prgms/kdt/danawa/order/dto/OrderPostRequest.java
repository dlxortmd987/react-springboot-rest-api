package com.prgms.kdt.danawa.order.dto;

import com.prgms.kdt.danawa.generic.domain.Address;
import com.prgms.kdt.danawa.generic.domain.Email;
import com.prgms.kdt.danawa.generic.domain.PostCode;
import com.prgms.kdt.danawa.order.domain.Order;
import com.prgms.kdt.danawa.order.domain.OrderItem;
import com.prgms.kdt.danawa.order.domain.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderPostRequest {

    private long customerId;
    private long sellerId;

    @Size(min = 1, message = "Order items should be at least one.")
    private List<OrderItemDetailsRequest> orderItems = new ArrayList<>();

    @NotNull
    private String email;

    @NotNull
    private String address;

    @NotNull
    private String postcode;

    public OrderPostRequest() {

    }

    public OrderPostRequest(long customerId, long sellerId, List<OrderItemDetailsRequest> orderItems, String email, String address, String postcode) {
        this.customerId = customerId;
        this.sellerId = sellerId;
        this.orderItems.addAll(orderItems);
        this.email = email;
        this.address = address;
        this.postcode = postcode;
    }

    public Order toOrder() {
        return new Order(
                this.customerId,
                this.sellerId,
                this.orderItems.stream().map(OrderItemDetailsRequest::toOrderItem).toList(),
                new Email(this.email),
                new Address(this.address),
                new PostCode(this.postcode),
                OrderStatus.ORDERED,
                LocalDateTime.now()
        );
    }

    public long getCustomerId() {
        return customerId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public List<OrderItemDetailsRequest> getOrderItems() {
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
}
