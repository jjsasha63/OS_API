package com.red.os_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {

    @EmbeddedId
    CartKey cartKey;

    @ManyToOne
    @MapsId("customer_id")
    @JoinColumn(name = "customer_id")
    Customer customer;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    Product product;

    @Column(name = "quantity",nullable = false)
    int quantity;

    public Cart(CartKey cartKey, int quantity) {
        this.cartKey = cartKey;
        this.quantity = quantity;
    }

    public Cart(){}

    public CartKey getCartKey() {
        return cartKey;
    }

    public void setCartKey(CartKey cartKey) {
        this.cartKey = cartKey;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartKey=" + cartKey +
                ", customer=" + customer +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
