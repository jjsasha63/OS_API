package com.red.os_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "order_id")
    Integer orderId;

    @ManyToOne
    @JoinColumn(name = "auth_id")
    private Auth auth;

    @ManyToOne
    @JoinColumn(name = "delivery_method_id")
    private DeliveryMethod deliveryMethod;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @Column(name = "order_date",nullable = false)
    LocalDateTime order_date;

    @Enumerated(EnumType.STRING)
    OrderStatus order_status;

    @Enumerated(EnumType.STRING)
    DeliveryStatus delivery_status;

    @Column(name = "delivery_tracking_number")
    String delivery_tracking_number;

    @Column(name = "delivery_price")
    BigDecimal delivery_price;

    @Column(name = "delivery_address")
    String delivery_address;

    @Column(name = "payment_link")
    String payment_link;

    @Column(name = "payment_reciept")
    String payment_reciept;

    @Column(name = "order_price")
    BigDecimal order_price;

    @Column(name = "comment")
    String comment;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProductList;

}
