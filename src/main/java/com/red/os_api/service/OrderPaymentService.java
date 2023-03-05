package com.red.os_api.service;

import com.red.os_api.entity.Order;
import com.red.os_api.entity.OrderPayment;
import com.red.os_api.entity.OrderPaymentKey;
import com.red.os_api.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderPaymentService implements AppService<OrderPayment, OrderPaymentKey> {

    AppRepository<OrderPayment, OrderPaymentKey> orderRep;

    @Autowired
    public void setOrderRep(AppRepository<OrderPayment, OrderPaymentKey> orderRep) {
        this.orderRep = orderRep;
    }

    @Override
    public List<OrderPayment> getAll() {
        List<OrderPayment> orderList = new ArrayList<>();
        orderRep.findAll().forEach(orderList::add);
        return orderList;
    }

    @Override
    public OrderPayment getById(OrderPaymentKey id) {
        return orderRep.findById(id).get();
    }

    @Override
    public OrderPayment insert(OrderPayment order) {
        return orderRep.save(order);
    }

    @Override
    public void update(OrderPaymentKey id, OrderPayment order) {
        OrderPayment orderNew = orderRep.findById(id).get();
        orderNew.setPayment_link(order.getPayment_link());
        orderNew.setOrders(order.getOrders());
        orderNew.setPaymentMethods(order.getPaymentMethods());
        orderRep.save(orderNew);
    }

    @Override
    public void delete(OrderPaymentKey id) {
        orderRep.delete(orderRep.findById(id).get());
    }
}
