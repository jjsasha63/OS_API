package com.red.os_api.entity.req_resp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    Integer auth_id;

    String email;

    Integer product_id;

    String ProductName;

    Integer quantity;

    List<CartResponse> cartResponseList;
}
