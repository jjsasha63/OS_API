package com.red.os_api.service;

import com.red.os_api.entity.Order;
import com.red.os_api.entity.PaymentMethods;
import com.red.os_api.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentMethodsService implements AppService<PaymentMethods, Integer> {

    AppRepository<PaymentMethods,Integer> paymentMethodRep;

    @Autowired
    public void setPaymentMethodRep(AppRepository<PaymentMethods, Integer> paymentMethodRep) {
        this.paymentMethodRep = paymentMethodRep;
    }

    @Override
    public List<PaymentMethods> getAll() {
        List<PaymentMethods> orderList = new ArrayList<>();
        paymentMethodRep.findAll().forEach(orderList::add);
        return orderList;
    }

    @Override
    public PaymentMethods getById(Integer id) {
        return paymentMethodRep.findById(id).get();
    }

    @Override
    public PaymentMethods insert(PaymentMethods paymentMethods) {
        return paymentMethodRep.save(paymentMethods);
    }

    @Override
    public void update(Integer id, PaymentMethods paymentMethods) {
        PaymentMethods paymentMethodsNew = paymentMethodRep.findById(id).get();
        paymentMethodsNew.setName(paymentMethods.getName());
        paymentMethodsNew.setDescription(paymentMethods.getDescription());
        paymentMethodRep.save(paymentMethodsNew);
    }

    @Override
    public void delete(Integer id) {
        paymentMethodRep.delete(paymentMethodRep.findById(id).get());
    }
}
