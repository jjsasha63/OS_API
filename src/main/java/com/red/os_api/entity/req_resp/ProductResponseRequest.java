package com.red.os_api.entity.req_resp;

import com.red.os_api.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductResponseRequest {

    Integer product_id;

    String product_name;

    BigDecimal price;

    String description;

    String picture;

    Integer quantity;

    Integer category_id;

   List<ProductResponseRequest> productResponseRequestList;

}
