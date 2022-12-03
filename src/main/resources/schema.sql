
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

