package com.prgms.kdt.danawa.product.repository;

import com.prgms.kdt.danawa.generic.domain.Money;
import com.prgms.kdt.danawa.product.domain.Category;
import com.prgms.kdt.danawa.product.domain.Product;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.prgms.kdt.danawa.generic.utils.JdbcUtils.toLocalDateTime;

@Repository
public class ProductJdbcRepository implements ProductRepository {

    private static final Logger log = LoggerFactory.getLogger(ProductJdbcRepository.class);
    private static final int UPDATED_SIZE = 1;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (resultSet, i) -> {

        long productId = resultSet.getLong("product_id");
        long sellerId = resultSet.getLong("seller_id");
        Category category = Category.valueOf(resultSet.getString("category"));
        Money price = Money.of(resultSet.getLong("price"));
        String description = resultSet.getString("description");
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));

        return new Product(productId, sellerId, category, price, description, createdAt, updatedAt);
    };

    private Map<String, Object> toParamMap(@Valid Product product) {
        return Map.of(
                "product_id", product.getProductId(),
                "seller_id", product.getSellerId(),
                "category", product.getCategory().toString(),
                "price", product.getPrice().getAmount(),
                "description", product.getDescription(),
                "created_at", product.getCreatedAt(),
                "updated_at", product.getUpdatedAt()

        );
    }


    public ProductJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM product LIMIT 20",
                Collections.emptyMap(),
                PRODUCT_ROW_MAPPER
        );
    }

    @Override
    public Optional<Product> findById(long productId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM product WHERE product_id = :productId",
                    Collections.singletonMap("productId", productId),
                    PRODUCT_ROW_MAPPER
            ));
        } catch (EmptyResultDataAccessException exception) {
            log.info("Not found Product. [product id]: " + productId);
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findProductsByUserId(long userId) {
        return jdbcTemplate.query("SELECT * FROM product WHERE seller_id = :sellerId",
                Collections.singletonMap("sellerId", userId),
                PRODUCT_ROW_MAPPER
        );
    }

    @Override
    public Product insert(Product product) {
        int update = jdbcTemplate.update(
                "INSERT INTO product(seller_id, category, price, `description`, created_at, updated_at) VALUES (:seller_id, :category, :price, :description, :created_at, :updated_at)",
                toParamMap(product));
        if (update != UPDATED_SIZE) {
            throw new IncorrectResultSizeDataAccessException(UPDATED_SIZE, update);
        }

        Long generatedId = jdbcTemplate.queryForObject("SELECT last_insert_id()", Collections.emptyMap(), Long.class);
        return new Product(generatedId, product.getSellerId(), product.getCategory(), product.getPrice(), product.getDescription(), product.getCreatedAt(), product.getUpdatedAt());
    }

    @Override
    public Product update(Product product) {
        int update = jdbcTemplate.update(
                "UPDATE product SET seller_id = :seller_id, category = :category, price = :price, description = :description, created_at = :created_at, updated_at = :updated_at" +
                        " WHERE product_id = :product_id",
                toParamMap(product)
        );
        if (update != UPDATED_SIZE) {
            throw new IncorrectResultSizeDataAccessException(UPDATED_SIZE, update);
        }
        return new Product(product.getProductId(), product.getSellerId(), product.getCategory(), product.getPrice(), product.getDescription(), product.getCreatedAt(), product.getUpdatedAt());
    }

    @Override
    public void delete(long productId) {
        int update = jdbcTemplate.update(
                "DELETE FROM product WHERE product_id = :product_id;", Collections.singletonMap("product_id", productId));
        if (update != UPDATED_SIZE) {
            throw new IncorrectResultSizeDataAccessException(UPDATED_SIZE, update);
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(
                "TRUNCATE TABLE product", Collections.emptyMap()
        );
    }
}
