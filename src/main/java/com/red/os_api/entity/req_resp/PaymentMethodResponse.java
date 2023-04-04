package com.red.os_api.entity.req_resp;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodResponse {

    Integer payment_method_id;

    String name;

    String description;

    List<PaymentMethodResponse> paymentMethodResponseList;

}
