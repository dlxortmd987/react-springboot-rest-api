create table `order`
(
    order_id     bigint auto_increment
        primary key,
    customer_id  bigint       null,
    seller_id    bigint       null,
    email        varchar(100) null,
    address      varchar(100) null,
    postcode     varchar(5)   null,
    order_status varchar(10)  null,
    created_at   timestamp    null
);

create table product
(
    product_id     bigint auto_increment
        primary key,
    seller_id        bigint null,
    category      varchar(20) null,
    price     decimal   null,
    description varchar(255)  null,
    created_at   timestamp    null,
    updated_at   timestamp    null
);

CREATE TABLE order_item
(
    seq        bigint      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_id   bigint  NOT NULL,
    product_id bigint  NOT NULL,
    category   VARCHAR(50) NOT NULL,
    price      decimal      NOT NULL,
    quantity   int         NOT NULL,
    created_at timestamp NOT NULL,
    CONSTRAINT fk_order_items_to_order FOREIGN KEY (order_id) REFERENCES `order` (order_id) ON DELETE CASCADE
);