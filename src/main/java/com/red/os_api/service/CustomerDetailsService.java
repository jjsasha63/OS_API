package com.red.os_api.service;

import com.red.os_api.encryption.AES;
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
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${cid.customerDetails.key}")
    private final String KEY;

    @Value("${cid.auth.key}")
    private final String SECRET;

    @Autowired
    private final CustomerDetailsRepository customerDetailsRepository;

    @Autowired
    private final AuthRepository authRepository;

    private final AuthService authService;

    @Autowired
    private final DeliveryMethodRepository deliveryMethodRepository;

    @Autowired
    private final PaymentMethodRepository paymentMethodRepository;





    public ResponseEntity<CustomerDetailsResponse> insertCustomerDetails(CustomerDetailsRequest customerDetailsRequest) {
        CustomerDetails customerDetails = new CustomerDetails();
        try {
            customerDetails = convertToEntity(customerDetailsRequest);
            customerDetailsRepository.save(customerDetails);
        } catch (IllegalArgumentException | NoSuchFieldException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Customer details were successfully inserted");
        return new ResponseEntity<>(convertToResponse(customerDetails), HttpStatus.OK);
    }

    public ResponseEntity<CustomerDetailsResponse> updateCurrentCustomerDetails(CustomerDetailsRequest customerDetailsRequest,@NonNull HttpServletRequest request,
                                                                                @NonNull HttpServletResponse response,
                                                                                @NonNull FilterChain filterChain) {
        CustomerDetails customerDetails = new CustomerDetails();
        try {
            if(customerDetailsRequest.getAuth_id()== null) customerDetailsRequest.setAuth_id(authService.getUserId(request,response,filterChain));
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
            customerDetails = customerDetailsRepository.findById(id).get();
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(convertToResponse(customerDetails),HttpStatus.OK);
    }

    public ResponseEntity<List<CustomerDetailsResponse>> getByAuth(Integer auth){
        List<CustomerDetails> customerDetails = new ArrayList<>();
        try {
            customerDetails = customerDetailsRepository.findCustomerDetailsByAuth(authRepository.findById(auth).get());
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(convertToResponse(customerDetails),HttpStatus.OK);
    }

    public ResponseEntity<CustomerDetailsResponse> getCurrentUserDetails(@NonNull HttpServletRequest request,
                                                                 @NonNull HttpServletResponse response,
                                                                 @NonNull FilterChain filterChain) throws ServletException, IOException, NoSuchFieldException {
        return new ResponseEntity<>(convertToResponse(customerDetailsRepository
                .getCustomerDetailsByAuth(authRepository
                        .findById(authService.getUserId(request,response,filterChain))
                        .get())
        ),HttpStatus.OK);
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

        if(customerDetailsRequest.getCustomer_id() != null&&customerDetailsRepository.existsById(customerDetailsRequest.getCustomer_id())){
            customerDetails = customerDetailsRepository.findById(customerDetailsRequest.getCustomer_id()).get();
            if (customerDetailsRequest.getAuth_id() != null
                    && isUserAccountExist(customerDetailsRequest.getAuth_id()))
            customerDetails.setAuth(authRepository.findById(customerDetailsRequest.getAuth_id()).get());

            if (customerDetailsRequest.getPreferred_payment_method() != null
                    && !isPaymentMethodExist(customerDetailsRequest.getPreferred_delivery_method()))
                throw new NoSuchFieldException();
            if (customerDetailsRequest.getPreferred_delivery_method() != null
                    && !isDeliveryMethodExist(customerDetailsRequest.getPreferred_delivery_method()))
                throw new NoSuchFieldException();

            if (customerDetailsRequest.getPreferred_payment_method() != null
                    && !isPaymentMethodExist(customerDetailsRequest.getPreferred_delivery_method()))
                throw new NoSuchFieldException();
            if (customerDetailsRequest.getPreferred_delivery_method() != null
                    && !isDeliveryMethodExist(customerDetailsRequest.getPreferred_delivery_method()))
                throw new NoSuchFieldException();
            if (customerDetailsRequest.getShipping_address() != null)
                customerDetails.setShipping_address(AES.encrypt(customerDetailsRequest.getShipping_address(),KEY));
            if (customerDetailsRequest.getBilling_address() != null)
                customerDetails.setBilling_address(AES.encrypt(customerDetailsRequest.getBilling_address(),KEY));
            if (customerDetailsRequest.getPreferred_payment_method() != null)
                customerDetails.setPreferred_payment_method(paymentMethodRepository.getReferenceById(customerDetailsRequest.getPreferred_payment_method()));
            if (customerDetailsRequest.getPreferred_delivery_method() != null)
                customerDetails.setPreferred_delivery_method(deliveryMethodRepository.getReferenceById(customerDetailsRequest.getPreferred_delivery_method()));
            if(customerDetailsRequest.getCard_number()!=null)
            customerDetails.setCard_number(AES.encrypt(customerDetailsRequest.getCard_number().toString(),KEY));
        }
        else{
        if (customerDetailsRequest.getPreferred_payment_method() != null
                && !isPaymentMethodExist(customerDetailsRequest.getPreferred_delivery_method()))
            throw new NoSuchFieldException();
        if (customerDetailsRequest.getPreferred_delivery_method() != null
                && !isDeliveryMethodExist(customerDetailsRequest.getPreferred_delivery_method()))
            throw new NoSuchFieldException();


        if (customerDetailsRequest.getAuth_id() != null
                && isUserAccountExist(customerDetailsRequest.getAuth_id())) {
            if (customerDetailsRequest.getCustomer_id()!=null)
                customerDetails.setCustomer_id(customerDetailsRequest.getCustomer_id()   );
            customerDetails.setAuth(authRepository.findById(customerDetailsRequest.getAuth_id()).get());
            if (customerDetailsRequest.getShipping_address() != null)
                customerDetails.setShipping_address(AES.encrypt(customerDetailsRequest.getShipping_address(),KEY));
            if (customerDetailsRequest.getBilling_address() != null)
                customerDetails.setBilling_address(AES.encrypt(customerDetailsRequest.getBilling_address(),KEY));
                customerDetails.setPreferred_payment_method(paymentMethodRepository.getReferenceById(customerDetailsRequest.getPreferred_payment_method()));
                customerDetails.setPreferred_delivery_method(deliveryMethodRepository.getReferenceById(customerDetailsRequest.getPreferred_delivery_method()));
            if(customerDetailsRequest.getCard_number()!=null)
                customerDetails.setCard_number(AES.encrypt(customerDetailsRequest.getCard_number().toString(),KEY));
        } else
            throw new IllegalArgumentException();}

        return customerDetails;


    }

    private CustomerDetailsResponse convertToResponse(CustomerDetails customerDetails){
        CustomerDetailsResponse customerDetailsResponse = new CustomerDetailsResponse();
        customerDetailsResponse.setCustomer_id(customerDetails.getCustomer_id());
        customerDetailsResponse.setBilling_address(AES.decrypt(customerDetails.getBilling_address(),KEY));
        customerDetailsResponse.setShipping_address(AES.decrypt(customerDetails.getShipping_address(),KEY));
        customerDetailsResponse.setPreferred_delivery_method(customerDetails.getPreferred_delivery_method().getName());
        customerDetailsResponse.setPreferred_payment_method(customerDetails.getPreferred_payment_method().getName());
        customerDetailsResponse.setAuth_id(customerDetails.getAuth().getId());
        customerDetailsResponse.setEmail(customerDetails.getAuth().getEmail());
        customerDetailsResponse.setCard_number(AES.decrypt(customerDetails.getCard_number(),KEY));
        customerDetailsResponse.setFirst_name(AES.decrypt(customerDetails.getAuth().getFirst_name(),SECRET));
        customerDetailsResponse.setLast_name(AES.decrypt(customerDetails.getAuth().getLast_name(),SECRET));
        return customerDetailsResponse;
    }

    private List<CustomerDetailsResponse> convertToResponse(List<CustomerDetails> customersDetails){
        List<CustomerDetailsResponse> customerDetailsResponses = new ArrayList<>();
        for(CustomerDetails customerDetails: customersDetails) {
            CustomerDetailsResponse customerDetailsResponse = new CustomerDetailsResponse();
            customerDetailsResponse.setCustomer_id(customerDetails.getCustomer_id());
            customerDetailsResponse.setBilling_address(AES.decrypt(customerDetails.getBilling_address(),KEY));
            customerDetailsResponse.setShipping_address(AES.decrypt(customerDetails.getShipping_address(),KEY));
            customerDetailsResponse.setPreferred_delivery_method(customerDetails.getPreferred_delivery_method().getName());
            customerDetailsResponse.setPreferred_payment_method(customerDetails.getPreferred_payment_method().getName());
            customerDetailsResponse.setAuth_id(customerDetails.getAuth().getId());
            customerDetailsResponse.setEmail(customerDetails.getAuth().getEmail());
            customerDetailsResponse.setCard_number(AES.decrypt(customerDetails.getCard_number(),KEY));
            customerDetailsResponse.setFirst_name(AES.decrypt(customerDetails.getAuth().getFirst_name(),SECRET));
            customerDetailsResponse.setLast_name(AES.decrypt(customerDetails.getAuth().getLast_name(),SECRET));
            customerDetailsResponses.add(customerDetailsResponse);
        }
        return customerDetailsResponses;
    }

}
