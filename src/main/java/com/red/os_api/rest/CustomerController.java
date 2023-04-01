package com.red.os_api.rest;

import com.red.os_api.entity.CustomerDetails;
import com.red.os_api.entity.req_resp.CartRequest;
import com.red.os_api.entity.req_resp.CartResponse;
import com.red.os_api.entity.req_resp.CustomerDetailsRequest;
import com.red.os_api.entity.req_resp.CustomerDetailsResponse;
import com.red.os_api.service.CartService;
import com.red.os_api.service.CustomerDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store/api/account")
public class CustomerController {

    @Autowired
    private final CustomerDetailsService customerDetailsService;

    @GetMapping("/getDetails")
    public ResponseEntity<CustomerDetailsResponse> getMyDetails(@NonNull HttpServletRequest request,
                                                                @NonNull HttpServletResponse response,
                                                                @NonNull FilterChain filterChain) throws ServletException, IOException {
        return customerDetailsService.getCurrentUserDetails(request,response,filterChain);
    }

    @PutMapping("/update-one")
    public ResponseEntity<CustomerDetailsResponse> updateCustomerDetails(@RequestBody CustomerDetailsRequest customerDetailsRequest,@NonNull HttpServletRequest request,
                                                                         @NonNull HttpServletResponse response,
                                                                         @NonNull FilterChain filterChain){
        return customerDetailsService.updateCurrentCustomerDetails(customerDetailsRequest,request,response,filterChain);
    }




}
