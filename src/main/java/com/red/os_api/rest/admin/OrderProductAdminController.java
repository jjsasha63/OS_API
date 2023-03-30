package com.red.os_api.rest.admin;

import com.red.os_api.entity.req_resp.OrderProductRequest;
import com.red.os_api.entity.req_resp.OrderRequest;
import com.red.os_api.entity.req_resp.OrderResponse;
import com.red.os_api.repository.OrderProductRepository;
import com.red.os_api.service.OrderProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/store/api/admin/orderProduct")
public class OrderProductAdminController {

    private final OrderProductService orderProductService;


    @PostMapping("/add-one")
    public ResponseEntity<OrderProductRequest> insertOrderProduct(@RequestBody OrderProductRequest orderProductRequest){
        return orderProductService.insert(orderProductRequest);
    }


    @PostMapping("/add-many")
    public ResponseEntity<List<OrderProductRequest>> insertOrderProducts(@RequestBody OrderProductRequest orderProductRequest){
        return orderProductService.insert(orderProductRequest.getOrderProductList());
    }


    @PutMapping("/update-one")
    public ResponseEntity<OrderProductRequest> updateOrderProduct(@RequestBody OrderProductRequest orderProductRequest){
        return orderProductService.insert(orderProductRequest);
    }

    @PutMapping("/update-many")
    public ResponseEntity<List<OrderProductRequest>> updateOrderProducts(@RequestBody OrderProductRequest orderProductRequest){
        return orderProductService.insert(orderProductRequest.getOrderProductList());
    }

    @PostMapping("/deleteByOrderAndProduct")
    public ResponseEntity<String> deleteById(@RequestParam Integer order, @RequestParam Integer product){
        return orderProductService.deleteById(order,product);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<OrderProductRequest>> getAll(){
        return orderProductService.getAll();
    }

    @GetMapping("/getByOrderAndProduct")
    public ResponseEntity<OrderProductRequest> getByOrderAndProduct(@RequestParam Integer order
            ,@RequestParam Integer product){
        return orderProductService.getByOrderAndProduct(order,product);
    }

    @GetMapping("/getByOrder")
    public ResponseEntity<List<OrderProductRequest>> getByOrder(@RequestParam Integer order){
        return orderProductService.getByOrder(order);
    }

    @GetMapping("/getByProduct")
    public ResponseEntity<List<OrderProductRequest>> getByProduct(@RequestParam Integer product){
        return orderProductService.getByProduct(product);
    }



}
