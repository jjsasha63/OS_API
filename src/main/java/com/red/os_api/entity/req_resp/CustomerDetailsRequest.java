package com.red.os_api.entity.req_resp;

import com.red.os_api.entity.Auth;
import com.red.os_api.entity.CustomerDetails;
import com.red.os_api.entity.DeliveryMethod;
import com.red.os_api.entity.PaymentMethod;
import io.swagger.models.auth.In;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailsRequest {

    Integer customer_id;

    Integer auth_id;

    String shipping_address;

    String billing_address;

    Integer card_number;


    Integer preferred_payment_method;

    Integer preferred_delivery_method;


    List<CustomerDetailsRequest> customerDetailsList;

}
