package com.red.os_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_auth")
public class CustomerAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int customer_auth_id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    Customers customers;

    @Column(name = "username",nullable = false,unique = true,length = 50)
    String username;

    @Column(name = "password_hash",nullable = false,length = 100)
    String password_hash;

    public CustomerAuth(int customer_auth_id, String username, String password_hash) {
        this.customer_auth_id = customer_auth_id;
        this.username = username;
        this.password_hash = password_hash;
    }

    public CustomerAuth(){

    }

    public int getCustomer_auth_id() {
        return customer_auth_id;
    }

    public void setCustomer_auth_id(int customer_auth_id) {
        this.customer_auth_id = customer_auth_id;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    @Override
    public String
    toString() {
        return "CustomerAuth{" +
                "customer_auth_id=" + customer_auth_id +
                ", customers=" + customers +
                ", username='" + username + '\'' +
                ", password_hash='" + password_hash + '\'' +
                '}';
    }
}
