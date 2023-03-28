package com.red.os_api.rest;

import com.red.os_api.entity.CustomerDetails;
import com.red.os_api.entity.req_resp.CartRequest;
import com.red.os_api.entity.req_resp.CartResponse;
import com.red.os_api.entity.req_resp.CustomerDetailsRequest;
import com.red.os_api.entity.req_resp.CustomerDetailsResponse;
import com.red.os_api.service.CartService;
import com.red.os_api.service.CustomerDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store/api/account")
public class CustomerController {

    @Autowired
    private final CustomerDetailsService customerDetailsService;

    @Autowired
    private final CartService cartService;



    @GetMapping("/getDetails")
    public ResponseEntity<CustomerDetailsResponse> getMyDetails(@NonNull HttpServletRequest request,
                                                                @NonNull HttpServletResponse response,
                                                                @NonNull FilterChain filterChain) throws ServletException, IOException {
        return customerDetailsService.getCurrentUserDetails(request,response,filterChain);
    }


    @PostMapping("/addToCart-one")
    public ResponseEntity<CartResponse> addToCart(@RequestBody CartRequest cartRequest, @NonNull HttpServletRequest request,
                                                  @NonNull HttpServletResponse response,
                                                  @NonNull FilterChain filterChain){
        return cartService.insertCartProduct(cartRequest,false,request,response,filterChain);
    }


    @PostMapping("/addToCart-many")
    public ResponseEntity<List<CartResponse>> addToCartMany(@RequestBody CartRequest cartRequest, @NonNull HttpServletRequest request,
                                                        @NonNull HttpServletResponse response,
                                                        @NonNull FilterChain filterChain){
        return cartService.insertCartProduct(cartRequest.getCartRequestList(),false,request,response,filterChain);
    }

    @PutMapping("/updateCart-one")
    public ResponseEntity<CartResponse> updateCart(@RequestBody CartRequest cartRequest, @NonNull HttpServletRequest request,
                                                  @NonNull HttpServletResponse response,
                                                  @NonNull FilterChain filterChain){
        return cartService.insertCartProduct(cartRequest,true,request,response,filterChain);
    }

    @PutMapping("/updateCart-many")
    public ResponseEntity<List<CartResponse>> updateCartMany(@RequestBody CartRequest cartRequest, @NonNull HttpServletRequest request,
                                                            @NonNull HttpServletResponse response,
                                                            @NonNull FilterChain filterChain){
        return cartService.insertCartProduct(cartRequest.getCartRequestList(),true,request,response,filterChain);
    }

    @PostMapping("/deleteCartById")
    public ResponseEntity<String> deleteFromCart(@RequestParam Integer id, @NonNull HttpServletRequest request,
                                                  @NonNull HttpServletResponse response,
                                                  @NonNull FilterChain filterChain){
        return cartService.deleteProductFromCart(id,request,response,filterChain);
    }

    @GetMapping("/getCartById")
    public ResponseEntity<CartResponse> getCartById(@RequestParam Integer id, @NonNull HttpServletRequest request,
                                                            @NonNull HttpServletResponse response,
                                                            @NonNull FilterChain filterChain){
        return cartService.getProductFromCartById(id,request,response,filterChain);
    }

    @GetMapping("/getCartAll")
    public ResponseEntity<List<CartResponse>> getAllCart(@NonNull HttpServletRequest request,
                                                            @NonNull HttpServletResponse response,
                                                            @NonNull FilterChain filterChain){
        return cartService.getAllFromCart(request,response,filterChain);
    }


}
