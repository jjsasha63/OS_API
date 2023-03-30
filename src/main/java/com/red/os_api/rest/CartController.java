package com.red.os_api.rest;

import com.red.os_api.entity.req_resp.CartRequest;
import com.red.os_api.entity.req_resp.CartResponse;
import com.red.os_api.service.CartService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store/api/account/cart")
public class CartController {

    @Autowired
    private final CartService cartService;


    @PostMapping("/insert-one")
    public ResponseEntity<CartResponse> addToCart(@RequestBody CartRequest cartRequest, @NonNull HttpServletRequest request,
                                                  @NonNull HttpServletResponse response,
                                                  @NonNull FilterChain filterChain){
        return cartService.insertCartProduct(cartRequest,false,request,response,filterChain);
    }


    @PostMapping("/insert-many")
    public ResponseEntity<List<CartResponse>> addToCartMany(@RequestBody CartRequest cartRequest, @NonNull HttpServletRequest request,
                                                            @NonNull HttpServletResponse response,
                                                            @NonNull FilterChain filterChain){
        return cartService.insertCartProduct(cartRequest.getCartList(),false,request,response,filterChain);
    }

    @PutMapping("/update-one")
    public ResponseEntity<CartResponse> updateCart(@RequestBody CartRequest cartRequest, @NonNull HttpServletRequest request,
                                                   @NonNull HttpServletResponse response,
                                                   @NonNull FilterChain filterChain){
        return cartService.insertCartProduct(cartRequest,true,request,response,filterChain);
    }

    @PutMapping("/update-many")
    public ResponseEntity<List<CartResponse>> updateCartMany(@RequestBody CartRequest cartRequest, @NonNull HttpServletRequest request,
                                                             @NonNull HttpServletResponse response,
                                                             @NonNull FilterChain filterChain){
        return cartService.insertCartProduct(cartRequest.getCartList(),true,request,response,filterChain);
    }

    @PostMapping("/deleteById")
    public ResponseEntity<String> deleteFromCart(@RequestParam Integer id, @NonNull HttpServletRequest request,
                                                 @NonNull HttpServletResponse response,
                                                 @NonNull FilterChain filterChain){
        return cartService.deleteProductFromCart(id,request,response,filterChain);
    }

    @GetMapping("/getById")
    public ResponseEntity<CartResponse> getCartById(@RequestParam Integer id, @NonNull HttpServletRequest request,
                                                    @NonNull HttpServletResponse response,
                                                    @NonNull FilterChain filterChain){
        return cartService.getProductFromCartById(id,request,response,filterChain);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CartResponse>> getAllCart(@NonNull HttpServletRequest request,
                                                         @NonNull HttpServletResponse response,
                                                         @NonNull FilterChain filterChain){
        return cartService.getAllFromCart(request,response,filterChain);
    }


}
