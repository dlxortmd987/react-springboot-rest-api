package com.prgms.kdt.danawa.product.repository;

import com.prgms.kdt.danawa.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();

    Optional<Product> findById(long productId);

    List<Product> findProductsByUserId(long userId);

    Product insert(Product product);

    Product update(Product product);

    void delete(long productId);

    void deleteAll();
}
