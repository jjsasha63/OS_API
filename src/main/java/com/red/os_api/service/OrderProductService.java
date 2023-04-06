package com.red.os_api.service;

import com.red.os_api.entity.OrderProduct;
import com.red.os_api.entity.OrderProductKey;
import com.red.os_api.entity.req_resp.OrderProductRequest;
import com.red.os_api.repository.OrderProductRepository;
import com.red.os_api.repository.OrderRepository;
import com.red.os_api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;


    public ResponseEntity<OrderProductRequest> insert(OrderProductRequest orderProductRequest){
        OrderProduct orderProduct = new OrderProduct();
        try{
            orderProduct = convertToEntity(orderProductRequest);
            orderProductRepository.save(orderProduct);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Product-Order association was added");
        return new ResponseEntity<>(convertToResponse(orderProduct),HttpStatus.OK);
    }

    public ResponseEntity<List<OrderProductRequest>> insert(List<OrderProductRequest> orderProductRequestList){
        List<OrderProduct> orderProductList = new ArrayList<>();
        try{
            orderProductList = convertToEntity(orderProductRequestList);
            orderProductRepository.saveAll(orderProductList);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Product-Order associations were added");
        return new ResponseEntity<>(convertToResponse(orderProductList),HttpStatus.OK);
    }

    public ResponseEntity<String> deleteById(Integer order_id, Integer product_id){
        try {
            orderProductRepository.deleteById(new OrderProductKey(order_id,product_id));
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.error("Product-Order association was deleted");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<OrderProductRequest> getByOrderAndProduct(Integer order_id, Integer product_id){
        OrderProduct orderProduct = new OrderProduct();
        try{
            orderProduct = orderProductRepository.findById(new OrderProductKey(order_id,product_id)).get();
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Product-Order association was retrieved");
        return new ResponseEntity<>(convertToResponse(orderProduct),HttpStatus.OK);
    }

    public ResponseEntity<List<OrderProductRequest>> getByOrder(Integer order_id){
        List<OrderProduct> orderProductList = new ArrayList<>();
        try{
            orderProductList = orderProductRepository.findAllByOrder(orderRepository.findById(order_id).get());
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Product-Order association was retrieved by order");
        return new ResponseEntity<>(convertToResponse(orderProductList),HttpStatus.OK);
    }

    public ResponseEntity<List<OrderProductRequest>> getByProduct(Integer product_id){
        List<OrderProduct> orderProductList = new ArrayList<>();
        try{
            orderProductList = orderProductRepository.findAllByProduct(productRepository.findById(product_id).get());
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Product-Order association was retrieved by product");
        return new ResponseEntity<>(convertToResponse(orderProductList),HttpStatus.OK);
    }

    public ResponseEntity<List<OrderProductRequest>> getAll(){
        List<OrderProduct> orderProductList = new ArrayList<>();
        try{
            orderProductList = orderProductRepository.findAll();
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Product-Order associations were retrieved");
        return new ResponseEntity<>(convertToResponse(orderProductList),HttpStatus.OK);
    }


    private OrderProduct convertToEntity(OrderProductRequest orderProductRequest){
        OrderProduct orderProduct = new OrderProduct();

        if(orderProductRequest.getOrder_id()==null||orderProductRequest.getProduct_id()==null) throw new IllegalArgumentException();

        if(orderProductRepository.existsById(new OrderProductKey(
                orderProductRequest.getOrder_id()
                ,orderProductRequest.getProduct_id()
        ))){
            orderProduct = orderProductRepository.findById(new OrderProductKey(
                    orderProductRequest.getOrder_id()
                    ,orderProductRequest.getProduct_id()
            )).get();
        }
        else {
            orderProduct.setProduct(productRepository.findById(orderProductRequest.getProduct_id()).get());
            orderProduct.setOrder(orderRepository.findById(orderProductRequest.getOrder_id()).get());
            orderProduct.setOrderProductKey(
                    new OrderProductKey(
                            orderProductRequest.getOrder_id()
                            ,orderProductRequest.getProduct_id()
                    ));
        }

        if(orderProductRequest.getQuantity()!=null) orderProduct.setQuantity(orderProductRequest.getQuantity());
        if(orderProductRequest.getProduct_price()!=null) orderProduct.setProduct_price(orderProductRequest.getProduct_price());
        return orderProduct;
    }

    private List<OrderProduct> convertToEntity(List<OrderProductRequest> orderProductRequestList){
        List<OrderProduct> orderProductList = new ArrayList<>();
        for(OrderProductRequest orderProductRequest: orderProductRequestList) {
            OrderProduct orderProduct = new OrderProduct();
            if(orderProductRequest.getOrder_id()==null||orderProductRequest.getProduct_id()==null) throw new IllegalArgumentException();

            if(orderProductRepository.existsById(new OrderProductKey(
                    orderProductRequest.getOrder_id()
                    ,orderProductRequest.getProduct_id()
            ))){
                orderProduct = orderProductRepository.findById(new OrderProductKey(
                        orderProductRequest.getOrder_id()
                        ,orderProductRequest.getProduct_id()
                )).get();
            }
            else {
                orderProduct.setProduct(productRepository.findById(orderProductRequest.getProduct_id()).get());
                orderProduct.setOrder(orderRepository.findById(orderProductRequest.getOrder_id()).get());
                orderProduct.setOrderProductKey(
                        new OrderProductKey(
                                orderProductRequest.getOrder_id()
                                ,orderProductRequest.getProduct_id()
                        ));
            }

            if(orderProductRequest.getQuantity()!=null) orderProduct.setQuantity(orderProductRequest.getQuantity());
            if(orderProductRequest.getProduct_price()!=null) orderProduct.setProduct_price(orderProductRequest.getProduct_price());
            orderProductList.add(orderProduct);
        }
        return orderProductList;
    }

    private OrderProductRequest convertToResponse(OrderProduct orderProduct){
        OrderProductRequest orderProductRequest = new OrderProductRequest();
        orderProductRequest.setProduct_id(orderProduct.getOrderProductKey().getProductId());
        orderProductRequest.setOrder_id(orderProduct.getOrderProductKey().getOrderId());
        orderProductRequest.setQuantity(orderProduct.getQuantity());
        orderProductRequest.setProduct_price(orderProduct.getProduct_price());
        return orderProductRequest;
    }


    private List<OrderProductRequest> convertToResponse(List<OrderProduct> orderProductList){
        List<OrderProductRequest> orderProductRequestList = new ArrayList<>();
        for(OrderProduct orderProduct: orderProductList) {
            OrderProductRequest orderProductRequest = new OrderProductRequest();
            orderProductRequest.setProduct_id(orderProduct.getOrderProductKey().getProductId());
            orderProductRequest.setOrder_id(orderProduct.getOrderProductKey().getOrderId());
            orderProductRequest.setQuantity(orderProduct.getQuantity());
            orderProductRequest.setProduct_price(orderProduct.getProduct_price());
            orderProductRequestList.add(orderProductRequest);
        }
        return orderProductRequestList;
    }

}
