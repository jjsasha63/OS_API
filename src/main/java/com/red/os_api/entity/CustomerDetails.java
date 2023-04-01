package com.red.os_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer_details")
public class CustomerDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private Integer customer_id;

    @OneToOne
    @JoinColumn(name = "auth")
    private Auth auth;

    @Column(name = "shipping_address")
    String shipping_address;

    @Column(name = "billing_address")
    String billing_address;

    @Column
    String card_number;


    @ManyToOne
    @JoinColumn(name = "preferred_payment_method")
    PaymentMethod preferred_payment_method;


    @ManyToOne
    @JoinColumn(name = "preferred_delivery_method")
    DeliveryMethod preferred_delivery_method;



}