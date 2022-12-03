package com.prgms.kdt.danawa.product.dto;

import com.prgms.kdt.danawa.generic.domain.Money;
import com.prgms.kdt.danawa.product.domain.Category;
import com.prgms.kdt.danawa.product.domain.Product;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDetailsRequest {
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

    public ProductDetailsRequest(long productId, long sellerId, String category, BigDecimal price, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.productId = productId;
        this.sellerId = sellerId;
        this.category = category;
        this.price = price;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Product toProduct() {
        return new Product(
                this.productId,
                this.sellerId,
                Category.valueOf(this.category),
                Money.of(this.price),
                this.description,
                this.createdAt,
                this.updatedAt
        );
    }
}
