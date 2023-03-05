package com.red.os_api.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int review_id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    public enum Grade {One,Two,Three,Four,Five}

    @Column(name = "review_text")
    String review_text;

    @Column(name = "review_date",nullable = false)
    LocalDateTime review_date;

    public Review(){}

    public Review(int review_id, Grade grade, String review_text, LocalDateTime review_date) {
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

    public Customer getCustomers() {
        return customer;
    }

    public void setCustomers(Customer customer) {
        this.customer = customer;
    }

    public Product getProducts() {
        return product;
    }

    public void setProducts(Product product) {
        this.product = product;
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
                ", customers=" + customer +
                ", products=" + product +
                ", grade=" + grade +
                ", review_text='" + review_text + '\'' +
                ", review_date=" + review_date +
                '}';
    }
}
