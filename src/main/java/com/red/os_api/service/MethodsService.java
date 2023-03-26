package com.red.os_api.service;

import com.red.os_api.entity.DeliveryMethod;
import com.red.os_api.entity.PaymentMethod;
import com.red.os_api.repository.DeliveryMethodRepository;
import com.red.os_api.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class MethodsService {

    @Autowired
    private final PaymentMethodRepository paymentRepository;

    @Autowired
    private final DeliveryMethodRepository deliveryRepository;


    public ResponseEntity<PaymentMethod> insertPaymentMethod(PaymentMethod paymentMethod){
        try {
            paymentRepository.save(paymentMethod);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Payment method was successfully added");
        return new ResponseEntity<>(paymentMethod,HttpStatus.OK);
    }

    public ResponseEntity<DeliveryMethod> insertDeliveryMethod(DeliveryMethod deliveryMethod){
        try {
            deliveryRepository.save(deliveryMethod);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Delivery method was successfully added");
        return new ResponseEntity<>(deliveryMethod,HttpStatus.OK);
    }

    public ResponseEntity<List<PaymentMethod>> insertPaymentMethod(List<PaymentMethod> paymentMethods){
        try {
            for(PaymentMethod paymentMethod: paymentMethods) paymentRepository.save(paymentMethod);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Payment methods were successfully added");
        return new ResponseEntity<>(paymentMethods,HttpStatus.OK);
    }

    public ResponseEntity<List<DeliveryMethod>> insertDeliveryMethod(List<DeliveryMethod> deliveryMethods){
        try {
            for(DeliveryMethod deliveryMethod: deliveryMethods) deliveryRepository.save(deliveryMethod);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Payment methods were successfully added");
        return new ResponseEntity<>(deliveryMethods,HttpStatus.OK);
    }

    public ResponseEntity<PaymentMethod> getPaymentMethodById(Integer id){
        PaymentMethod paymentMethod = new PaymentMethod();
        try {
            paymentMethod = paymentRepository.findById(id).get();
        }catch (NoSuchElementException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Payment method was successfully retrieved");
        return new ResponseEntity<>(paymentMethod,HttpStatus.OK);
    }

    public ResponseEntity<DeliveryMethod> getDeliveryMethodById(Integer id){
        DeliveryMethod deliveryMethod = new DeliveryMethod();
        try {
            deliveryMethod = deliveryRepository.findById(id).get();
        }catch (NoSuchElementException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Delivery method was successfully retrieved");
        return new ResponseEntity<>(deliveryMethod,HttpStatus.OK);
    }

    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethods(){
        List<PaymentMethod> paymentMethod = new ArrayList<>();
        try {
            paymentMethod = paymentRepository.findAll();
        }catch (NoSuchElementException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Payment method was successfully retrieved");
        return new ResponseEntity<>(paymentMethod,HttpStatus.OK);
    }


    public ResponseEntity<List<DeliveryMethod>> getAllDeliveryMethods(){
        List<DeliveryMethod> deliveryMethods = new ArrayList<>();
        try {
            deliveryMethods = deliveryRepository.findAll();
        }catch (NoSuchElementException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Delivery method was successfully retrieved");
        return new ResponseEntity<>(deliveryMethods,HttpStatus.OK);
    }

    public ResponseEntity<String> deletePaymentMethod(Integer id){
        try {
            paymentRepository.deleteById(id);
        }catch (NoSuchElementException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Payment method was successfully deleted");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<String> deleteDeliveryMethod(Integer id){
        try {
            deliveryRepository.deleteById(id);
        }catch (NoSuchElementException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Delivery method was successfully deleted");
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<PaymentMethod> getPaymentMethodByName(String name){
        PaymentMethod paymentMethod = new PaymentMethod();
        try {
            paymentMethod = paymentRepository.getPaymentMethodByName(name);
        }catch (NoSuchElementException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Payment method was successfully retrieved");
        return new ResponseEntity<>(paymentMethod,HttpStatus.OK);
    }

    public ResponseEntity<DeliveryMethod> getDeliveryMethodByName(String name){
        DeliveryMethod deliveryMethod = new DeliveryMethod();
        try {
            deliveryMethod = deliveryRepository.getDeliveryMethodByName(name);
        }catch (NoSuchElementException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Delivery method was successfully retrieved");
        return new ResponseEntity<>(deliveryMethod,HttpStatus.OK);
    }
}
