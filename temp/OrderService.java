package com.red.os_api.service;

import com.red.os_api.entity.Customer;
import com.red.os_api.entity.Order;
import com.red.os_api.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements AppService<Order, Integer> {

    AppRepository<Order,Integer> orderRep;

    @Autowired
    public void setOrderRep(AppRepository<Order, Integer> orderRep) {
        this.orderRep = orderRep;
    }

    @Override
    public List<Order> getAll() {
        List<Order> orderList = new ArrayList<>();
        orderRep.findAll().forEach(orderList::add);
        return orderList;
    }

    @Override
    public Order getById(Integer id) {
        return orderRep.findById(id).get();
    }

    @Override
    public Order insert(Order order) {
        return orderRep.save(order);
    }

    @Override
    public void update(Integer id, Order order) {
        Order orderNew = orderRep.findById(id).get();
        orderNew.setOrder_date(order.getOrder_date());
        orderNew.setOrder_status(order.getOrder_status());
        orderNew.setCustomers(order.getCustomers());
        orderRep.save(orderNew);
    }

    @Override
    public void delete(Integer id) {
        orderRep.delete(orderRep.findById(id).get());
    }
}
