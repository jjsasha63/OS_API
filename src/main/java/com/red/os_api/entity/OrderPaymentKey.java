package com.red.os_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class OrderPaymentKey implements Serializable {

    @Column(name = "order_id")
    Integer order_id;

    @Column(name = "payment_method_id")
    Integer payment_method_id;
}
