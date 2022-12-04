package com.prgms.kdt.danawa.order.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderItemsResponse {

    private final List<OrderItemDetailsResponse> orderItemDetailsResponses = new ArrayList<>();

    public OrderItemsResponse(List<OrderItemDetailsResponse> orderItemDetailsResponses) {
        this.orderItemDetailsResponses.addAll(orderItemDetailsResponses);
    }

    public List<OrderItemDetailsResponse> getOrderItemDetailsResponses() {
        return orderItemDetailsResponses;
    }
}
