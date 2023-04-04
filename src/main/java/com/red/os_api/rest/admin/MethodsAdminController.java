package com.red.os_api.rest.admin;

import com.red.os_api.entity.DeliveryMethod;
import com.red.os_api.entity.PaymentMethod;
import com.red.os_api.entity.Product;
import com.red.os_api.entity.req_resp.DeliveryMethodResponse;
import com.red.os_api.entity.req_resp.PaymentMethodResponse;
import com.red.os_api.service.MethodsService;
import com.red.os_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/store/api/admin")
public class MethodsAdminController {

    @Autowired
    private final MethodsService methodsService;


    @PostMapping("/paymentMethod/insert-one")
    public ResponseEntity<PaymentMethodResponse> putPaymentMethod(@RequestBody PaymentMethodResponse paymentMethod){
        return methodsService.insertPaymentMethod(paymentMethod);
    }

    @GetMapping("/paymentMethod/getAll")
    public ResponseEntity<List<PaymentMethodResponse>> getPaymentMethods(){
        return methodsService.getAllPaymentMethods();
    }

    @PostMapping("/paymentMethod/insert-many")
    public ResponseEntity<List<PaymentMethodResponse>> putPaymentMethods(@RequestBody PaymentMethodResponse paymentMethods){
        return methodsService.insertPaymentMethod(paymentMethods.getPaymentMethodResponseList());
    }

    @PostMapping("/paymentMethod/deleteById")
    public ResponseEntity<String> deletePaymentMethod(@RequestParam(name = "id") Integer id){
        return methodsService.deletePaymentMethod(id);
    }

    @PutMapping("/paymentMethod/update-one")
    public  ResponseEntity<PaymentMethodResponse> updatePaymentMethod(@RequestBody PaymentMethodResponse paymentMethod){
       return methodsService.insertPaymentMethod(paymentMethod);
    }

    @PutMapping("/paymentMethod/update-many")
    public  ResponseEntity<List<PaymentMethodResponse>> updatePaymentMethods(@RequestBody PaymentMethodResponse paymentMethods){
        return methodsService.insertPaymentMethod(paymentMethods.getPaymentMethodResponseList());
    }


    @PostMapping("/deliveryMethod/insert-one")
    public ResponseEntity<DeliveryMethodResponse> putDeliveryMethod(@RequestBody DeliveryMethodResponse deliveryMethod){
        return methodsService.insertDeliveryMethod(deliveryMethod);
    }

    @PostMapping("/deliveryMethod/insert-many")
    public ResponseEntity<List<DeliveryMethodResponse>> putDeliveryMethods(@RequestBody DeliveryMethodResponse deliveryMethod){
        return methodsService.insertDeliveryMethod(deliveryMethod.getDeliveryMethodResponseList());
    }

    @PostMapping("/deliveryMethod/deleteById")
    public ResponseEntity<String> deleteDeliveryMethod(@RequestParam(name = "id") Integer id){
        return methodsService.deleteDeliveryMethod(id);
    }

    @PutMapping("/deliveryMethod/update-one")
    public  ResponseEntity<DeliveryMethodResponse> updateDeliveryMethod(@RequestBody DeliveryMethodResponse deliveryMethod){
        return methodsService.insertDeliveryMethod(deliveryMethod);
    }

    @PutMapping("deliveryMethod/update-many")
    public  ResponseEntity<List<DeliveryMethodResponse>> updateDeliveryMethods(@RequestBody DeliveryMethodResponse deliveryMethod){
        return methodsService.insertDeliveryMethod(deliveryMethod.getDeliveryMethodResponseList());
    }

    @GetMapping("deliveryMethod/getAll")
    public ResponseEntity<List<DeliveryMethodResponse>> getDeliveryMethods(){
        return methodsService.getAllDeliveryMethods();
    }

}
