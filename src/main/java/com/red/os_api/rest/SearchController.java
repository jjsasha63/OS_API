package com.red.os_api.rest;

import com.red.os_api.entity.Product;
import com.red.os_api.entity.search.ProductPage;
import com.red.os_api.entity.search.ProductSearchCriteria;
import com.red.os_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store/api/search")
@RequiredArgsConstructor
public class SearchController {

    @Autowired
    private final ProductService productService;

    @GetMapping( "/products")
    public ResponseEntity<Page<Product>> searchProducts( ProductPage productPage, @RequestBody ProductSearchCriteria productSearchCriteria){
        return  new ResponseEntity<>(productService.getProducts(productPage,productSearchCriteria), HttpStatus.OK);

    }


}
