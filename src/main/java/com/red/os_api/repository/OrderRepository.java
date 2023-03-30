package com.red.os_api.repository;

import com.red.os_api.entity.Auth;
import com.red.os_api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Integer> {

    Optional<Order> findByOrderIdAndAuth(Integer id, Auth auth);

    List<Order> findAllByAuth(Auth auth);
}
