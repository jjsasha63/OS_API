package com.red.os_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_product")
public class OrderProduct {

    @EmbeddedId
    OrderProductKey orderProductKey;

    @ManyToOne
    @MapsId("order_id")
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    Product product;

    @Column(name = "quantity",nullable = false)
    int quantity;

    public OrderProduct(OrderProductKey orderProductKey, int quantity) {
        this.orderProductKey = orderProductKey;
        this.quantity = quantity;
    }

    public OrderProduct(){}

    public OrderProductKey getOrderProductKey() {
        return orderProductKey;
    }

    public void setOrderProductKey(OrderProductKey orderProductKey) {
        this.orderProductKey = orderProductKey;
    }

    public Order getOrders() {
        return order;
    }

    public void setOrders(Order order) {
        this.order = order;
    }

    public Product getProducts() {
        return product;
    }

    public void setProducts(Product product) {
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
        return "OrdersProducts{" +
                "orderProductKey=" + orderProductKey +
                ", orders=" + order +
                ", products=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
