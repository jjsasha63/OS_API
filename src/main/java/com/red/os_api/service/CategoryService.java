package com.red.os_api.service;

import com.red.os_api.entity.Category;
import com.red.os_api.entity.search.CategoryPage;
import com.red.os_api.entity.search.CategorySearchCriteria;
import com.red.os_api.repository.CategoryCriteriaRepository;
import com.red.os_api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final CategoryCriteriaRepository categoryCriteriaRepository;

    public Page<Category> getCategories(CategoryPage categoryPage, CategorySearchCriteria categorySearchCriteria){
        return categoryCriteriaRepository.findByFilters(categoryPage,categorySearchCriteria);
    }

    public ResponseEntity<Category> getCategoryById(Integer id){
        Category category = new Category();
        try{
            category = categoryRepository.findById(id).get();
        } catch (NoSuchElementException e){
            log.error(e.toString());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("The category was retrieved");
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    public ResponseEntity<Category> insertCategory(Category category){
            try{
                categoryRepository.save(category);
            } catch (Exception e){
                log.error(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            log.info("The category was inserted");
            return new ResponseEntity<>(category,HttpStatus.OK);
    }

    public ResponseEntity<List<Category>> insertCategory(List<Category> categories){
        try{
            for(Category category: categories) categoryRepository.save(category);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Categories were inserted");
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

    public ResponseEntity<String> deleteCategoryById(int id){
        try{
            categoryRepository.deleteById(id);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("The product was successfully deleted");
        return verifyCategoryId(id) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<String> deleteCategoryByName(String name){
        try{
            categoryRepository.delete(categoryRepository.findCategoryByName(name));
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("The product was successfully deleted");
        return verifyCategoryName(name) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<List<Category>> getAll(){
        return new ResponseEntity<>(categoryRepository.findAll(),HttpStatus.OK);
    }


    private boolean verifyCategoryId(Integer id){
        return categoryRepository.existsById(id);
    }

    private boolean verifyCategoryName(String name){
        return categoryRepository.existsCategoryByName(name);
    }



}
