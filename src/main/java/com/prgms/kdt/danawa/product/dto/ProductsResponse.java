package com.prgms.kdt.danawa.product.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductsResponse {
     private final List<ProductDetailsResponse> products = new ArrayList<>();

    public ProductsResponse(List<ProductDetailsResponse> products) {
        this.products.addAll(products);
    }

    public List<ProductDetailsResponse> getProducts() {
        return products;
    }
}
