package com.prgms.kdt.danawa.order.dto;

import com.prgms.kdt.danawa.generic.domain.Address;
import com.prgms.kdt.danawa.generic.domain.Email;
import com.prgms.kdt.danawa.generic.domain.PostCode;
import com.prgms.kdt.danawa.order.domain.Order;
import com.prgms.kdt.danawa.order.domain.OrderStatus;
import com.prgms.kdt.danawa.order.repository.OrderRepository;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class OrderPostRequest {

    private long customerId;
    private long sellerId;

    @NotNull
    private String email;

    @NotNull
    private String address;

    @NotNull
    private String postcode;

    public OrderPostRequest() {

    }

    public OrderPostRequest(long customerId, long sellerId, String email, String address, String postcode) {
        this.customerId = customerId;
        this.sellerId = sellerId;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
    }

    public Order toOrder() {
        return new Order(
                this.customerId,
                this.sellerId,
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
