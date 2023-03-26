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
@Table(name = "delivery_method")
public class DeliveryMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    Integer delivery_method_id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "description")
    String description;

    @Transient
    List<DeliveryMethod> deliveryMethods;

}
