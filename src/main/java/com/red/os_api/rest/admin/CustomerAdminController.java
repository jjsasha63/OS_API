package com.red.os_api.rest.admin;

import com.red.os_api.entity.Category;
import com.red.os_api.entity.CustomerDetails;
import com.red.os_api.entity.req_resp.CustomerDetailsRequest;
import com.red.os_api.entity.req_resp.CustomerDetailsResponse;
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
    public ResponseEntity<CustomerDetailsResponse> putCustomerDetails(@RequestBody CustomerDetailsRequest customerDetailsRequest){
        return customerDetailsService.insertCustomerDetails(customerDetailsRequest);
    }

    @PostMapping("/insert-many")
    public ResponseEntity<List<CustomerDetailsResponse>> putCustomersDetails(@RequestBody CustomerDetailsRequest customerDetailsRequests) throws NoSuchFieldException {
        return customerDetailsService.insertCustomerDetails(customerDetailsRequests.getCustomerDetailsList());
    }

    @PostMapping("/deleteById")
    public ResponseEntity<String> putCustomersDetails(@RequestParam(name = "id") Integer id){
        return customerDetailsService.deleteById(id);
    }


    @PutMapping("/update-one")
    public ResponseEntity<CustomerDetailsResponse> updateCustomerDetails(@RequestBody CustomerDetailsRequest customerDetailsRequest){
        return customerDetailsService.insertCustomerDetails(customerDetailsRequest);
    }

    @PutMapping("/update-many")
    public ResponseEntity<List<CustomerDetailsResponse>> updateCustomersDetails(@RequestBody CustomerDetailsRequest customerDetailsRequests) throws NoSuchFieldException {
        return customerDetailsService.insertCustomerDetails(customerDetailsRequests.getCustomerDetailsList());
    }

    @GetMapping("/getById")
    public ResponseEntity<CustomerDetailsResponse> getCustomerDetailsById(@RequestParam Integer id){
        return customerDetailsService.getById(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CustomerDetailsResponse>> getAllCustomerDetails(){
        return customerDetailsService.getAllCustomerDetails();
    }


}
