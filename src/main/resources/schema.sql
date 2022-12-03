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
    price     bigint   null,
    description varchar(255)  null,
    created_at   timestamp    null,
    updated_at   timestamp    null
);