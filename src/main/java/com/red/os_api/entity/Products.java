package com.red.os_api.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int product_id;

    @Column(name = "product_name",nullable = false,length = 50)
    String product_name;

    @Column(name = "price",nullable = false)
    BigDecimal price;

    @Column(name = "description")
    String description;

    @Column(name = "picture")
    String picture;

    @Column(name = "quantity",nullable = false)
    int quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Categories categories;

    public Products(int product_id, String product_name, BigDecimal price, String description, String picture, int quantity) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
        this.description = description;
        this.picture = picture;
        this.quantity = quantity;
    }

    public Products(){

    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Products{" +
                "product_id=" + product_id +
                ", product_name='" + product_name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", picture='" + picture + '\'' +
                ", quantity=" + quantity +
                ", categories=" + categories +
                '}';
    }
}
