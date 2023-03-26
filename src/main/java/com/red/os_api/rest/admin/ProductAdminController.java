package com.red.os_api.rest.admin;

import com.red.os_api.entity.Product;
import com.red.os_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/store/api/admin/product")
public class ProductAdminController {

    @Autowired
    private final ProductService productService;


    @PostMapping("/insert-one")
    public ResponseEntity<Product> putProduct(@RequestBody Product product){
        return productService.insertProduct(product);
    }

    @PostMapping("/insert-many")
    public ResponseEntity<List<Product>> putProducts(@RequestBody Product products){
        return productService.insertProduct(products.getProducts());
    }

    @PostMapping("/deleteById")
    public ResponseEntity<String> deleteProduct(@RequestParam(name = "id") Integer id){
        return productService.deleteCategoryById(id);
    }

    @PutMapping("/update-one")
    public  ResponseEntity<Product> updateProduct(@RequestBody Product product){
       return productService.insertProduct(product);
    }

    @PutMapping("/update-many")
    public  ResponseEntity<List<Product>> updateProducts(@RequestBody Product product){
        return productService.insertProduct(product.getProducts());
    }


}
