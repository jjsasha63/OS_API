package com.red.os_api.entity.req_resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailsResponse {

    Integer customer_id;

    String first_name;

    String last_name;

    String shipping_address;

    String billing_address;

    String preferred_payment_method;

    String preferred_delivery_method;
    String email;

    String card_number;

    Integer auth_id;
}
