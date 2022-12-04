package com.prgms.kdt.danawa.order.repository;

public enum OrderSql {
    FIND_ORDERS_BY_CUSTOMER_ID("SELECT * FROM `order` o JOIN order_item oi on o.order_id = oi.order_id WHERE customer_id = :customerId"),
    FIND_BY_ID("SELECT *  FROM `order` o JOIN order_item oi on o.order_id = oi.order_id  WHERE o.order_id = :orderId"),
    INSERT_ORDER("INSERT INTO `order`(customer_id, seller_id, email, address, postcode, order_status, created_at) VALUES (:customer_id, :seller_id, :email, :address, :postcode, :order_status, :created_at)"),
    INSERT_ORDER_ITEMS("INSERT INTO order_item(order_id, product_id, category, price, quantity, created_at) VALUES (:order_id, :product_id, :category, :price, :quantity, :created_at)"),
    UPDATE_ORDER("UPDATE `order` SET customer_id = :customer_id, seller_id = :seller_id, email = :email, address = :address, postcode = :postcode, order_status = :order_status, created_at = :created_at WHERE order_id = :order_id"),
    DELETE_ORDER("DELETE FROM `order` WHERE order_id = :orderId;"),
    DELETE_ALL_ORDER("DELETE FROM `order`");

    private final String sql;

    OrderSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
