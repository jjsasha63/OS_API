package com.red.os_api.entity.req_resp;

import com.red.os_api.entity.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductRequest {

    Integer order_id;

    Integer product_id;

    Integer quantity;

    BigDecimal product_price;

    List<OrderProductRequest> orderProductList;

}
