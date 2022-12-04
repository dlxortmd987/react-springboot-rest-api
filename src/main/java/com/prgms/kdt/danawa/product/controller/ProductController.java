package com.prgms.kdt.danawa.product.controller;

import com.prgms.kdt.danawa.product.ProductService;
import com.prgms.kdt.danawa.product.dto.ProductDetailsRequest;
import com.prgms.kdt.danawa.product.dto.ProductDetailsResponse;
import com.prgms.kdt.danawa.product.dto.ProductPostRequest;
import com.prgms.kdt.danawa.product.dto.ProductsResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ProductsResponse> getProducts() {
        return ResponseEntity.ok(productService.showProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailsResponse> getProductDetails(@PathVariable(value = "productId") long productId) {
        return ResponseEntity.ok(productService.showProductDetails(productId));
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<ProductsResponse> getSellerProducts(@PathVariable(value = "sellerId") long sellerId) {
        return ResponseEntity.ok(productService.showSellerProducts(sellerId));
    }

    @PostMapping
    public ResponseEntity<String> postProduct(@RequestBody @Valid ProductPostRequest productPostRequest) {
        productService.postProduct(productPostRequest);
        return ResponseEntity.ok("Post Success!");
    }

    @PatchMapping
    public ResponseEntity<String> modifyProduct(@RequestBody @Valid ProductDetailsRequest productDetailsRequest) {
        productService.modifyProduct(productDetailsRequest);
        return ResponseEntity.ok("Modify Success!");
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable(value = "productId") long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Delete Success!");
    }
}
