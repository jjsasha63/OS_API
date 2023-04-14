create table auth
(
    id         int auto_increment
        primary key,
    email      varchar(50)    not null,
    first_name varbinary(255) not null,
    last_name  varbinary(255) not null,
    password   varchar(100)   not null,
    role       varchar(256)   not null,
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

create table delivery_method
(
    delivery_method_id int auto_increment
        primary key,
    name               varchar(255) not null,
    description        text         null
);

create table payment_method
(
    payment_method_id int auto_increment
        primary key,
    name              varchar(255) not null,
    description       text         null,
    constraint name
        unique (name)
);

create table customer_details
(
    customer_id               int auto_increment
        primary key,
    shipping_address          varbinary(255) null,
    billing_address           varbinary(255) null,
    preferred_delivery_method int default 1  null,
    preferred_payment_method  int default 1  null,
    auth                      int            null,
    card_number               varbinary(255) null,
    constraint customer_details___fk
        foreign key (preferred_payment_method) references payment_method (payment_method_id)
            on delete set null,
    constraint customer_details_auth_id_fk
        foreign key (auth) references auth (id),
    constraint customer_details_delivery_method_delivery_id_fk
        foreign key (preferred_delivery_method) references delivery_method (delivery_method_id)
            on delete set null
);

create table order_
(
    order_id                 int auto_increment
        primary key,
    auth_id                  int            not null,
    order_date               date           not null,
    order_status             varchar(50)    not null,
    delivery_method_id       int            null,
    delivery_status          varchar(100)   null,
    delivery_tracking_number varchar(255)   null,
    delivery_price           decimal(10, 2) null,
    delivery_address         varchar(255)   not null,
    payment_method_id        int            null,
    payment_link             varchar(255)   null,
    payment_reciept          varchar(255)   null,
    order_price              decimal(10, 2) null,
    comment                  text           null,
    constraint fk_customer_id
        foreign key (auth_id) references auth (id),
    constraint order__delivery_method_delivery_method_id_fk
        foreign key (delivery_method_id) references delivery_method (delivery_method_id)
            on update cascade on delete set null,
    constraint order__payment_method_payment_method_id_fk
        foreign key (payment_method_id) references payment_method (payment_method_id)
            on update cascade on delete set null
);

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
    auth_id    int not null,
    product_id int not null,
    quantity   int not null,
    primary key (auth_id, product_id),
    constraint cart_ibfk_1
        foreign key (auth_id) references auth (id)
            on update cascade on delete cascade,
    constraint cart_ibfk_2
        foreign key (product_id) references product (product_id)
            on update cascade on delete cascade
);

create index product_id
    on cart (product_id);

create table order_product
(
    order_id      int            not null,
    product_id    int            not null,
    quantity      int            not null,
    product_price decimal(10, 2) not null,
    primary key (order_id, product_id),
    constraint fk_order_id
        foreign key (order_id) references order_ (order_id),
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
    auth_id     int          not null,
    product_id  int          not null,
    grade       varchar(50)  null,
    review_text varchar(255) null,
    review_date date         not null,
    constraint review_ibfk_1
        foreign key (auth_id) references auth (id)
            on delete cascade,
    constraint review_ibfk_2
        foreign key (product_id) references product (product_id)
            on delete cascade
);

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

