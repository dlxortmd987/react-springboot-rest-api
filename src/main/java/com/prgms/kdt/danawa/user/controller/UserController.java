package com.prgms.kdt.danawa.user.controller;

import com.prgms.kdt.danawa.configuration.BaseResponse;
import com.prgms.kdt.danawa.product.ProductService;
import com.prgms.kdt.danawa.product.dto.ProductDetailsRequest;
import com.prgms.kdt.danawa.product.dto.ProductDetailsResponse;
import com.prgms.kdt.danawa.product.dto.ProductsResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final ProductService productService;

    public UserController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public BaseResponse<ProductsResponse> getProducts() {
        return new BaseResponse<>(productService.showProducts());
    }

    @GetMapping("/{productId}")
    public BaseResponse<ProductDetailsResponse> getProduct(@PathVariable(value = "productId") long productId) {
        return new BaseResponse<>(productService.showProductDetails(productId));
    }

    @PatchMapping
    public BaseResponse<String> modifyProduct(@RequestBody ProductDetailsRequest productDetailsRequest) {
        productService.modifyProduct(productDetailsRequest);
        return new BaseResponse<>("Modify Success!");
    }

    @DeleteMapping("/{productId}")
    public BaseResponse<String> deleteProduct(@PathVariable(value = "productId") long productId) {
        productService.deleteProduct(productId);
        return new BaseResponse<>("Delete Success!");
    }
}
