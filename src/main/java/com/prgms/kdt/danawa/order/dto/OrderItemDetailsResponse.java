package com.prgms.kdt.danawa.order.dto;

import com.prgms.kdt.danawa.generic.domain.Money;
import com.prgms.kdt.danawa.generic.domain.Quantity;
import com.prgms.kdt.danawa.order.domain.OrderItem;
import com.prgms.kdt.danawa.product.domain.Category;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class OrderItemDetailsResponse {
    private final long productId;

    @NotNull
    private final String category;

    @NotNull
    private final BigDecimal price;

    @NotNull
    private final int quantity;

    public OrderItemDetailsResponse(long productId, String category, BigDecimal price, int quantity) {
        this.productId = productId;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderItem toOrderItem() {
        return new OrderItem(
                this.productId,
                Category.valueOf(this.category),
                Money.of(this.price),
                new Quantity(this.quantity)
        );
    }

    public long getProductId() {
        return productId;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
