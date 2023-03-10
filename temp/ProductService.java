package com.red.os_api.service;

import com.red.os_api.entity.Order;
import com.red.os_api.entity.Product;
import com.red.os_api.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements AppService<Product, Integer> {

    AppRepository<Product,Integer> productRep;

    @Autowired
    public void setProductRep(AppRepository<Product, Integer> productRep) {
        this.productRep = productRep;
    }

    @Override
    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();
        productRep.findAll().forEach(productList::add);
        return productList;
    }

    @Override
    public Product getById(Integer id) {
        return productRep.findById(id).get();
    }

    @Override
    public Product insert(Product product) {
        return productRep.save(product);
    }

    @Override
    public void update(Integer id, Product product) {
        Product productNew = productRep.findById(id).get();
        productNew.setProduct_name(product.getProduct_name());
        productNew.setPrice(product.getPrice());
        productNew.setDescription(product.getDescription());
        productNew.setPicture(product.getPicture());
        productNew.setQuantity(product.getQuantity());
        productNew.setCategories(product.getCategories());
        productRep.save(productNew);
    }

    @Override
    public void delete(Integer id) {
        productRep.delete(productRep.findById(id).get());
    }
}
