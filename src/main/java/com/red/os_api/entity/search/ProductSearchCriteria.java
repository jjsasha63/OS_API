package com.red.os_api.entity.search;

import com.red.os_api.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductSearchCriteria {
    private String product_name;
    private BigDecimal price_min;

    private BigDecimal price_max;

    private String category_name;

}
