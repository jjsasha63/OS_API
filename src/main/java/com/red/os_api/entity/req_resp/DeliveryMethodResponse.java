package com.red.os_api.entity.req_resp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryMethodResponse {

    Integer delivery_method_id;

    String name;

    String description;

    List<DeliveryMethodResponse> deliveryMethodResponseList;
}
