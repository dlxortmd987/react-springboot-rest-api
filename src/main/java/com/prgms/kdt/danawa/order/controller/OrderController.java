package com.prgms.kdt.danawa.order.controller;

import com.prgms.kdt.danawa.order.OrderDetailsResponse;
import com.prgms.kdt.danawa.order.OrderService;
import com.prgms.kdt.danawa.order.dto.OrderPostRequest;
import com.prgms.kdt.danawa.order.dto.OrdersResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<OrdersResponse> getCustomerOrders(@PathVariable(value = "customerId") long customerId) {
        return ResponseEntity.ok(orderService.showOrders(customerId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailsResponse> getOrderDetails(@PathVariable(value = "orderId") long orderId) {
        return ResponseEntity.ok(orderService.showOrderDetails(orderId));
    }

    @PostMapping()
    public ResponseEntity<String> placeOrder(@RequestBody @Valid OrderPostRequest orderPostRequest) {
        orderService.saveOrder(orderPostRequest);
        return ResponseEntity.ok("Order Placed!");
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable(value = "orderId") long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order cancelled!");
    }

    @PatchMapping("/{orderId}/pay")
    public ResponseEntity<String> payOrder(@PathVariable(value = "orderId") long orderId) {
        orderService.payOrder(orderId);
        return ResponseEntity.ok("Order payed!");
    }

    @PatchMapping("/{orderId}/deliver")
    public ResponseEntity<String> deliverOrder(@PathVariable(value = "orderId") long orderId) {
        orderService.deliverOrder(orderId);
        return ResponseEntity.ok("Order delivered!");
    }
}
