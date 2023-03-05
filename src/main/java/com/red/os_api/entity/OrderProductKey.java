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
}
