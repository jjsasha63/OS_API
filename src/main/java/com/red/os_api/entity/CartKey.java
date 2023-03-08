package com.red.os_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CartKey implements Serializable {

    @Column(name = "customer_id")
    Integer customer_id;

    @Column(name = "product_id")
    Integer product_id;

    public CartKey(Integer customer_id, Integer product_id) {
        this.customer_id = customer_id;
        this.product_id = product_id;
    }

    public CartKey() {

    }
}
