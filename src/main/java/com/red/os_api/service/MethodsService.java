package com.red.os_api.service;

import com.red.os_api.entity.DeliveryMethod;
import com.red.os_api.entity.PaymentMethod;
import com.red.os_api.entity.req_resp.DeliveryMethodResponse;
import com.red.os_api.entity.req_resp.PaymentMethodResponse;
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


    public ResponseEntity<PaymentMethodResponse> insertPaymentMethod(PaymentMethodResponse paymentMethodResponse){
        PaymentMethod paymentMethod = new PaymentMethod();
        try {
            paymentMethod = fromResponseP(paymentMethodResponse);
            paymentRepository.save(paymentMethod);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Payment method was successfully added");
        return new ResponseEntity<>(toResponseP(paymentMethod),HttpStatus.OK);
    }

    public ResponseEntity<DeliveryMethodResponse> insertDeliveryMethod(DeliveryMethodResponse deliveryMethodResponse){
        DeliveryMethod deliveryMethod = new DeliveryMethod();
        try {
            deliveryMethod = fromResponseD(deliveryMethodResponse);
            deliveryRepository.save(deliveryMethod);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Delivery method was successfully added");
        return new ResponseEntity<>(toResponseD(deliveryMethod),HttpStatus.OK);
    }

    public ResponseEntity<List<PaymentMethodResponse>> insertPaymentMethod(List<PaymentMethodResponse> paymentMethodResponseList){
        List<PaymentMethod> paymentMethodList = new ArrayList<>();
        try {
            paymentMethodList = fromResponseP(paymentMethodResponseList);
                paymentRepository.saveAll(paymentMethodList);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Payment methods were successfully added");
        return new ResponseEntity<>(toResponseP(paymentMethodList),HttpStatus.OK);
    }

    public ResponseEntity<List<DeliveryMethodResponse>> insertDeliveryMethod(List<DeliveryMethodResponse> deliveryMethodResponseList){
        List<DeliveryMethod> deliveryMethodList = new ArrayList<>();
        try {
            deliveryMethodList = fromResponseD(deliveryMethodResponseList);
            deliveryRepository.saveAll(deliveryMethodList);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Payment methods were successfully added");
        return new ResponseEntity<>(toResponseD(deliveryMethodList),HttpStatus.OK);
    }

    public ResponseEntity<PaymentMethodResponse> getPaymentMethodById(Integer id){
        PaymentMethod paymentMethod = new PaymentMethod();
        try {
            paymentMethod = paymentRepository.findById(id).get();
        }catch (NoSuchElementException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Payment method was successfully retrieved");
        return new ResponseEntity<>(toResponseP(paymentMethod),HttpStatus.OK);
    }

    public ResponseEntity<DeliveryMethodResponse> getDeliveryMethodById(Integer id){
        DeliveryMethod deliveryMethod = new DeliveryMethod();
        try {
            deliveryMethod = deliveryRepository.findById(id).get();
        }catch (NoSuchElementException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Delivery method was successfully retrieved");
        return new ResponseEntity<>(toResponseD(deliveryMethod),HttpStatus.OK);
    }

    public ResponseEntity<List<PaymentMethodResponse>> getAllPaymentMethods(){
        List<PaymentMethod> paymentMethod = new ArrayList<>();
        try {
            paymentMethod = paymentRepository.findAll();
        }catch (NoSuchElementException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Payment method was successfully retrieved");
        return new ResponseEntity<>(toResponseP(paymentMethod),HttpStatus.OK);
    }


    public ResponseEntity<List<DeliveryMethodResponse>> getAllDeliveryMethods(){
        List<DeliveryMethod> deliveryMethods = new ArrayList<>();
        try {
            deliveryMethods = deliveryRepository.findAll();
        }catch (NoSuchElementException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Delivery method was successfully retrieved");
        return new ResponseEntity<>(toResponseD(deliveryMethods),HttpStatus.OK);
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


    public ResponseEntity<PaymentMethodResponse> getPaymentMethodByName(String name){
        PaymentMethod paymentMethod = new PaymentMethod();
        try {
            paymentMethod = paymentRepository.getPaymentMethodByName(name);
        }catch (NoSuchElementException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Payment method was successfully retrieved");
        return new ResponseEntity<>(toResponseP(paymentMethod),HttpStatus.OK);
    }

    public ResponseEntity<DeliveryMethodResponse> getDeliveryMethodByName(String name){
        DeliveryMethod deliveryMethod = new DeliveryMethod();
        try {
            deliveryMethod = deliveryRepository.getDeliveryMethodByName(name);
        }catch (NoSuchElementException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Delivery method was successfully retrieved");
        return new ResponseEntity<>(toResponseD(deliveryMethod),HttpStatus.OK);
    }


    private PaymentMethod fromResponseP(PaymentMethodResponse paymentMethodResponse){
        PaymentMethod paymentMethod = new PaymentMethod();
        if (paymentMethodResponse.getPayment_method_id()!=null&&paymentRepository.existsById(paymentMethodResponse.getPayment_method_id())){
            paymentMethod = paymentRepository.findById(paymentMethodResponse.getPayment_method_id()).get();
            paymentMethod.setPayment_method_id(paymentMethodResponse.getPayment_method_id());
            if(paymentMethodResponse.getName()!=null) paymentMethod.setName(paymentMethodResponse.getName());
            if(paymentMethodResponse.getDescription()!=null) paymentMethod.setDescription(paymentMethodResponse.getDescription());
        }
        else {
            paymentMethod.setName(paymentMethodResponse.getName());
            paymentMethod.setDescription(paymentMethodResponse.getDescription());
        }
        return paymentMethod;
    }

    private List<PaymentMethod> fromResponseP(List<PaymentMethodResponse> paymentMethodResponseList){
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        for(PaymentMethodResponse paymentMethodResponse:paymentMethodResponseList) {
            PaymentMethod paymentMethod = new PaymentMethod();
            if (paymentMethodResponse.getPayment_method_id()!=null&&paymentRepository.existsById(paymentMethodResponse.getPayment_method_id())){
                paymentMethod = paymentRepository.findById(paymentMethodResponse.getPayment_method_id()).get();
                paymentMethod.setPayment_method_id(paymentMethodResponse.getPayment_method_id());
                if(paymentMethodResponse.getName()!=null) paymentMethod.setName(paymentMethodResponse.getName());
                if(paymentMethodResponse.getDescription()!=null) paymentMethod.setDescription(paymentMethodResponse.getDescription());
            }
            else {
                paymentMethod.setName(paymentMethodResponse.getName());
                paymentMethod.setDescription(paymentMethodResponse.getDescription());
            }
            paymentMethods.add(paymentMethod);
        }
        return paymentMethods;
    }

    private DeliveryMethod fromResponseD(DeliveryMethodResponse deliveryMethodResponse){
        DeliveryMethod deliveryMethod = new DeliveryMethod();
        if (deliveryMethodResponse.getDelivery_method_id()!=null&&deliveryRepository.existsById(deliveryMethodResponse.getDelivery_method_id())){
            deliveryMethod = deliveryRepository.findById(deliveryMethodResponse.getDelivery_method_id()).get();
            deliveryMethod.setDelivery_method_id(deliveryMethodResponse.getDelivery_method_id());
            if(deliveryMethodResponse.getName()!=null) deliveryMethod.setName(deliveryMethodResponse.getName());
            if(deliveryMethodResponse.getDescription()!=null) deliveryMethod.setDescription(deliveryMethodResponse.getDescription());
        }
        else {
            deliveryMethod.setName(deliveryMethodResponse.getName());
            deliveryMethod.setDescription(deliveryMethodResponse.getDescription());
        }
        return deliveryMethod;
    }

    private List<DeliveryMethod> fromResponseD(List<DeliveryMethodResponse> deliveryMethodResponseList){
        List<DeliveryMethod> deliveryMethods = new ArrayList<>();
        for(DeliveryMethodResponse deliveryMethodResponse:deliveryMethodResponseList) {
            DeliveryMethod deliveryMethod = new DeliveryMethod();
            if (deliveryMethodResponse.getDelivery_method_id()!=null&&deliveryRepository.existsById(deliveryMethodResponse.getDelivery_method_id())){
                deliveryMethod = deliveryRepository.findById(deliveryMethodResponse.getDelivery_method_id()).get();
                deliveryMethod.setDelivery_method_id(deliveryMethodResponse.getDelivery_method_id());
                if(deliveryMethodResponse.getName()!=null) deliveryMethod.setName(deliveryMethodResponse.getName());
                if(deliveryMethodResponse.getDescription()!=null) deliveryMethod.setDescription(deliveryMethodResponse.getDescription());
            }
            else {
                deliveryMethod.setName(deliveryMethodResponse.getName());
                deliveryMethod.setDescription(deliveryMethodResponse.getDescription());
            }
            deliveryMethods.add(deliveryMethod);
        }
        return deliveryMethods;
    }

    private PaymentMethodResponse toResponseP(PaymentMethod paymentMethod){
        PaymentMethodResponse paymentMethodResponse = new PaymentMethodResponse();
        paymentMethodResponse.setPayment_method_id(paymentMethod.getPayment_method_id());
        paymentMethodResponse.setName(paymentMethod.getName());
        paymentMethodResponse.setDescription(paymentMethod.getDescription());
        return paymentMethodResponse;
    }

    private List<PaymentMethodResponse> toResponseP(List<PaymentMethod> paymentMethodList){
        List<PaymentMethodResponse> paymentMethodResponseList = new ArrayList<>();
        for(PaymentMethod paymentMethod: paymentMethodList) {
            PaymentMethodResponse paymentMethodResponse = new PaymentMethodResponse();
            paymentMethodResponse.setPayment_method_id(paymentMethod.getPayment_method_id());
            paymentMethodResponse.setName(paymentMethod.getName());
            paymentMethodResponse.setDescription(paymentMethod.getDescription());
            paymentMethodResponseList.add(paymentMethodResponse);
        }
        return paymentMethodResponseList;
    }

    private DeliveryMethodResponse toResponseD(DeliveryMethod deliveryMethod){
        DeliveryMethodResponse deliveryMethodResponse = new DeliveryMethodResponse();
        deliveryMethodResponse.setDelivery_method_id(deliveryMethod.getDelivery_method_id());
        deliveryMethodResponse.setName(deliveryMethod.getName());
        deliveryMethodResponse.setDescription(deliveryMethod.getDescription());
        return deliveryMethodResponse;
    }

    private List<DeliveryMethodResponse> toResponseD(List<DeliveryMethod> deliveryMethodList){
        List<DeliveryMethodResponse> deliveryMethodResponseList = new ArrayList<>();
        for(DeliveryMethod deliveryMethod: deliveryMethodList) {
            DeliveryMethodResponse deliveryMethodResponse = new DeliveryMethodResponse();
            deliveryMethodResponse.setDelivery_method_id(deliveryMethod.getDelivery_method_id());
            deliveryMethodResponse.setName(deliveryMethod.getName());
            deliveryMethodResponse.setDescription(deliveryMethod.getDescription());
            deliveryMethodResponseList.add(deliveryMethodResponse);
        }
        return deliveryMethodResponseList;
    }
}
