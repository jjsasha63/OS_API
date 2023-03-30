package com.red.os_api.service;

import com.red.os_api.entity.Auth;
import com.red.os_api.entity.CustomerDetails;
import com.red.os_api.entity.Role;
import com.red.os_api.entity.req_resp.CustomerDetailsRequest;
import com.red.os_api.entity.req_resp.CustomerDetailsResponse;
import com.red.os_api.repository.*;
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

    private final TokenRepository tokenRepository;


    public ResponseEntity<CustomerDetailsResponse> insertCustomerDetails(CustomerDetailsRequest customerDetailsRequest) {
        CustomerDetails customerDetails = new CustomerDetails();
        try {
            customerDetails = convertToEntity(customerDetailsRequest);
            customerDetailsRepository.save(customerDetails);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Customer details were successfully inserted");
        return new ResponseEntity<>(convertToResponse(customerDetails), HttpStatus.OK);
    }

    public ResponseEntity<List<CustomerDetailsResponse>> insertCustomerDetails(List<CustomerDetailsRequest> customerDetailsRequests) throws NoSuchFieldException {
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
        return new ResponseEntity<>(convertToResponse(customerDetailsList), HttpStatus.OK);
    }

    public ResponseEntity<String> deleteById(Integer id) {
        try{
            customerDetailsRepository.deleteById(id);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("The customer details were successfully deleted");
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<List<CustomerDetailsResponse>> getAllCustomerDetails(){
        return new ResponseEntity<>(convertToResponse(customerDetailsRepository.findAll()),HttpStatus.OK);
    }

    public ResponseEntity<CustomerDetailsResponse> getById(Integer id){
        CustomerDetails customerDetails = new CustomerDetails();
        try {
            customerDetails = customerDetailsRepository.getReferenceById(id);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(convertToResponse(customerDetails),HttpStatus.OK);
    }

    public ResponseEntity<CustomerDetailsResponse> getCurrentUserDetails(@NonNull HttpServletRequest request,
                                                                 @NonNull HttpServletResponse response,
                                                                 @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(!tokenRepository.existsTokenByTokenAndRevokedAndExpired(authHeader
                .substring(7),false,false)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(convertToResponse(customerDetailsRepository
                .findCustomerDetailsByAuth(authRepository
                        .findByEmail(jwtService
                                .getUsername(authHeader
                                        .substring(7)))
                        .get())
                .get()),HttpStatus.OK);
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
        if (customerDetailsRequest.getAuth_id() != null
                && isUserAccountExist(customerDetailsRequest.getAuth_id())) {

            if (customerDetailsRequest.getPreferred_payment_method() != null
                    && !isPaymentMethodExist(customerDetailsRequest.getPreferred_delivery_method()))
                throw new NoSuchFieldException();
            if (customerDetailsRequest.getPreferred_delivery_method() != null
                    && !isDeliveryMethodExist(customerDetailsRequest.getPreferred_delivery_method()))
                throw new NoSuchFieldException();
            if (customerDetailsRequest.getCustomer_id()!=null) customerDetails.setCustomer_id(customerDetailsRequest.getCustomer_id()   );
            customerDetails.setAuth(authRepository.findById(customerDetailsRequest.getAuth_id()).get());
            if (customerDetailsRequest.getShipping_address() != null)
                customerDetails.setShipping_address(customerDetailsRequest.getShipping_address());
            if (customerDetailsRequest.getBilling_address() != null)
                customerDetails.setBilling_address(customerDetailsRequest.getBilling_address());
            if (customerDetailsRequest.getPreferred_payment_method() != null)
                customerDetails.setPreferred_payment_method(paymentMethodRepository.getReferenceById(customerDetailsRequest.getPreferred_payment_method()));
            if (customerDetailsRequest.getPreferred_delivery_method() != null)
                customerDetails.setPreferred_delivery_method(deliveryMethodRepository.getReferenceById(customerDetailsRequest.getPreferred_delivery_method()));
            customerDetails.setCard_number(customerDetailsRequest.getCard_number());
        }

        return customerDetails;


    }

    private CustomerDetailsResponse convertToResponse(CustomerDetails customerDetails){
        CustomerDetailsResponse customerDetailsResponse = new CustomerDetailsResponse();
        customerDetailsResponse.setCustomer_id(customerDetails.getCustomer_id());
        customerDetailsResponse.setBilling_address(customerDetails.getBilling_address());
        customerDetailsResponse.setShipping_address(customerDetails.getShipping_address());
        customerDetailsResponse.setPreferred_delivery_method(customerDetails.getPreferred_delivery_method().getName());
        customerDetailsResponse.setPreferred_payment_method(customerDetails.getPreferred_payment_method().getName());
        customerDetailsResponse.setAuth_id(customerDetails.getAuth().getId());
        customerDetailsResponse.setEmail(customerDetails.getAuth().getEmail());
        customerDetailsResponse.setCard_number(customerDetails.getCard_number());
        return customerDetailsResponse;
    }

    private List<CustomerDetailsResponse> convertToResponse(List<CustomerDetails> customersDetails){
        List<CustomerDetailsResponse> customerDetailsResponses = new ArrayList<>();
        for(CustomerDetails customerDetails: customersDetails) {
            CustomerDetailsResponse customerDetailsResponse = new CustomerDetailsResponse();
            customerDetailsResponse.setCustomer_id(customerDetails.getCustomer_id());
            customerDetailsResponse.setBilling_address(customerDetails.getBilling_address());
            customerDetailsResponse.setShipping_address(customerDetails.getShipping_address());
            customerDetailsResponse.setPreferred_delivery_method(customerDetails.getPreferred_delivery_method().getName());
            customerDetailsResponse.setPreferred_payment_method(customerDetails.getPreferred_payment_method().getName());
            customerDetailsResponse.setAuth_id(customerDetails.getAuth().getId());
            customerDetailsResponse.setEmail(customerDetails.getAuth().getEmail());
            customerDetailsResponse.setCard_number(customerDetails.getCard_number());
            customerDetailsResponses.add(customerDetailsResponse);
        }
        return customerDetailsResponses;
    }

}
