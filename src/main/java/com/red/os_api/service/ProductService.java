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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public ResponseEntity<String> addProduct(Product product){
        checkCategory(product.getCategory_name());
        product.setCategory(categoryRepository.findCategoryByName(product.getCategory_name()));
        try{
            productRepository.save(product);
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Product was successfully saved");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<String> addProduct(List<Product> products){
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
        return new ResponseEntity<>(HttpStatus.OK);
    }

   public Product getById(int id){
       Product product = new Product();
       try{
           product = productRepository.findById(id).get();
       }catch (Exception e){
           log.error(e.getMessage());
       }
       log.info("Product was successfully retrieved");
       return product;
   }

   public void deleteById(int id){
       try{
           productRepository.deleteById(id);
       }catch (Exception e){
           log.error(e.getMessage());
       }
       log.info("The product was successfully deleted");
   }

//   public Product getByName(String name){
//       Product product = new Product();
//       try{
//           product = productRepository.findByProduct_name(name).get();
//       }catch (Exception e){
//           log.error(e.getMessage());
//       }
//       log.info("Product was successfully retrieved");
//       return product;
//   }


   public ResponseEntity<String> updateProduct(Product product){
       checkCategory(product.getCategory_name());
       product.setCategory(categoryRepository.findCategoryByName(product.getCategory_name()));
       try{
           productRepository.save(product);
       }catch (IllegalArgumentException e){
           log.error(e.getMessage());
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
       log.info("Product was successfully updated");
       return new ResponseEntity<>(HttpStatus.OK);
   }


    public ResponseEntity<String> updateProduct(List<Product> productList){
        try{
            for(Product product: productList) {
                checkCategory(product.getCategory_name());
                product.setCategory(categoryRepository.findCategoryByName(product.getCategory_name()));
                productRepository.save(product);
            }
        }catch (NoSuchElementException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Products were successfully updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }

   private void checkCategory(String name){
       if(categoryRepository.findCategoryByName(name)==null)
           throw new NullPointerException("Given Category doesn't exist");
   }

   public boolean verifyProductId(Integer id){
        return productRepository.existsById(id);
   }
}
