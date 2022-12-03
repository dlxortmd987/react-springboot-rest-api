package com.prgms.kdt.danawa.order.repository;

import com.prgms.kdt.danawa.generic.domain.Address;
import com.prgms.kdt.danawa.generic.domain.Email;
import com.prgms.kdt.danawa.generic.domain.PostCode;
import com.prgms.kdt.danawa.order.domain.Order;
import com.prgms.kdt.danawa.order.domain.OrderStatus;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.prgms.kdt.danawa.generic.utils.JdbcUtils.toLocalDateTime;

@Repository
public class OrderJdbcRepository implements OrderRepository {

    private static final Logger log = LoggerFactory.getLogger(OrderJdbcRepository.class);
    private static final int UPDATED_SIZE = 1;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Order> ORDER_ROW_MAPPER = (resultSet, i) -> {
        long orderId = resultSet.getLong("order_id");
        long customerId = resultSet.getLong("customer_id");
        long sellerId = resultSet.getLong("seller_id");
        Email email = new Email(resultSet.getString("email"));
        Address address = new Address(resultSet.getString("address"));
        PostCode postcode = new PostCode(resultSet.getString("postcode"));
        OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString("order_status"));
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));

        return new Order(
                orderId,
                customerId,
                sellerId,
                email,
                address,
                postcode,
                orderStatus,
                createdAt
        );
    };

    private Map<String, Object> toParamMap(@Valid Order order) {
        return Map.of(
                "order_id", order.getOrderId(),
                "customer_id", order.getCustomerId(),
                "seller_id", order.getSellerId(),
                "email", order.getEmail().getValue(),
                "address", order.getAddress().getValue(),
                "postcode", order.getPostcode().getNumber(),
                "order_status", order.getOrderStatus().toString(),
                "created_at", order.getCreatedAt()
        );
    }

    public OrderJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Order> findOrdersByCustomerId(long customerId) {
        return jdbcTemplate.query("SELECT * FROM `order` WHERE customer_id = :customerId",
                Collections.singletonMap("customerId", customerId),
                ORDER_ROW_MAPPER
        );
    }

    @Override
    public Optional<Order> findById(long orderId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM `order` WHERE order_id = :orderId",
                    Collections.singletonMap("orderId", orderId),
                    ORDER_ROW_MAPPER
            ));
        } catch (EmptyResultDataAccessException exception) {
            log.info("Not found Order. [order id]: " + orderId);
            return Optional.empty();
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Order insert(Order order) {
        int update = jdbcTemplate.update(
                "INSERT INTO `order`(customer_id, seller_id, email, address, postcode, order_status, created_at) VALUES (:customer_id, :seller_id, :email, :address, :postcode, :order_status, :created_at)",
                toParamMap(order));
        if (update != UPDATED_SIZE) {
            throw new IncorrectResultSizeDataAccessException(UPDATED_SIZE, update);
        }

        Long generatedId = jdbcTemplate.queryForObject("SELECT last_insert_id()", Collections.emptyMap(), Long.class);
        return new Order(generatedId, order.getCustomerId(), order.getSellerId(), order.getEmail(), order.getAddress(), order.getPostcode(), order.getOrderStatus(), order.getCreatedAt());
    }


    @Override
    public Order update(Order order) {
        int update = jdbcTemplate.update(
                        "UPDATE `order` SET customer_id = :customer_id, seller_id = :seller_id, email = :email, address = :address, postcode = :postcode, order_status = :order_status, created_at = :created_at" +
                        " WHERE order_id = :order_id",
                toParamMap(order));
        if (update != UPDATED_SIZE) {
            throw new IncorrectResultSizeDataAccessException(UPDATED_SIZE, update);
        }
        return new Order(order.getOrderId(), order.getCustomerId(), order.getSellerId(), order.getEmail(), order.getAddress(), order.getPostcode(), order.getOrderStatus(), order.getCreatedAt());
    }

    @Override
    public void delete(long orderId) {
        int update = jdbcTemplate.update(
                "DELETE FROM `order` WHERE order_id = :orderId;", Collections.singletonMap("orderId", orderId));
        if (update != UPDATED_SIZE) {
            throw new IncorrectResultSizeDataAccessException(UPDATED_SIZE, update);
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(
                "TRUNCATE TABLE `order`", Collections.emptyMap());
    }

}
