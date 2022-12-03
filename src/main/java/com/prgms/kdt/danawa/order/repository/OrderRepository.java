package com.prgms.kdt.danawa.order.repository;

import com.prgms.kdt.danawa.order.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    List<Order> findOrdersByCustomerId(long customerId);

    Optional<Order> findById(long orderId);

    Order insert(Order order);

    Order update(Order order);

    void delete(long orderId);

    void deleteAll();
}
