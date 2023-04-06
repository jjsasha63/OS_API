package com.red.os_api.rest.admin;

import com.red.os_api.entity.Product;
import com.red.os_api.entity.req_resp.ProductResponseRequest;
import com.red.os_api.entity.search.ProductPage;
import com.red.os_api.entity.search.ProductSearchCriteria;
import com.red.os_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ProductResponseRequest> putProduct(@RequestBody ProductResponseRequest product){
        return productService.insertProduct(product);
    }

    @PostMapping("/insert-many")
    public ResponseEntity<List<ProductResponseRequest>> putProducts(@RequestBody ProductResponseRequest products){
        return productService.insertProduct(products.getProductResponseRequestList());
    }

    @PostMapping("/deleteById")
    public ResponseEntity<String> deleteProduct(@RequestParam(name = "id") Integer id){
        return productService.deleteCategoryById(id);
    }

    @PutMapping("/update-one")
    public  ResponseEntity<ProductResponseRequest> updateProduct(@RequestBody ProductResponseRequest product){
       return productService.insertProduct(product);
    }

    @PutMapping("/update-many")
    public  ResponseEntity<List<ProductResponseRequest>> updateProducts(@RequestBody ProductResponseRequest product){
        return productService.insertProduct(product.getProductResponseRequestList());
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductResponseRequest>> getProducts(){
        return productService.getAll();
    }

    @GetMapping("/getById")
    public ResponseEntity<ProductResponseRequest> getProductById(@RequestParam(name = "id") Integer id){
        return productService.getProductById(id);
    }

    @PostMapping( "/filter")
    public ResponseEntity<Page<ProductResponseRequest>> searchProducts(ProductPage productPage, @RequestBody ProductSearchCriteria productSearchCriteria){
        return  new ResponseEntity<>(productService.getProducts(productPage,productSearchCriteria), HttpStatus.OK);

    }


}
