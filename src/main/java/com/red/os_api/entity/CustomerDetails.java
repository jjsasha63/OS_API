package com.red.os_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer customer_id;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "customer_id",referencedColumnName = "id")
    Auth auth_id;

    @Column(name = "shipping_address")
    String shipping_address;

    @Column(name = "billing_address")
    String billing_address;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    PaymentMethod preferred_payment_method;

    @ManyToOne
    @JoinColumn(name = "delivery_method_id")
    DeliveryMethod preferred_delivery_method;


    @Transient
    List<CustomerDetails> customersDetails;

}