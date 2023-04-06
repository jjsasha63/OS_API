package com.red.os_api.service;

import com.red.os_api.entity.Product;
import com.red.os_api.entity.req_resp.ProductResponseRequest;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Page<ProductResponseRequest> getProducts(ProductPage productPage, ProductSearchCriteria productSearchCriteria){
        Page<Product> page = productCriteriaRepository.findByFilters(productPage,productSearchCriteria);
        return toPageResponse(page);
    }

    public ResponseEntity<ProductResponseRequest> insertProduct(ProductResponseRequest productResponseRequest){
        Product product = new Product();
        try{
            product = fromResponse(productResponseRequest);
            productRepository.save(product);
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Product was successfully saved");
        return new ResponseEntity<>(toResponse(product),HttpStatus.OK);
    }

    public ResponseEntity<List<ProductResponseRequest>> insertProduct(List<ProductResponseRequest> productResponseRequestList){
        List<Product> products = new ArrayList<>();
        try{
            products = fromResponse(productResponseRequestList);
            productRepository.saveAll(products);

        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Products were successfully saved");
        return new ResponseEntity<>(toResponse(products),HttpStatus.OK);
    }

   public ResponseEntity<ProductResponseRequest> getProductById(int id){
       Product product = new Product();
       try{
           product = productRepository.findById(id).get();
       }catch (Exception e){
           log.error(e.getMessage());
       }
       log.info("Product was successfully retrieved");
       return new ResponseEntity<>(toResponse(product),HttpStatus.OK);
   }

   public ResponseEntity<List<ProductResponseRequest>> getAll(){
        return new ResponseEntity<>(toResponse(productRepository.findAll()),HttpStatus.OK);
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

   private Product fromResponse(ProductResponseRequest productResponseRequest){
       Product product = new Product();
        if(productResponseRequest.getProduct_id()!=null&&productRepository.existsById(productResponseRequest.getProduct_id())){
            product = productRepository.findById(productResponseRequest.getProduct_id()).get();
            if(productResponseRequest.getCategory_id()!=null) {
                checkCategory(categoryRepository.findById(productResponseRequest.getCategory_id()).get().getName());
                product.setCategory(categoryRepository.findById(productResponseRequest.getCategory_id()).get());
            }
            if(productResponseRequest.getProduct_name()!=null) product.setProduct_name(productResponseRequest.getProduct_name());
            if(productResponseRequest.getPrice()!=null) product.setPrice(productResponseRequest.getPrice());
            if(productResponseRequest.getDescription()!=null) product.setDescription(productResponseRequest.getDescription());
            if(productResponseRequest.getPicture()!=null) product.setPicture(productResponseRequest.getPicture());
            if(productResponseRequest.getQuantity()!=null) product.setQuantity(productResponseRequest.getQuantity());
        } else {
            checkCategory(categoryRepository.findById(productResponseRequest.getCategory_id()).get().getName());
            product.setCategory(categoryRepository.findById(productResponseRequest.getCategory_id()).get());
            product.setProduct_name(productResponseRequest.getProduct_name());
            product.setPrice(productResponseRequest.getPrice());
            product.setDescription(productResponseRequest.getDescription());
            product.setPicture(productResponseRequest.getPicture());
            product.setQuantity(productResponseRequest.getQuantity());
        }
       return product;
   }

    private List<Product> fromResponse(List<ProductResponseRequest> productResponseRequestList){
        List<Product> productList = new ArrayList<>();
        for (ProductResponseRequest productResponseRequest:productResponseRequestList) {
            Product product = new Product();
            if(productResponseRequest.getProduct_id()!=null&&productRepository.existsById(productResponseRequest.getProduct_id())){
                product = productRepository.findById(productResponseRequest.getProduct_id()).get();
                if(productResponseRequest.getCategory_id()!=null) {
                    checkCategory(categoryRepository.findById(productResponseRequest.getCategory_id()).get().getName());
                    product.setCategory(categoryRepository.findById(productResponseRequest.getCategory_id()).get());
                }
                if(productResponseRequest.getProduct_name()!=null) product.setProduct_name(productResponseRequest.getProduct_name());
                if(productResponseRequest.getPrice()!=null) product.setPrice(productResponseRequest.getPrice());
                if(productResponseRequest.getDescription()!=null) product.setDescription(productResponseRequest.getDescription());
                if(productResponseRequest.getPicture()!=null) product.setPicture(productResponseRequest.getPicture());
                if(productResponseRequest.getQuantity()!=null) product.setQuantity(productResponseRequest.getQuantity());
            } else {
                checkCategory(categoryRepository.findById(productResponseRequest.getCategory_id()).get().getName());
                product.setCategory(categoryRepository.findById(productResponseRequest.getCategory_id()).get());
                product.setProduct_name(productResponseRequest.getProduct_name());
                product.setPrice(productResponseRequest.getPrice());
                product.setDescription(productResponseRequest.getDescription());
                product.setPicture(productResponseRequest.getPicture());
                product.setQuantity(productResponseRequest.getQuantity());
            }
            productList.add(product);
        }
        return productList;
    }

    private Page<ProductResponseRequest> toPageResponse(Page<Product> products){
       List<Product> productList =  products.getContent();
       List<ProductResponseRequest> productResponseRequestList = new ArrayList<>();
       for(Product product:productList){
           ProductResponseRequest productResponseRequest = new ProductResponseRequest();
           productResponseRequest.setProduct_id(product.getProduct_id());
           productResponseRequest.setProduct_name(product.getProduct_name());
           productResponseRequest.setPrice(product.getPrice());
           productResponseRequest.setDescription(product.getDescription());
           productResponseRequest.setPicture(product.getPicture());
           productResponseRequest.setQuantity(product.getQuantity());
           productResponseRequest.setCategory_id(product.getCategory().getCategory_id());
           productResponseRequestList.add(productResponseRequest);
       }
        return new PageImpl<>(productResponseRequestList,products.getPageable(),products.getSize());
    }

    private ProductResponseRequest toResponse(Product product){
        ProductResponseRequest productResponseRequest = new ProductResponseRequest();
        productResponseRequest.setProduct_id(product.getProduct_id());
        productResponseRequest.setProduct_name(product.getProduct_name());
        productResponseRequest.setPrice(product.getPrice());
        productResponseRequest.setDescription(product.getDescription());
        productResponseRequest.setPicture(product.getPicture());
        productResponseRequest.setQuantity(product.getQuantity());
        productResponseRequest.setCategory_id(product.getCategory().getCategory_id());
        return productResponseRequest;
    }

    private List<ProductResponseRequest> toResponse(List<Product> productList){
        List<ProductResponseRequest> productResponseRequestList = new ArrayList<>();
        for (Product product: productList) {
            ProductResponseRequest productResponseRequest = new ProductResponseRequest();
            productResponseRequest.setProduct_id(product.getProduct_id());
            productResponseRequest.setProduct_name(product.getProduct_name());
            productResponseRequest.setPrice(product.getPrice());
            productResponseRequest.setDescription(product.getDescription());
            productResponseRequest.setPicture(product.getPicture());
            productResponseRequest.setQuantity(product.getQuantity());
            productResponseRequest.setCategory_id(product.getCategory().getCategory_id());
            productResponseRequestList.add(productResponseRequest);
        }
        return productResponseRequestList;
    }

}
