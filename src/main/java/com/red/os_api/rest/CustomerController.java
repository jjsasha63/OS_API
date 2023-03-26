package com.red.os_api.rest;

import com.red.os_api.entity.CustomerDetails;
import com.red.os_api.entity.req_resp.CustomerDetailsRequest;
import com.red.os_api.service.CustomerDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store/api/account")
public class CustomerController {

    @Autowired
    private CustomerDetailsService customerDetailsService;


    @GetMapping("/getDetails")
    public ResponseEntity<CustomerDetails> getMyDetails(@NonNull HttpServletRequest request,
                                                        @NonNull HttpServletResponse response,
                                                        @NonNull FilterChain filterChain) throws ServletException, IOException {
        return customerDetailsService.getCurrentUserDetails(request,response,filterChain);
    }

}
