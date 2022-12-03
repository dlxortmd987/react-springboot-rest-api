package com.prgms.kdt.danawa.product.repository;

import com.prgms.kdt.danawa.JdbcTestConfig;
import com.prgms.kdt.danawa.generic.domain.Money;
import com.prgms.kdt.danawa.product.domain.Category;
import com.prgms.kdt.danawa.product.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(JdbcTestConfig.class)
@Sql(scripts = "classpath:schema.sql")
class ProductJdbcRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @DisplayName("제품들을 조회할 수 있다.")
    @Test
    void findAll() {
        // given
        Product product1 = new Product(1, Category.CELLPHONE, Money.of(100000L), "갤럭시탭", LocalDateTime.now(), LocalDateTime.now());
        Product product2 = new Product(1, Category.COMPUTER, Money.of(123120000L), "맥북", LocalDateTime.now(), LocalDateTime.now());
        Product insertedProduct1 = productRepository.insert(product1);
        Product insertedProduct2 = productRepository.insert(product2);

        // when
        List<Product> products = productRepository.findAll();

        // then
        assertThat(List.of(insertedProduct1, insertedProduct2))
                .isEqualTo(products);

    }

    @DisplayName("제품 아이디로 제품을 조회할 수 있다.")
    @Test
    void findById() {
        // given
        Product product = new Product(1, Category.CELLPHONE, Money.of(100000L), "갤럭시탭", LocalDateTime.now(), LocalDateTime.now());
        Product insertedProduct = productRepository.insert(product);

        // when
        Optional<Product> maybeProduct = productRepository.findById(insertedProduct.getProductId());

        // then
        assertThat(maybeProduct.get())
                .isEqualTo(insertedProduct);
    }

    @DisplayName("판매자의 제품 목록을 조회할 수 있다.")
    @Test
    void findProductsByUserId() {
        // given
        Product product1 = new Product(1, Category.CELLPHONE, Money.of(100000L), "갤럭시탭", LocalDateTime.now(), LocalDateTime.now());
        Product product2 = new Product(1, Category.COMPUTER, Money.of(123120000L), "맥북", LocalDateTime.now(), LocalDateTime.now());
        Product product3 = new Product(2, Category.COMPUTER, Money.of(123120000L), "맥북", LocalDateTime.now(), LocalDateTime.now());
        Product insertedProduct1 = productRepository.insert(product1);
        Product insertedProduct2 = productRepository.insert(product2);
        Product insertedProduct3 = productRepository.insert(product3);

        // when
        List<Product> products = productRepository.findProductsBySellerId(1);

        // then
        assertThat(List.of(insertedProduct1, insertedProduct2))
                .isEqualTo(products);

    }

    @DisplayName("제품을 등록할 수 있다.")
    @Test
    void insert() {
        // given
        Product product = new Product(1, Category.CELLPHONE, Money.of(100000L), "갤럭시탭", LocalDateTime.now(), LocalDateTime.now());

        // when
        Product insertedProduct = productRepository.insert(product);

        Optional<Product> maybeProduct = productRepository.findById(insertedProduct.getProductId());

        // then
        assertThat(maybeProduct.get())
                .isEqualTo(insertedProduct);
    }

    @DisplayName("제품을 수정할 수 있다.")
    @Test
    void update() {
        // given
        Product product = new Product(1, Category.CELLPHONE, Money.of(100000L), "갤럭시탭", LocalDateTime.now(), LocalDateTime.now());
        Product insertedProduct = productRepository.insert(product);

        // when
        insertedProduct.changePrice(200000L);
        Product updatedProduct = productRepository.update(insertedProduct);

        // then
        assertThat(updatedProduct.getPrice())
                .isEqualTo(Money.of(200000L));
    }

    @DisplayName("제품을 삭제할 수 있다.")
    @Test
    void delete() {
        // given
        Product product = new Product(1, Category.CELLPHONE, Money.of(100000L), "갤럭시탭", LocalDateTime.now(), LocalDateTime.now());
        Product insertedProduct = productRepository.insert(product);

        // when
        productRepository.delete(insertedProduct.getProductId());
        Optional<Product> maybeProduct = productRepository.findById(insertedProduct.getProductId());

        // then
        assertThat(maybeProduct)
                .isEqualTo(Optional.empty());
    }
}