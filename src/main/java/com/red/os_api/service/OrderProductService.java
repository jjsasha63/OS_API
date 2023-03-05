package com.red.os_api.service;

import com.red.os_api.entity.OrderPayment;
import com.red.os_api.entity.OrderPaymentKey;
import com.red.os_api.entity.OrderProduct;
import com.red.os_api.entity.OrderProductKey;
import com.red.os_api.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderProductService implements AppService<OrderProduct, OrderProductKey> {

    AppRepository<OrderProduct, OrderProductKey> orderRep;

    @Autowired
    public void setOrderRep(AppRepository<OrderProduct, OrderProductKey> orderRep) {
        this.orderRep = orderRep;
    }

    @Override
    public List<OrderProduct> getAll() {
        List<OrderProduct> orderList = new ArrayList<>();
        orderRep.findAll().forEach(orderList::add);
        return orderList;
    }

    @Override
    public OrderProduct getById(OrderProductKey id) {
        return orderRep.findById(id).get();
    }

    @Override
    public OrderProduct insert(OrderProduct order) {
        return orderRep.save(order);
    }

    @Override
    public void update(OrderProductKey id, OrderProduct order) {
        OrderProduct orderNew = orderRep.findById(id).get();
        orderNew.setQuantity(order.getQuantity());
        orderNew.setOrders(order.getOrders());
        orderNew.setProducts(order.getProducts());
        orderRep.save(orderNew);
    }

    @Override
    public void delete(OrderProductKey id) {
        orderRep.delete(orderRep.findById(id).get());
    }
}
