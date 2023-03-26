package com.red.os_api.service;

import com.red.os_api.entity.Auth;
import com.red.os_api.entity.CustomerDetails;
import com.red.os_api.entity.Role;
import com.red.os_api.entity.req_resp.CustomerDetailsRequest;
import com.red.os_api.repository.AuthRepository;
import com.red.os_api.repository.CustomerDetailsRepository;
import com.red.os_api.repository.DeliveryMethodRepository;
import com.red.os_api.repository.PaymentMethodRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerDetailsService {

    @Autowired
    private final CustomerDetailsRepository customerDetailsRepository;

    @Autowired
    private final AuthRepository authRepository;

    @Autowired
    private final DeliveryMethodRepository deliveryMethodRepository;

    @Autowired
    private final PaymentMethodRepository paymentMethodRepository;

    private final JwtService jwtService;


    public ResponseEntity<CustomerDetails> insertCustomerDetails(CustomerDetailsRequest customerDetailsRequest) {
        CustomerDetails customerDetails = new CustomerDetails();
        try {
            customerDetails = convertToEntity(customerDetailsRequest);
            customerDetailsRepository.save(customerDetails);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Customer details were successfully inserted");
        return new ResponseEntity<>(customerDetails, HttpStatus.OK);
    }

    public ResponseEntity<List<CustomerDetails>> insertCustomerDetails(List<CustomerDetailsRequest> customerDetailsRequests) throws NoSuchFieldException {
        List<CustomerDetails> customerDetailsList = new ArrayList<>();
        try {
            for (CustomerDetailsRequest customerDetailsRequest : customerDetailsRequests) {
                customerDetailsList.add(convertToEntity(customerDetailsRequest));
            }
            customerDetailsRepository.saveAll(customerDetailsList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Customers details were successfully inserted");
        return new ResponseEntity<>(customerDetailsList, HttpStatus.OK);
    }

//    public ResponseEntity<String> deleteById(Integer id) {
//        //TODO Deletion in case it needed
//    }


    public ResponseEntity<List<CustomerDetails>> getAllCustomerDetails(){
        return new ResponseEntity<>(customerDetailsRepository.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<CustomerDetails> getById(Integer id){
        CustomerDetails customerDetails = new CustomerDetails();
        try {
            customerDetails = customerDetailsRepository.getReferenceById(id);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(customerDetails,HttpStatus.OK);
    }

    public ResponseEntity<CustomerDetails> getCurrentUserDetails(@NonNull HttpServletRequest request,
                                                                 @NonNull HttpServletResponse response,
                                                                 @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(customerDetailsRepository
                .getReferenceById(authRepository
                        .findByEmail(jwtService
                                .getUsername(authHeader
                                        .substring(7)))
                        .get()
                        .getId()),HttpStatus.OK);
    }

    private boolean isUserAccountExist(Integer id) {
        return authRepository.existsByIdAndRoleIs(id, Role.CUSTOMER);
    }

    private boolean isPaymentMethodExist(Integer id) {
        return paymentMethodRepository.existsById(id);
    }

    private boolean isDeliveryMethodExist(Integer id) {
        return deliveryMethodRepository.existsById(id);
    }

    private CustomerDetails convertToEntity(CustomerDetailsRequest customerDetailsRequest) throws NoSuchFieldException {
        CustomerDetails customerDetails = new CustomerDetails();
        if (customerDetailsRequest.getCustomer_id() != null
                && isUserAccountExist(customerDetailsRequest.getCustomer_id())) {

            if (customerDetailsRequest.getPreferred_payment_method() != null
                    && !isPaymentMethodExist(customerDetailsRequest.getPreferred_delivery_method()))
                throw new NoSuchFieldException();
            if (customerDetailsRequest.getPreferred_delivery_method() != null
                    && !isDeliveryMethodExist(customerDetailsRequest.getPreferred_delivery_method()))
                throw new NoSuchFieldException();

            customerDetails.setCustomer_id(customerDetailsRequest.getCustomer_id());
          //  customerDetails.setAuth_id(authRepository.findById(customerDetailsRequest.getCustomer_id()).get());
            if (customerDetailsRequest.getShipping_address() != null)
                customerDetails.setShipping_address(customerDetailsRequest.getShipping_address());
            if (customerDetailsRequest.getBilling_address() != null)
                customerDetails.setBilling_address(customerDetailsRequest.getBilling_address());
            if (customerDetailsRequest.getPreferred_payment_method() != null)
                customerDetails.setPreferred_payment_method(paymentMethodRepository.getReferenceById(customerDetailsRequest.getPreferred_payment_method()));
            if (customerDetailsRequest.getPreferred_delivery_method() != null)
                customerDetails.setPreferred_delivery_method(deliveryMethodRepository.getReferenceById(customerDetailsRequest.getPreferred_delivery_method()));

        }

        return customerDetails;


    }
}
