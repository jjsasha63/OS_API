package com.red.os_api.service;

import com.red.os_api.encryption.AES;
import com.red.os_api.entity.*;
import com.red.os_api.entity.req_resp.OrderRequest;
import com.red.os_api.entity.req_resp.OrderResponse;
import com.red.os_api.repository.AuthRepository;
import com.red.os_api.repository.DeliveryMethodRepository;
import com.red.os_api.repository.OrderRepository;
import com.red.os_api.repository.PaymentMethodRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    @Value("${cid.order.key}")
    private final String KEY;

    private final AuthService authService;

    private final AuthRepository authRepository;

    private final DeliveryMethodRepository deliveryMethodRepository;

    private final PaymentMethodRepository paymentMethodRepository;

    private final OrderRepository orderRepository;


    public ResponseEntity<OrderResponse> insertOrder(OrderRequest orderRequest,@NonNull HttpServletRequest request,
                                                      @NonNull HttpServletResponse response,
                                                      @NonNull FilterChain filterChain){
        Order order = new Order();
        try{
            order = convertToEntity(orderRequest,request,response,filterChain);
            orderRepository.save(order);
        } catch (IllegalArgumentException | ServletException | IOException | NoSuchFieldException e){
            log.error(e.getMessage());
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("The order was inserted");
        return new ResponseEntity<>(convertToResponse(order),HttpStatus.OK);
    }

    public ResponseEntity<OrderResponse> insertOrder(OrderRequest orderRequest, Integer auth, Integer id){
        Order order = new Order();
        try{
            order = convertToEntity(orderRequest,auth, id);
            orderRepository.save(order);
        } catch (IllegalArgumentException | ServletException | IOException | NoSuchFieldException e){
            log.error(e.getMessage());
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("The order was inserted");
        return new ResponseEntity<>(convertToResponse(order),HttpStatus.OK);
    }

    public ResponseEntity<String> deleteOrderById(Integer id){
        try{
            orderRepository.deleteById(id);
        } catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("The order was deleted, id - " + id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<OrderResponse> getById(Integer id){
        Order order = new Order();
        try{
            order = orderRepository.findById(id).get();
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("The order was retrieved id - " + id);
        return new ResponseEntity<>(convertToResponse(order),HttpStatus.OK);
    }

    public ResponseEntity<OrderResponse> getById(Integer id,@NonNull HttpServletRequest request,
                                                 @NonNull HttpServletResponse response,
                                                 @NonNull FilterChain filterChain){
        Order order = new Order();
        try{
            order = orderRepository
                    .findByOrderIdAndAuth(id,authRepository
                            .findById(authService.getUserId(request,response,filterChain)).get()).get();
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("The order was retrieved id - " + id);
        return new ResponseEntity<>(convertToResponse(order),HttpStatus.OK);
    }


    public ResponseEntity<List<OrderResponse>> getAll(@NonNull HttpServletRequest request,
                                                      @NonNull HttpServletResponse response,
                                                      @NonNull FilterChain filterChain){
        List<Order> orders = new ArrayList<>();
        try{
            orders = orderRepository
                    .findAllByAuth(authRepository
                            .findById(authService.getUserId(request,response,filterChain)).get());
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Orders were retrieved");
        return new ResponseEntity<>(convertToResponse(orders),HttpStatus.OK);
    }


    public ResponseEntity<List<OrderResponse>> getByAuth(Integer id){
        List<Order> orders = new ArrayList<>();
        try{
            orders = orderRepository
                    .findAllByAuth(authRepository
                            .findById(id).get());
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Orders were retrieved");
        return new ResponseEntity<>(convertToResponse(orders),HttpStatus.OK);
    }


    public ResponseEntity<List<OrderResponse>> getAll(){
        List<Order> orders = new ArrayList<>();
        try{
            orders = orderRepository.findAll();
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Orders were retrieved");
        return new ResponseEntity<>(convertToResponse(orders),HttpStatus.OK);
    }




    private Order convertToEntity(OrderRequest orderRequest, @NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain) throws ServletException, IOException, NoSuchFieldException {
        Order order = new Order();
        order.setAuth(authRepository.findById(authService.getUserId(request,response,filterChain)).get());
        order.setOrder_date(LocalDateTime.now());
        order.setOrder_status(OrderStatus.NEW);
        order.setDeliveryMethod(deliveryMethodRepository.findById(orderRequest.getDelivery_method_id()).get());
        order.setDelivery_status(DeliveryStatus.PROCESSING);
        order.setDelivery_address(AES.encrypt(orderRequest.getDelivery_address(),KEY));
        order.setPaymentMethod(paymentMethodRepository.findById(orderRequest.getPayment_method_id()).get());
        order.setComment(orderRequest.getComment());
        return order;
    }


    private Order convertToEntity(OrderRequest orderRequest, Integer auth, Integer id) throws ServletException, IOException, NoSuchFieldException {
        Order order = new Order();
        if(id!=null&&orderRepository.existsById(id)){
            order = orderRepository.findById(id).get();
            if(orderRequest.getDelivery_method_id()!=null) order.setDeliveryMethod(deliveryMethodRepository.findById(orderRequest.getDelivery_method_id()).get());
            if(orderRequest.getPayment_method_id()!=null) order.setPaymentMethod(paymentMethodRepository.findById(orderRequest.getPayment_method_id()).get());
        }
        else if(auth!=null&&authRepository.existsById(auth)) {
            order.setAuth(authRepository.findById(auth).get());
            order.setOrder_date(LocalDateTime.now());
            if (orderRequest.getOrder_status() == null) order.setOrder_status(OrderStatus.NEW);
            order.setDeliveryMethod(deliveryMethodRepository.findById(orderRequest.getDelivery_method_id()).get());
            order.setPaymentMethod(paymentMethodRepository.findById(orderRequest.getPayment_method_id()).get());
        } else{
            throw new IllegalArgumentException();}

        if (orderRequest.getOrder_status() != null) order.setOrder_status(OrderStatus.valueOf(orderRequest.getOrder_status()));
        if(orderRequest.getDelivery_status()!=null) order.setDelivery_status(DeliveryStatus.valueOf(orderRequest.getDelivery_status()));
            if(orderRequest.getDelivery_tracking_number()!=null)order.setDelivery_tracking_number(AES.encrypt(orderRequest.getDelivery_tracking_number(), KEY));
            if(orderRequest.getDelivery_price()!=null)order.setDelivery_price(orderRequest.getDelivery_price());
            if(orderRequest.getDelivery_address()!=null)order.setDelivery_address(AES.encrypt(orderRequest.getDelivery_address(), KEY));
            if(orderRequest.getPayment_link()!=null)order.setPayment_link(AES.encrypt(orderRequest.getPayment_link(), KEY));
            if(orderRequest.getPayment_receipt()!=null)order.setPayment_reciept(AES.encrypt(orderRequest.getPayment_receipt(), KEY));
            if(orderRequest.getOrder_price()!=null)order.setOrder_price(orderRequest.getOrder_price());
            if(orderRequest.getComment()!=null)order.setComment(orderRequest.getComment());

        return order;
    }


    private List<Order> convertToEntity(List<OrderRequest> orderRequestList, @NonNull HttpServletRequest request,
                                        @NonNull HttpServletResponse response,
                                        @NonNull FilterChain filterChain) throws ServletException, IOException, NoSuchFieldException {
        List<Order> orders = new ArrayList<>();
        for(OrderRequest orderRequest: orderRequestList) {
            Order order = new Order();
            order.setAuth(authRepository.findById(authService.getUserId(request, response, filterChain)).get());
            order.setOrder_date(LocalDateTime.now());
            order.setOrder_status(OrderStatus.valueOf(orderRequest.getOrder_status()));
            order.setDeliveryMethod(deliveryMethodRepository.findById(orderRequest.getDelivery_method_id()).get());
            order.setDelivery_status(DeliveryStatus.valueOf(orderRequest.getDelivery_status()));
            order.setDelivery_tracking_number(AES.encrypt(orderRequest.getDelivery_tracking_number(),KEY));
            order.setDelivery_price(orderRequest.getDelivery_price());
            order.setDelivery_address(AES.encrypt(orderRequest.getDelivery_address(),KEY));
            order.setPaymentMethod(paymentMethodRepository.findById(orderRequest.getPayment_method_id()).get());
            order.setPayment_link(AES.encrypt(orderRequest.getPayment_link(),KEY));
            order.setPayment_reciept(AES.encrypt(orderRequest.getPayment_receipt(),KEY));
            order.setOrder_price(orderRequest.getOrder_price());
            order.setComment(orderRequest.getComment());
            orders.add(order);
        }
        return orders;
    }


    private OrderResponse convertToResponse(Order order){
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrder_id(order.getOrderId());
        orderResponse.setOrderStatus(order.getOrder_status());
        orderResponse.setComment(order.getComment());
        orderResponse.setAuth_id(order.getAuth().getId());
        orderResponse.setOrder_date(order.getOrder_date());
        orderResponse.setDelivery_address(AES.decrypt(order.getDelivery_address(),KEY));
        orderResponse.setOrder_price(order.getOrder_price());
        orderResponse.setDeliveryStatus(order.getDelivery_status());
        orderResponse.setDelivery_price(order.getDelivery_price());
        orderResponse.setPayment_link(AES.decrypt(order.getPayment_link(),KEY));
        orderResponse.setDelivery_method_id(order.getDeliveryMethod().getDelivery_method_id());
        orderResponse.setPayment_receipt(AES.decrypt(order.getPayment_reciept(),KEY));
        orderResponse.setDelivery_tracking_number(AES.decrypt(order.getDelivery_tracking_number(),KEY));
        orderResponse.setPayment_method_id(order.getPaymentMethod().getPayment_method_id());
        orderResponse.setPayment_method(order.getPaymentMethod().getName());
        orderResponse.setDelivery_method(order.getDeliveryMethod().getName());
        return orderResponse;
    }


    private List<OrderResponse> convertToResponse(List<Order> orderList){
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for(Order order: orderList) {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrder_id(order.getOrderId());
            orderResponse.setOrderStatus(order.getOrder_status());
            orderResponse.setComment(order.getComment());
            orderResponse.setAuth_id(order.getAuth().getId());
            orderResponse.setOrder_date(order.getOrder_date());
            orderResponse.setDelivery_address(AES.decrypt(order.getDelivery_address(),KEY));
            orderResponse.setOrder_price(order.getOrder_price());
            orderResponse.setDeliveryStatus(order.getDelivery_status());
            orderResponse.setDelivery_price(order.getDelivery_price());
            orderResponse.setPayment_link(AES.decrypt(order.getPayment_link(),KEY));
            orderResponse.setDelivery_method_id(order.getDeliveryMethod().getDelivery_method_id());
            orderResponse.setPayment_receipt(AES.decrypt(order.getPayment_reciept(),KEY));
            orderResponse.setDelivery_tracking_number(AES.decrypt(order.getDelivery_tracking_number(),KEY));
            orderResponse.setPayment_method_id(order.getPaymentMethod().getPayment_method_id());
            orderResponse.setPayment_method(order.getPaymentMethod().getName());
            orderResponse.setDelivery_method(order.getDeliveryMethod().getName());
            orderResponseList.add(orderResponse);
        }
        return orderResponseList;
    }

}
