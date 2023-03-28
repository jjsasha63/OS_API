package com.red.os_api.repository;

import com.red.os_api.entity.Auth;
import com.red.os_api.entity.Cart;
import com.red.os_api.entity.CartKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, CartKey> {

    List<Cart> findAllByAuth(Auth auth);

}
