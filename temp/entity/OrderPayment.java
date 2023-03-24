package com.red.os_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_payment")
public class OrderPayment {

    @EmbeddedId
    OrderPaymentKey orderPaymentKey;

    @ManyToOne
    @MapsId("order_id")
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne
    @MapsId("payment_method_id")
    @JoinColumn(name = "payment_method_id")
    PaymentMethods paymentMethods;

    @Column(name = "payment_link",nullable = false)
    String payment_link;

    public OrderPayment(){

    }

    public OrderPayment(OrderPaymentKey orderPaymentKey, String payment_link) {
        this.orderPaymentKey = orderPaymentKey;
        this.payment_link = payment_link;
    }

    public OrderPaymentKey getOrderPaymentKey() {
        return orderPaymentKey;
    }

    public void setOrderPaymentKey(OrderPaymentKey orderPaymentKey) {
        this.orderPaymentKey = orderPaymentKey;
    }

    public Order getOrders() {
        return order;
    }

    public void setOrders(Order order) {
        this.order = order;
    }

    public PaymentMethods getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(PaymentMethods paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public String getPayment_link() {
        return payment_link;
    }

    public void setPayment_link(String payment_link) {
        this.payment_link = payment_link;
    }

    @Override
    public String toString() {
        return "OrdersPayments{" +
                "orderPaymentKey=" + orderPaymentKey +
                ", orders=" + order +
                ", paymentMethods=" + paymentMethods +
                ", payment_link='" + payment_link + '\'' +
                '}';
    }
}
