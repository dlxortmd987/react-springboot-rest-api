package com.prgms.kdt.danawa.product.domain;

import com.prgms.kdt.danawa.generic.domain.Money;

import java.time.LocalDateTime;

public class Product {
    private final long product;
    private final long sellerId;
    private Category category;
    private Money price;
    private String description;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product(long product, long sellerId, Category category, Money price, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.product = product;
        this.sellerId = sellerId;
        this.category = category;
        this.price = price;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
