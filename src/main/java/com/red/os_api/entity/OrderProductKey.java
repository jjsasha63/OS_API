package com.red.os_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderProductKey implements Serializable {

    @Column(name = "order_id")
    Integer orderId;

    @Column(name = "product_id")
    Integer productId;


}
