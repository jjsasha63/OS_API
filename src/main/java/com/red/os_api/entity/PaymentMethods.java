package com.red.os_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_methods")
public class PaymentMethods {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int payment_method_id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    String name;

    @Column(name = "description")
    String description;

    public PaymentMethods(int payment_method_id, String name, String description) {
        this.payment_method_id = payment_method_id;
        this.name = name;
        this.description = description;
    }

    public PaymentMethods(){

    }

    public int getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(int payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PaymentMethods{" +
                "payment_method_id=" + payment_method_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
