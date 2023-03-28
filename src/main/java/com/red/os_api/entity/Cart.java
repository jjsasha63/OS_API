package com.red.os_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {

    @EmbeddedId
    CartKey cartKey;

    @ManyToOne
    @MapsId("auth_id")
    @JoinColumn(name = "auth_id")
    Auth auth;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    Product product;

    @Column(name = "quantity",nullable = false)
    int quantity;


}
