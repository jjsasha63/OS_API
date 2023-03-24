package com.red.os_api.rest;

import com.red.os_api.entity.Product;
import com.red.os_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/store/api/admin")
public class AdminController {

    @Autowired
    private final ProductService productService;

    @PostMapping("/product/insert-one")
    public ResponseEntity<String> putProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @PostMapping("/product/insert-many")
    public ResponseEntity<String> putProducts(@RequestBody Product products){
        return productService.addProduct(products.getProducts());
    }

    @PostMapping("/product/deleteById")
    public ResponseEntity<String> deleteProduct(@RequestParam(name = "id") Integer id){
        productService.deleteById(id);
        return productService.verifyProductId(id) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("product/update")
    public  ResponseEntity<String> updateProduct(@RequestBody Product product){
       return productService.updateProduct(product);
    }

    @PutMapping("product/update-many")
    public  ResponseEntity<String> updateProducts(@RequestBody Product product){
        return productService.updateProduct(product.getProducts());
    }


}
