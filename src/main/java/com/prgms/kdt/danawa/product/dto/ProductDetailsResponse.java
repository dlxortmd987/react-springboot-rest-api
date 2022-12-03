package com.prgms.kdt.danawa.product.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDetailsResponse {

    private final long productId;
    private final long sellerId;

    @NotNull
    private final String category;

    @NotNull
    private final BigDecimal price;

    @NotNull
    private final String description;

    @NotNull
    private final LocalDateTime createdAt;

    @NotNull
    private final LocalDateTime updatedAt;

    public ProductDetailsResponse(long productId, long sellerId, String category, BigDecimal price, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.productId = productId;
        this.sellerId = sellerId;
        this.category = category;
        this.price = price;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getProductId() {
        return productId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
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
}
