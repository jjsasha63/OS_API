package com.red.os_api.entity.req_resp;

import com.red.os_api.entity.DeliveryStatus;
import com.red.os_api.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

   OrderStatus order_status;

   Integer delivery_method_id;

   DeliveryStatus delivery_status;

   String delivery_tracking_number;

   BigDecimal delivery_price;

   String delivery_address;

   Integer payment_method_id;

   String payment_link;

   String payment_receipt;

  BigDecimal order_price;

   String comment;

}
