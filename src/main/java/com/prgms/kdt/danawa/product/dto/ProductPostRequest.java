package com.prgms.kdt.danawa.product.dto;

import com.prgms.kdt.danawa.generic.domain.Money;
import com.prgms.kdt.danawa.product.domain.Category;
import com.prgms.kdt.danawa.product.domain.Product;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductPostRequest {

    private long sellerId;

    @NotNull
    private String category;

    @NotNull
    private BigDecimal price;

    @NotNull
    private String description;

    public ProductPostRequest() {

    }

    public ProductPostRequest(long sellerId, String category, long price, String description) {
        this.sellerId = sellerId;
        this.category = category;
        this.price = BigDecimal.valueOf(price);
        this.description = description;
    }

    public ProductPostRequest(long sellerId, String category, double price, String description) {
        this.sellerId = sellerId;
        this.category = category;
        this.price = BigDecimal.valueOf(price);
        this.description = description;
    }

    public Product toProduct() {
        return new Product(
                this.sellerId,
                Category.valueOf(this.category),
                Money.of(this.price),
                this.description,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
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
}
