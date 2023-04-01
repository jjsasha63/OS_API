package com.red.os_api.service;

import com.red.os_api.entity.Product;
import com.red.os_api.entity.search.ProductPage;
import com.red.os_api.entity.search.ProductSearchCriteria;
import com.red.os_api.repository.CategoryRepository;
import com.red.os_api.repository.ProductCriteriaRepository;
import com.red.os_api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private final ProductCriteriaRepository productCriteriaRepository;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final CategoryRepository categoryRepository;

    public Page<Product> getProducts(ProductPage productPage, ProductSearchCriteria productSearchCriteria){
        return productCriteriaRepository.findByFilters(productPage,productSearchCriteria);
    }

    public ResponseEntity<Product> insertProduct(Product product){
        checkCategory(product.getCategory_name());
        product.setCategory(categoryRepository.findCategoryByName(product.getCategory_name()));
        try{
            productRepository.save(product);
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Product was successfully saved");
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    public ResponseEntity<List<Product>> insertProduct(List<Product> products){
        try{
            for(Product product: products) {
                checkCategory(product.getCategory_name());
                product.setCategory(categoryRepository.findCategoryByName(product.getCategory_name()));
                productRepository.save(product);
            }
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Products were successfully saved");
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

   public ResponseEntity<Product> getProductById(int id){
       Product product = new Product();
       try{
           product = productRepository.findById(id).get();
       }catch (Exception e){
           log.error(e.getMessage());
       }
       log.info("Product was successfully retrieved");
       return new ResponseEntity<>(product,HttpStatus.OK);
   }

   public ResponseEntity<List<Product>> getAll(){
        return new ResponseEntity<>(productRepository.findAll(),HttpStatus.OK);
  }

   public ResponseEntity<String> deleteCategoryById(int id){
       try{
           productRepository.deleteById(id);
       }catch (Exception e){
           log.error(e.getMessage());
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
       log.info("The product was successfully deleted");
       return verifyProductId(id) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(HttpStatus.OK);
   }



   private void checkCategory(String name){
       if(categoryRepository.findCategoryByName(name)==null)
           throw new NullPointerException("Given Category doesn't exist");
   }

   private boolean verifyProductId(Integer id){
        return productRepository.existsById(id);
   }
}
