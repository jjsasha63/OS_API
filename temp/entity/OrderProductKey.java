package com.red.os_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class OrderProductKey implements Serializable {

    @Column(name = "order_id")
    Integer order_id;

    @Column(name = "product_id")
    Integer product_id;

    public OrderProductKey(Integer order_id, Integer product_id) {
        this.order_id = order_id;
        this.product_id = product_id;
    }

    public OrderProductKey() {

    }
}
