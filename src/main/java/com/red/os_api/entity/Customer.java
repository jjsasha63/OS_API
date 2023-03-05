package com.red.os_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer customer_id;

    @Column(name = "first_name", nullable = false,length = 50)
    String first_name;

    @Column(name = "second_name",nullable = false,length = 50)
    String second_name;

    @Column(name = "email", nullable = false,unique = true,length = 50)
    String email;

    @Column(name = "shipping_address", length = 100)
    String shipping_address;

    @Column(name = "billing_address",length = 100)
    String billing_address;

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(String billing_address) {
        this.billing_address = billing_address;
    }

    public Customer(){

    }
    public Customer(int customer_id, String first_name, String second_name, String email, String shipping_address, String billing_address) {
        this.customer_id = customer_id;
        this.first_name = first_name;
        this.second_name = second_name;
        this.email = email;
        this.shipping_address = shipping_address;
        this.billing_address = billing_address;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "customer_id=" + customer_id +
                ", first_name='" + first_name + '\'' +
                ", second_name='" + second_name + '\'' +
                ", email='" + email + '\'' +
                ", shipping_address='" + shipping_address + '\'' +
                ", billing_address='" + billing_address + '\'' +
                '}';
    }
}
