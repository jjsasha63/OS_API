package com.red.os_api.service;

import com.red.os_api.entity.Cart;
import com.red.os_api.entity.CartKey;
import com.red.os_api.entity.OrderProduct;
import com.red.os_api.entity.OrderProductKey;
import com.red.os_api.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService implements AppService<Cart, CartKey> {

    AppRepository<Cart, CartKey> cartRep;

    @Autowired
    public void setCartRep(AppRepository<Cart, OrderProductKey> cartRep) {
        this.cartRep = cartRep;
    }

    @Override
    public List<Cart> getAll() {
        List<Cart> cartList = new ArrayList<>();
        cartRep.findAll().forEach(cartList::add);
        return cartList;
    }

    @Override
    public Cart getById(CartKey id) {
        return cartRep.findById(id).get();
    }

    @Override
    public Cart insert(Cart cart) {
        return cartRep.save(cart);
    }

    @Override
    public void update(CartKey id, Cart cart) {
        Cart cartNew = cartRep.findById(id).get();
        cartNew.setQuantity(cart.getQuantity());
        cartRep.save(cartNew);
    }

    @Override
    public void delete(CartKey id) {
        cartRep.delete(cartRep.findById(id).get());
    }
}
