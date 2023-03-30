package com.red.os_api.repository;

import com.red.os_api.entity.Order;
import com.red.os_api.entity.OrderProduct;
import com.red.os_api.entity.OrderProductKey;
import com.red.os_api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductKey> {

    List<OrderProduct> findAllByOrder(Order order);

    List<OrderProduct> findAllByProduct(Product product);

}
