package com.red.os_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders_products")
public class OrdersProducts {

    @EmbeddedId
    OrderProductKey orderProductKey;

    @ManyToOne
    @MapsId("order_id")
    @JoinColumn(name = "order_id")
    Orders orders;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    Products products;

    @Column(name = "quantity",nullable = false)
    int quantity;

    public OrdersProducts(OrderProductKey orderProductKey, int quantity) {
        this.orderProductKey = orderProductKey;
        this.quantity = quantity;
    }

    public OrdersProducts(){}

    public OrderProductKey getOrderProductKey() {
        return orderProductKey;
    }

    public void setOrderProductKey(OrderProductKey orderProductKey) {
        this.orderProductKey = orderProductKey;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrdersProducts{" +
                "orderProductKey=" + orderProductKey +
                ", orders=" + orders +
                ", products=" + products +
                ", quantity=" + quantity +
                '}';
    }
}
