package com.red.os_api.entity.req_resp;

import com.red.os_api.entity.DeliveryStatus;
import com.red.os_api.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    Integer order_id;

    Integer auth_id;

    LocalDateTime order_date;

    OrderStatus orderStatus;

    Integer delivery_method_id;

    DeliveryStatus deliveryStatus;

    String delivery_tracking_number;

    BigDecimal delivery_price;

    String delivery_address;

    Integer payment_method_id;

    String payment_link;

    String payment_receipt;

    BigDecimal order_price;

    String comment;


}
