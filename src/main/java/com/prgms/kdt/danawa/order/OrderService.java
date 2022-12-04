package com.prgms.kdt.danawa.order;

import com.prgms.kdt.danawa.exception.EmptyResultException;
import com.prgms.kdt.danawa.order.domain.Order;
import com.prgms.kdt.danawa.order.dto.OrderPostRequest;
import com.prgms.kdt.danawa.order.dto.OrdersResponse;
import com.prgms.kdt.danawa.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void saveOrder(OrderPostRequest orderPostRequest) {
        orderRepository.insert(orderPostRequest.toOrder());
    }

    public OrdersResponse showOrders(long customerId) {
        List<Order> orders = orderRepository.findOrdersByCustomerId(customerId);

        List<OrderDetailsResponse> orderDetailsResponses = orders.stream()
                .map(order -> new OrderDetailsResponse(
                        order.getOrderId(),
                        order.getCustomerId(),
                        order.getSellerId(),
                        order.getEmail().getValue(),
                        order.getAddress().getValue(),
                        order.getPostcode().getNumber(),
                        order.getOrderStatus().toString(),
                        order.getCreatedAt()
                )).toList();

        return new OrdersResponse(orderDetailsResponses);
    }

    public OrderDetailsResponse showOrderDetails(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EmptyResultException(String.format("Failed to find the Order. [Order ID]: %d", orderId)));

        return new OrderDetailsResponse(
                order.getOrderId(),
                order.getCustomerId(),
                order.getSellerId(),
                order.getEmail().getValue(),
                order.getAddress().getValue(),
                order.getPostcode().getNumber(),
                order.getOrderStatus().toString(),
                order.getCreatedAt()
        );
    }

    @Transactional
    public void payOrder(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EmptyResultException(String.format("Failed to find the Order. [Order ID]: %d", orderId)));
        order.pay();
        orderRepository.update(order);
    }

    @Transactional
    public void deliverOrder(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EmptyResultException(String.format("Failed to find the Order. [Order ID]: %d", orderId)));
        order.deliver();
        orderRepository.update(order);
    }

    @Transactional
    public void cancelOrder(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EmptyResultException(String.format("Failed to find the Order. [Order ID]: %d", orderId)));
        order.cancel();
        orderRepository.update(order);
    }
}
