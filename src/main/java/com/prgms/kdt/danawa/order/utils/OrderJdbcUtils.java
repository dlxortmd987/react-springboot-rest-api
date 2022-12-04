package com.prgms.kdt.danawa.order.utils;

import com.prgms.kdt.danawa.generic.domain.*;
import com.prgms.kdt.danawa.order.domain.Order;
import com.prgms.kdt.danawa.order.domain.OrderItem;
import com.prgms.kdt.danawa.order.domain.OrderStatus;
import com.prgms.kdt.danawa.product.domain.Category;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.prgms.kdt.danawa.generic.utils.JdbcUtils.toLocalDateTime;

public class OrderJdbcUtils {

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
    private static final RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER = (resultSet, i) -> {
        long productId = resultSet.getLong("product_id");
        Category category = Category.valueOf(resultSet.getString("category"));
        Money money = Money.of(resultSet.getBigDecimal("price"));
        Quantity quantity = new Quantity(resultSet.getInt("quantity"));

        return new OrderItem(
                productId,
                category,
                money,
                quantity
        );
    };

    public static boolean isSameOrder(ResultSet rs, Long orderId) throws SQLException {
        return !orderId.equals(rs.getLong("order_id"));
    }

    public static boolean isOrderNull(Order currentOrder) {
        return currentOrder == null;
    }

    public static List<Order> getOrders(ResultSet rs) throws SQLException {
        List<Order> orders = new ArrayList<>();
        Long orderId = null;
        Order currentOrder = null;
        int orderIdx = 0;
        int itemIdx = 0;
        while (rs.next()) {
            if (isOrderNull(currentOrder) || isSameOrder(rs, orderId)) {
                orderId = rs.getLong("order_id");
                currentOrder = ORDER_ROW_MAPPER.mapRow(rs, orderIdx++);
                itemIdx = 0;
                orders.add(currentOrder);
            }
            currentOrder.addItem(ORDER_ITEM_ROW_MAPPER.mapRow(rs, itemIdx++));
        }
        return orders;
    }

    public static Order getOrder(ResultSet rs) throws SQLException {
        Order order = null;
        int row = 0;
        while (rs.next()) {
            if (isOrderNull(order)) {
                order = ORDER_ROW_MAPPER.mapRow(rs, row);
            }
            order.addItem(ORDER_ITEM_ROW_MAPPER.mapRow(rs, row));
            row++;
        }
        return order;
    }
}
