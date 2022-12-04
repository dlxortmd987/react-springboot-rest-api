package com.prgms.kdt.danawa.order;

import com.prgms.kdt.danawa.exception.EmptyResultException;
import com.prgms.kdt.danawa.order.domain.Order;
import com.prgms.kdt.danawa.order.dto.OrderItemDetailsResponse;
import com.prgms.kdt.danawa.order.dto.OrderItemsResponse;
import com.prgms.kdt.danawa.order.dto.OrderPostRequest;
import com.prgms.kdt.danawa.order.dto.OrdersResponse;
import com.prgms.kdt.danawa.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                        getOrderItems(order),
                        order.getEmail().getValue(),
                        order.getAddress().getValue(),
                        order.getPostcode().getNumber(),
                        order.getOrderStatus().toString(),
                        order.getCreatedAt()
                )).toList();

        return new OrdersResponse(orderDetailsResponses);
    }

    private static OrderItemsResponse getOrderItems(Order order) {
        return new OrderItemsResponse(order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDetailsResponse(
                        orderItem.getProductId(),
                        orderItem.getCategory().name(),
                        orderItem.getPrice().getAmount(),
                        orderItem.getQuantity().getValue()
                )).toList());
    }

    public OrderDetailsResponse showOrderDetails(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EmptyResultException(String.format("Failed to find the Order. [Order ID]: %d", orderId)));

        return new OrderDetailsResponse(
                order.getOrderId(),
                order.getCustomerId(),
                order.getSellerId(),
                getOrderItems(order),
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
