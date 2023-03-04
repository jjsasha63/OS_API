package com.red.os_api.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int review_id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customers customers;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Products products;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    public enum Grade {One,Two,Three,Four,Five}

    @Column(name = "review_text")
    String review_text;

    @Column(name = "review_date",nullable = false)
    LocalDateTime review_date;

    public Reviews(){}

    public Reviews(int review_id, Grade grade, String review_text, LocalDateTime review_date) {
        this.review_id = review_id;
        this.grade = grade;
        this.review_text = review_text;
        this.review_date = review_date;
    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }

    public LocalDateTime getReview_date() {
        return review_date;
    }

    public void setReview_date(LocalDateTime review_date) {
        this.review_date = review_date;
    }

    @Override
    public String toString() {
        return "Reviews{" +
                "review_id=" + review_id +
                ", customers=" + customers +
                ", products=" + products +
                ", grade=" + grade +
                ", review_text='" + review_text + '\'' +
                ", review_date=" + review_date +
                '}';
    }
}
