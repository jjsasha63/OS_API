package com.red.os_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    int product_id;

    @Column(name = "product_name",nullable = false,length = 255)
    String product_name;

    @Column(name = "price",nullable = false)
    BigDecimal price;

    @Column(name = "description")
    String description;

    @Column(name = "picture")
    String picture;

    @Column(name = "quantity",nullable = false)
    int quantity;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    Category category;

    @OneToMany(mappedBy = "product")
    private List<Cart> carts;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @Transient
    String category_name;

    @Transient
    List<Product> products;

}
