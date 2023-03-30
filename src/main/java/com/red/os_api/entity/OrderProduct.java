package com.red.os_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_product")
public class OrderProduct {

    @EmbeddedId
    OrderProductKey orderProductKey;

    @ManyToOne
    @MapsId("order_id")
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    Product product;

    @Column(name = "quantity",nullable = false)
    int quantity;

    @Column(name = "product_price")
    BigDecimal product_price;

}
