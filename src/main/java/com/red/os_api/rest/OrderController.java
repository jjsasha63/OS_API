package com.red.os_api.rest;

import com.red.os_api.entity.*;
import com.red.os_api.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("store/order")
public class OrderController {

AppService<Order,Integer> orderService;
AppService<OrderProduct, OrderProductKey> orderProductService;
AppService<OrderPayment, OrderPaymentKey> orderPaymentService;
AppService<Cart, CartKey> cartService;

@Autowired
    public void setOrderService(AppService<Order, Integer> orderService) {
        this.orderService = orderService;
    }
@Autowired
    public void setOrderProductService(AppService<OrderProduct, OrderProductKey> orderProductService) {
        this.orderProductService = orderProductService;
    }
@Autowired
    public void setOrderPaymentService(AppService<OrderPayment, OrderPaymentKey> orderPaymentService) {
        this.orderPaymentService = orderPaymentService;
    }
@Autowired
    public void setCartService(AppService<Cart, CartKey> cartService) {
        this.cartService = cartService;
    }




}
