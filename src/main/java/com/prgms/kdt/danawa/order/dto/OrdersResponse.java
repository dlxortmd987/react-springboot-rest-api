package com.prgms.kdt.danawa.order.dto;

import com.prgms.kdt.danawa.order.OrderDetailsResponse;

import java.util.ArrayList;
import java.util.List;

public class OrdersResponse {

    private List<OrderDetailsResponse> orders = new ArrayList<>();

    public OrdersResponse(List<OrderDetailsResponse> orders) {
        this.orders.addAll(orders);
    }

    public List<OrderDetailsResponse> getOrders() {
        return orders;
    }
}
