package com.red.os_api.rest;

import com.red.os_api.entity.Category;
import com.red.os_api.entity.DeliveryMethod;
import com.red.os_api.entity.PaymentMethod;
import com.red.os_api.entity.Product;
import com.red.os_api.entity.search.CategoryPage;
import com.red.os_api.entity.search.CategorySearchCriteria;
import com.red.os_api.entity.search.ProductPage;
import com.red.os_api.entity.search.ProductSearchCriteria;
import com.red.os_api.service.CategoryService;
import com.red.os_api.service.MethodsService;
import com.red.os_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store/api/search")
@RequiredArgsConstructor
public class SearchController {

    @Autowired
    private final ProductService productService;

    @Autowired
    private final CategoryService categoryService;

    @Autowired
    private final MethodsService methodsService;

    @GetMapping( "/filterProducts")
    public ResponseEntity<Page<Product>> searchProducts( ProductPage productPage, @RequestBody ProductSearchCriteria productSearchCriteria){
        return  new ResponseEntity<>(productService.getProducts(productPage,productSearchCriteria), HttpStatus.OK);

    }

    @GetMapping("/getProductById")
    public ResponseEntity<Product> getProductById(@RequestParam(name = "id") Integer id){
        return productService.getProductById(id);
    }

    @GetMapping("/getProducts")
    public ResponseEntity<List<Product>> getProducts(){
        return productService.getAll();
    }

    @GetMapping("/filterCategories")
    public ResponseEntity<Page<Category>> searchCategory(CategoryPage categoryPage, @RequestBody CategorySearchCriteria categorySearchCriteria){
        return new ResponseEntity<>(categoryService.getCategories(categoryPage,categorySearchCriteria),HttpStatus.OK);
    }

    @GetMapping("/getCategoryById")
    public ResponseEntity<Category> getCategoryById(@RequestParam(name = "id") Integer id){
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/getCategories")
    public ResponseEntity<List<Category>> getCategories(){
        return categoryService.getAll();
    }

    @GetMapping("/getPaymentMethodById")
    public ResponseEntity<PaymentMethod> getPaymentMethodById(@RequestParam Integer id){
        return methodsService.getPaymentMethodById(id);
    }

    @GetMapping("/getPaymentMethodByName")
    public ResponseEntity<PaymentMethod> getPaymentMethodByName(@RequestParam String name){
        return methodsService.getPaymentMethodByName(name);
    }

    @GetMapping("/getPaymentMethods")
    public ResponseEntity<List<PaymentMethod>> getPaymentMethods(){
        return methodsService.getAllPaymentMethods();
    }


    @GetMapping("/getDeliveryMethodById")
    public ResponseEntity<DeliveryMethod> getDeliveryMethodById(@RequestParam Integer id){
        return methodsService.getDeliveryMethodById(id);
    }

    @GetMapping("/getDeliveryMethodByName")
    public ResponseEntity<DeliveryMethod> getDeliveryMethodByName(@RequestParam String name){
        return methodsService.getDeliveryMethodByName(name);
    }

    @GetMapping("/getDeliveryMethods")
    public ResponseEntity<List<DeliveryMethod>> getDeliveryMethods(){
        return methodsService.getAllDeliveryMethods();
    }


}
