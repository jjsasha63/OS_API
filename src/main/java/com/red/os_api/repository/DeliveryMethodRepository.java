package com.red.os_api.repository;

import com.red.os_api.entity.DeliveryMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethod,Integer> {

    DeliveryMethod getDeliveryMethodByName(String name);


}
