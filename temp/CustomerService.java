package com.red.os_api.service;

import com.red.os_api.entity.Category;
import com.red.os_api.entity.Customer;
import com.red.os_api.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService implements AppService<Customer, Integer> {

    AppRepository<Customer,Integer> customerRep;

    @Autowired
    public void setCustomerRep(AppRepository<Customer, Integer> customerRep) {
        this.customerRep = customerRep;
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> customerList = new ArrayList<>();
        customerRep.findAll().forEach(customerList::add);
        return customerList;
    }

    @Override
    public Customer getById(Integer id) {
        return customerRep.findById(id).get();
    }

    @Override
    public Customer insert(Customer customer) {
        return customerRep.save(customer);
    }

    @Override
    public void update(Integer id, Customer customer) {
        Customer customerNew = customerRep.findById(id).get();
        customerNew.setFirst_name(customer.getFirst_name());
        customerNew.setSecond_name(customer.getSecond_name());
        customerNew.setEmail(customer.getEmail());
        customerNew.setShipping_address(customer.getShipping_address());
        customerNew.setBilling_address(customer.getBilling_address());
        customerRep.save(customerNew);
    }

    @Override
    public void delete(Integer id) {
        customerRep.delete(customerRep.findById(id).get());
    }
}
