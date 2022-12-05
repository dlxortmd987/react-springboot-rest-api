create table IF NOT EXISTS `order`
(
    order_id     bigint       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    customer_id  bigint       NOT NULL,
    seller_id    bigint       NOT NULL,
    email        varchar(100) NOT NULL,
    address      varchar(100) NOT NULL,
    postcode     varchar(5)   NOT NULL,
    order_status varchar(10)  NOT NULL,
    created_at   timestamp    NOT NULL
);

create table IF NOT EXISTS product
(
    product_id  bigint       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    seller_id   bigint       NOT NULL,
    category    varchar(20)  NOT NULL,
    price       decimal      NOT NULL,
    description varchar(255) NOT NULL,
    created_at  timestamp    NOT NULL,
    updated_at  timestamp    NOT NULL
);

CREATE TABLE IF NOT EXISTS order_item
(
    seq        bigint      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_id   bigint      NOT NULL,
    product_id bigint      NOT NULL,
    category   VARCHAR(50) NOT NULL,
    price      decimal     NOT NULL,
    quantity   int         NOT NULL,
    created_at timestamp   NOT NULL,
    CONSTRAINT fk_order_items_to_order FOREIGN KEY (order_id) REFERENCES `order` (order_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `user`
(
    user_id    bigint      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_type  varchar(20) NOT NULL,
    email      varchar(100) NOT NULL UNIQUE,
    created_at timestamp   NOT NULL,
    updated_at timestamp   NOT NULL
)