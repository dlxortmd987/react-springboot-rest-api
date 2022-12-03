package com.prgms.kdt.danawa.product;

import com.prgms.kdt.danawa.exception.EmptyResultException;
import com.prgms.kdt.danawa.product.domain.Product;
import com.prgms.kdt.danawa.product.dto.ProductDetailsRequest;
import com.prgms.kdt.danawa.product.repository.ProductRepository;
import com.prgms.kdt.danawa.product.dto.ProductDetailsResponse;
import com.prgms.kdt.danawa.product.dto.ProductsResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductsResponse showProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductDetailsResponse> productDetailsResponses = products.stream()
                .map(product -> new ProductDetailsResponse(
                        product.getProductId(),
                        product.getSellerId(),
                        product.getCategory().name(),
                        product.getPrice().getAmount(),
                        product.getDescription(),
                        product.getCreatedAt(),
                        product.getUpdatedAt()
                ))
                .toList();

        return new ProductsResponse(productDetailsResponses);
    }

    public ProductDetailsResponse showProductDetails(long productId) {
        Optional<Product> maybeProduct = productRepository.findById(productId);

        if (maybeProduct.isEmpty()) {
            throw new EmptyResultException(String.format("Failed to find the Product. [Product ID]: %d", productId));
        }

        Product foundProduct = maybeProduct.get();
        return new ProductDetailsResponse(
                foundProduct.getProductId(),
                foundProduct.getSellerId(),
                foundProduct.getCategory().name(),
                foundProduct.getPrice().getAmount(),
                foundProduct.getDescription(),
                foundProduct.getCreatedAt(),
                foundProduct.getUpdatedAt()
        );
    }

    public void modifyProduct(ProductDetailsRequest productDetailsRequest) {
        productRepository.update(productDetailsRequest.toProduct());
    }

    public void deleteProduct(long productId) {
        productRepository.delete(productId);
    }
}
