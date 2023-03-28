package com.red.os_api.entity.req_resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {

  //  Integer auth_id;

    Integer product_id;

    Integer quantity;

    List<CartRequest> cartRequestList;
}
