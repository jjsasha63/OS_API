package com.red.os_api.rest.admin;

import com.red.os_api.entity.Category;
import com.red.os_api.entity.CustomerDetails;
import com.red.os_api.entity.req_resp.CustomerDetailsRequest;
import com.red.os_api.service.CustomerDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/store/api/admin/customer")
public class CustomerAdminController {

    @Autowired
    private final CustomerDetailsService customerDetailsService;


    @PostMapping("/insert-one")
    public ResponseEntity<CustomerDetails> putCustomerDetails(@RequestBody CustomerDetailsRequest customerDetailsRequest){
        return customerDetailsService.insertCustomerDetails(customerDetailsRequest);
    }

    @PostMapping("/insert-many")
    public ResponseEntity<List<CustomerDetails>> putCustomersDetails(@RequestBody CustomerDetailsRequest customerDetailsRequests) throws NoSuchFieldException {
        return customerDetailsService.insertCustomerDetails(customerDetailsRequests.getCustomerDetails());
    }


    @PutMapping("/update-one")
    public ResponseEntity<CustomerDetails> updateCustomerDetails(@RequestBody CustomerDetailsRequest customerDetailsRequest){
        return customerDetailsService.insertCustomerDetails(customerDetailsRequest);
    }

    @PutMapping("/update-many")
    public ResponseEntity<List<CustomerDetails>> updateCustomersDetails(@RequestBody CustomerDetailsRequest customerDetailsRequests) throws NoSuchFieldException {
        return customerDetailsService.insertCustomerDetails(customerDetailsRequests.getCustomerDetails());
    }

    @GetMapping("/getById")
    public ResponseEntity<CustomerDetails> getCustomerDetailsById(@RequestParam Integer id){
        return customerDetailsService.getById(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CustomerDetails>> getAllCustomerDetails(){
        return customerDetailsService.getAllCustomerDetails();
    }


}
