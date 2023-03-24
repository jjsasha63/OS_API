create database if not exists online_store;

create table auth
(
    id         int auto_increment
        primary key,
    email      varchar(50)  not null,
    first_name varchar(50)  not null,
    last_name  varchar(50)  not null,
    password   varchar(100) not null,
    role       varchar(256) not null,
    constraint email
        unique (email)
);

create table auth_seq
(
    next_val bigint null
);

create table category
(
    category_id int auto_increment
        primary key,
    name        varchar(50) not null,
    description text        null,
    constraint name
        unique (name)
);

create table customer
(
    customer_id      int auto_increment
        primary key,
    shipping_address varchar(100) null,
    billing_address  varchar(100) null,
    constraint customer_ibfk_1
        foreign key (customer_id) references auth (id)
            on delete cascade
);

create table delivery_method
(
    delivery_id int auto_increment
        primary key,
    method      varchar(255) not null,
    description linestring   null
);

create table `order`
(
    order_id     int auto_increment
        primary key,
    customer_id  int         not null,
    order_date   date        not null,
    order_status varchar(50) not null,
    constraint fk_customer_id
        foreign key (customer_id) references customer (customer_id)
);

create table order_delivery
(
    order_id         int          not null,
    delivery_id      int          not null,
    delivered        tinyint(1)   not null,
    tracking_number  varchar(255) null,
    delivery_price   decimal      not null,
    delivery_address linestring   not null,
    primary key (order_id, delivery_id),
    constraint order_delivery_delivery_method_delivery_id_fk
        foreign key (delivery_id) references delivery_method (delivery_id),
    constraint order_delivery_order_order_id_fk
        foreign key (order_id) references `order` (order_id)
);

create table payment_methods
(
    payment_method_id int auto_increment
        primary key,
    name              varchar(50) not null,
    description       text        null,
    constraint name
        unique (name)
);

create table order_payment
(
    order_id          int          not null,
    payment_method_id int          not null,
    payment_link      varchar(255) not null,
    primary key (order_id, payment_method_id),
    constraint order_payment_ibfk_1
        foreign key (order_id) references `order` (order_id)
            on update cascade on delete cascade,
    constraint order_payment_ibfk_2
        foreign key (payment_method_id) references payment_methods (payment_method_id)
            on update cascade on delete cascade
);

create index payment_method_id
    on order_payment (payment_method_id);

create table product
(
    product_id   int auto_increment
        primary key,
    product_name varchar(255)   not null,
    price        decimal(10, 2) not null,
    description  text           null,
    picture      varchar(255)   null,
    quantity     int            not null,
    category_id  int            not null,
    constraint fk_category_id
        foreign key (category_id) references category (category_id),
    constraint chk_price
        check (`price` >= 0),
    constraint chk_quantity
        check (`quantity` >= 0)
);

create table cart
(
    customer_id int not null,
    product_id  int not null,
    quantity    int not null,
    primary key (customer_id, product_id),
    constraint cart_ibfk_1
        foreign key (customer_id) references customer (customer_id)
            on update cascade on delete cascade,
    constraint cart_ibfk_2
        foreign key (product_id) references product (product_id)
            on update cascade on delete cascade
);

create index product_id
    on cart (product_id);

create table order_product
(
    order_id   int not null,
    product_id int not null,
    quantity   int not null,
    primary key (order_id, product_id),
    constraint fk_order_id
        foreign key (order_id) references `order` (order_id),
    constraint fk_product_id
        foreign key (product_id) references product (product_id),
    constraint chk_pr_quantity
        check (`quantity` > 0)
);

create index idx_order_id
    on order_product (order_id);

create index idx_product_id
    on order_product (product_id);

create index idx_category_id
    on product (category_id);

create index idx_quantity
    on product (quantity);

create table review
(
    review_id   int auto_increment
        primary key,
    customer_id int                                          not null,
    product_id  int                                          not null,
    grade       enum ('One', 'Two', 'Three', 'Four', 'Five') not null,
    review_text varchar(255)                                 null,
    review_date date                                         not null,
    constraint review_ibfk_1
        foreign key (customer_id) references customer (customer_id)
            on delete cascade,
    constraint review_ibfk_2
        foreign key (product_id) references product (product_id)
            on delete cascade
);

create index customer_id
    on review (customer_id);

create index product_id
    on review (product_id);

create table token
(
    id         int auto_increment
        primary key,
    token      varchar(255) null,
    token_type varchar(255) null,
    revoked    bit          not null,
    expired    bit          not null,
    auth       int          null,
    constraint token_pk
        unique (token),
    constraint token_auth_id_fk
        foreign key (auth) references auth (id)
);

create table token_seq
(
    next_val bigint null
);

