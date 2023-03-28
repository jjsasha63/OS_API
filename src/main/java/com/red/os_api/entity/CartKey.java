package com.red.os_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Embeddable
public class CartKey implements Serializable {

    @Column(name = "auth_id")
    Integer auth_id;

    @Column(name = "product_id")
    Integer product_id;

}
