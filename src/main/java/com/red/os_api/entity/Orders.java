package com.red.os_api.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int order_id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customers customers;

    @Column(name = "order_date",nullable = false)
    LocalDateTime order_date;

    @Column(name = "order_status",nullable = false,length = 50)
    String order_status;

    public Orders(){

    }
    public Orders(int order_id,LocalDateTime order_date, String order_status) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.order_status = order_status;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public LocalDateTime getOrder_date() {
        return order_date;
    }

    public void setOrder_date(LocalDateTime order_date) {
        this.order_date = order_date;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "order_id=" + order_id +
                ", customers=" + customers +
                ", order_date=" + order_date +
                ", order_status='" + order_status + '\'' +
                '}';
    }
}
