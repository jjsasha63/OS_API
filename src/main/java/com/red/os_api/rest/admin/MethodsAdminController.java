package com.red.os_api.rest.admin;

import com.red.os_api.entity.DeliveryMethod;
import com.red.os_api.entity.PaymentMethod;
import com.red.os_api.entity.Product;
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
    public ResponseEntity<PaymentMethod> putPaymentMethod(@RequestBody PaymentMethod paymentMethod){
        return methodsService.insertPaymentMethod(paymentMethod);
    }

    @PostMapping("/paymentMethod/insert-many")
    public ResponseEntity<List<PaymentMethod>> putPaymentMethods(@RequestBody PaymentMethod paymentMethods){
        return methodsService.insertPaymentMethod(paymentMethods.getPaymentMethods());
    }

    @PostMapping("/paymentMethod/deleteById")
    public ResponseEntity<String> deletePaymentMethod(@RequestParam(name = "id") Integer id){
        return methodsService.deletePaymentMethod(id);
    }

    @PutMapping("/paymentMethod/update-one")
    public  ResponseEntity<PaymentMethod> updatePaymentMethod(@RequestBody PaymentMethod paymentMethod){
       return methodsService.insertPaymentMethod(paymentMethod);
    }

    @PutMapping("/paymentMethod/update-many")
    public  ResponseEntity<List<PaymentMethod>> updatePaymentMethods(@RequestBody PaymentMethod paymentMethods){
        return methodsService.insertPaymentMethod(paymentMethods.getPaymentMethods());
    }


    @PostMapping("/deliveryMethod/insert-one")
    public ResponseEntity<DeliveryMethod> putDeliveryMethod(@RequestBody DeliveryMethod deliveryMethod){
        return methodsService.insertDeliveryMethod(deliveryMethod);
    }

    @PostMapping("/deliveryMethod/insert-many")
    public ResponseEntity<List<DeliveryMethod>> putDeliveryMethods(@RequestBody DeliveryMethod deliveryMethod){
        return methodsService.insertDeliveryMethod(deliveryMethod.getDeliveryMethods());
    }

    @PostMapping("/deliveryMethod/deleteById")
    public ResponseEntity<String> deleteDeliveryMethod(@RequestParam(name = "id") Integer id){
        return methodsService.deleteDeliveryMethod(id);
    }

    @PutMapping("/deliveryMethod/update-one")
    public  ResponseEntity<DeliveryMethod> updateDeliveryMethod(@RequestBody DeliveryMethod deliveryMethod){
        return methodsService.insertDeliveryMethod(deliveryMethod);
    }

    @PutMapping("deliveryMethod/update-many")
    public  ResponseEntity<List<DeliveryMethod>> updateDeliveryMethods(@RequestBody DeliveryMethod deliveryMethod){
        return methodsService.insertDeliveryMethod(deliveryMethod.getDeliveryMethods());
    }

}
