package com.prgms.kdt.danawa.order.repository;

import com.prgms.kdt.danawa.order.domain.Order;
import com.prgms.kdt.danawa.order.domain.OrderItem;
import com.prgms.kdt.danawa.order.utils.OrderJdbcUtils;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static com.prgms.kdt.danawa.order.repository.OrderSql.*;

@Repository
public class OrderJdbcRepository implements OrderRepository {

    private static final Logger log = LoggerFactory.getLogger(OrderJdbcRepository.class);
    private static final int UPDATED_SIZE = 1;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private Map<String, Object> toOrderParamMap(@Valid Order order) {
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

    private MapSqlParameterSource toOrderItemParamMap(OrderItem item, long orderId, LocalDateTime createdAt) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("order_id", orderId);
        source.addValue("product_id", item.getProductId());
        source.addValue("category", item.getCategory().name());
        source.addValue("price", item.getPrice().getAmount().longValue());
        source.addValue("quantity", item.getQuantity().getValue());
        source.addValue("created_at", createdAt);
        return source;
    }


    public OrderJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Order> findOrdersByCustomerId(long customerId) {
        return jdbcTemplate.query(FIND_ORDERS_BY_CUSTOMER_ID.getSql(),
                Collections.singletonMap("customerId", customerId),
                OrderJdbcUtils::getOrders
        );
    }

    @Override
    public Optional<Order> findById(long orderId) {
        try {
            return Optional.ofNullable(jdbcTemplate.query(FIND_BY_ID.getSql(),
                    Collections.singletonMap("orderId", orderId),
                    OrderJdbcUtils::getOrder));
        } catch (EmptyResultDataAccessException exception) {
            log.info("Not found Order. [order id]: " + orderId);
            return Optional.empty();
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Order insert(Order order) {
        long generatedId = insertOrder(order);
        insertOrderItems(order.getOrderItems(), generatedId, order.getCreatedAt());

        return new Order(generatedId, order.getCustomerId(), order.getSellerId(), order.getOrderItems(), order.getEmail(), order.getAddress(), order.getPostcode(), order.getOrderStatus(), order.getCreatedAt());
    }

    private long insertOrder(Order order) {
        int update = jdbcTemplate.update(INSERT_ORDER.getSql(), toOrderParamMap(order));
        if (update != UPDATED_SIZE) {
            throw new IncorrectResultSizeDataAccessException(UPDATED_SIZE, update);
        }

        return Objects.requireNonNull(jdbcTemplate.queryForObject("SELECT last_insert_id()", Collections.emptyMap(), Long.class));
    }

    private void insertOrderItems(List<OrderItem> orderItems, long orderId, LocalDateTime createdAt) {
        List<MapSqlParameterSource> params = new ArrayList<>();

        orderItems.forEach(
                orderItem -> params.add(toOrderItemParamMap(orderItem, orderId, createdAt))
        );

        jdbcTemplate.batchUpdate(
                INSERT_ORDER_ITEMS.getSql(),
                params.toArray(MapSqlParameterSource[]::new)
        );
    }


    @Override
    public Order update(Order order) {
        updateOrder(order);

        return new Order(order.getOrderId(), order.getCustomerId(), order.getSellerId(), order.getOrderItems(), order.getEmail(), order.getAddress(), order.getPostcode(), order.getOrderStatus(), order.getCreatedAt());
    }

    private void updateOrder(Order order) {
        int update = jdbcTemplate.update(UPDATE_ORDER.getSql(), toOrderParamMap(order));
        if (update != UPDATED_SIZE) {
            throw new IncorrectResultSizeDataAccessException(UPDATED_SIZE, update);
        }
    }

    @Override
    public void delete(long orderId) {
        int update = jdbcTemplate.update(DELETE_ORDER.getSql(), Collections.singletonMap("orderId", orderId));
        if (update != UPDATED_SIZE) {
            throw new IncorrectResultSizeDataAccessException(UPDATED_SIZE, update);
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_ORDER.getSql(), Collections.emptyMap());
    }

}
