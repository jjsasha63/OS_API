package com.red.os_api.repository;

import com.red.os_api.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Integer> {

    PaymentMethod getPaymentMethodByName(String name);

}
