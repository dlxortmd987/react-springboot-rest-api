package com.prgms.kdt.danawa.product.domain;

import com.prgms.kdt.danawa.generic.domain.Money;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

public class Product {
    private final long productId;
    private final long sellerId;

    @NotNull
    private Category category;

    @NotNull
    private Money price;

    @NotNull
    private String description;

    @NotNull
    private final LocalDateTime createdAt;

    @NotNull
    private LocalDateTime updatedAt;

    public Product(long productId, long sellerId, Category category, Money price, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.productId = productId;
        this.sellerId = sellerId;
        this.category = category;
        this.price = price;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Product(long sellerId, Category category, Money price, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(0, sellerId, category, price, description, createdAt, updatedAt);
    }

    public long getProductId() {
        return productId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public Category getCategory() {
        return category;
    }

    public Money getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void changePrice(long price) {
        this.price = Money.of(price);
    }

    public void changePrice(double price) {
        this.price = Money.of(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId && sellerId == product.sellerId && category == product.category && Objects.equals(price, product.price) && Objects.equals(description, product.description) && Objects.equals(createdAt, product.createdAt) && Objects.equals(updatedAt, product.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, sellerId, category, price, description, createdAt, updatedAt);
    }
}
