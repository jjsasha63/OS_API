package com.red.os_api.rest;

import com.red.os_api.entity.req_resp.OrderRequest;
import com.red.os_api.entity.req_resp.OrderResponse;
import com.red.os_api.entity.req_resp.ReviewRequest;
import com.red.os_api.entity.req_resp.ReviewResponse;
import com.red.os_api.service.OrderService;
import com.red.os_api.service.ReviewService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store/api/account/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/add-one")
    public ResponseEntity<OrderResponse> insertOrder(@RequestBody OrderRequest orderRequest, @NonNull HttpServletRequest request,
                                                      @NonNull HttpServletResponse response,
                                                      @NonNull FilterChain filterChain){
        return orderService.insertOrder(orderRequest,request,response,filterChain);
    }


    @GetMapping("/getById")
    public ResponseEntity<OrderResponse> getById(@RequestParam Integer id, @NonNull HttpServletRequest request,
                                             @NonNull HttpServletResponse response,
                                             @NonNull FilterChain filterChain){
        return orderService.getById(id,request,response,filterChain);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<OrderResponse>> getAll( @NonNull HttpServletRequest request,
                                                  @NonNull HttpServletResponse response,
                                                  @NonNull FilterChain filterChain){
        return orderService.getAll(request,response,filterChain);
    }



}
