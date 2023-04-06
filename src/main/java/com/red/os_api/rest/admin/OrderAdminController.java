package com.red.os_api.rest.admin;


import com.red.os_api.entity.req_resp.OrderRequest;
import com.red.os_api.entity.req_resp.OrderResponse;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/store/api/admin/order")
public class OrderAdminController {

    private final OrderService orderService;

    @PostMapping("/deleteById")
    public ResponseEntity<String> deleteById(@RequestParam Integer id){
        return orderService.deleteOrderById(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<OrderResponse>> getAll(){
        return orderService.getAll();
    }

    @GetMapping("/getById")
    public ResponseEntity<OrderResponse> getAllById(@RequestParam Integer id){
        return orderService.getById(id);
    }

    @GetMapping("/getByAuth")
    public ResponseEntity<List<OrderResponse>> getAllByAuth(@RequestParam Integer auth){
        return orderService.getByAuth(auth);
    }

    @PostMapping("/insert-one")
    public ResponseEntity<OrderResponse> insertOrder(@RequestBody OrderRequest orderRequest,@RequestParam Integer auth){
        return orderService.insertOrder(orderRequest,auth,null);
    }


    @PutMapping("/update-one")
    public ResponseEntity<OrderResponse> updateOrder(@RequestBody OrderRequest orderRequest,@RequestParam Integer id){
        return orderService.insertOrder(orderRequest,null,id);
    }





}
