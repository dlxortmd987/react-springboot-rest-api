package com.prgms.kdt.danawa.order.domain;

import com.prgms.kdt.danawa.generic.domain.Money;
import com.prgms.kdt.danawa.generic.domain.Quantity;
import com.prgms.kdt.danawa.product.domain.Category;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class OrderItem {
    private final long productId;

    @NotNull
    private final Category category;

    @NotNull
    private final Money price;

    @NotNull
    private final Quantity quantity;

    public OrderItem(long productId, Category category, Money price, Quantity quantity) {
        this.productId = productId;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return productId == orderItem.productId && category == orderItem.category && Objects.equals(price, orderItem.price) && Objects.equals(quantity, orderItem.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, category, price, quantity);
    }

    public long getProductId() {
        return productId;
    }

    public Category getCategory() {
        return category;
    }

    public Money getPrice() {
        return price;
    }

    public Quantity getQuantity() {
        return quantity;
    }
}
