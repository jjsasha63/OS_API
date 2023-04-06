package com.red.os_api.rest.admin;

import com.red.os_api.entity.Category;
import com.red.os_api.entity.CustomerDetails;
import com.red.os_api.entity.Product;
import com.red.os_api.entity.Token;
import com.red.os_api.entity.search.CategoryPage;
import com.red.os_api.entity.search.CategorySearchCriteria;
import com.red.os_api.service.CategoryService;
import com.red.os_api.service.CustomerDetailsService;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/store/api/admin/category")
public class CategoryAdminController {

    @Autowired
    private final CategoryService categoryService;


    @PostMapping("/insert-one")
    public ResponseEntity<Category> putCategory(@RequestBody Category category){
        return categoryService.insertCategory(category);
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<Category>> searchCategory(CategoryPage categoryPage, @RequestBody CategorySearchCriteria categorySearchCriteria){
        return new ResponseEntity<>(categoryService.getCategories(categoryPage,categorySearchCriteria),HttpStatus.OK);
    }

    @PostMapping("/insert-many")
    public ResponseEntity<List<Category>> putCategories(@RequestBody Category categories){
        return categoryService.insertCategory(categories.getCategories());
    }

    @PostMapping("/deleteById")
    public ResponseEntity<String> deleteCategory( @RequestParam(name = "id") Integer id){
        return categoryService.deleteCategoryById(id);
    }

    @GetMapping("/getById")
    public ResponseEntity<Category> getCategoryById(@RequestParam(name = "id") Integer id) {
        return categoryService.getCategoryById(id);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Category>> getCategories(){
        return categoryService.getAll();
    }

    @PutMapping("/update-one")
    public  ResponseEntity<Category> updateCategory(@RequestBody Category category){
       return categoryService.insertCategory(category);
    }

    @PutMapping("/update-many")
    public  ResponseEntity<List<Category>> updateProducts(@RequestBody Category categories){
        return categoryService.insertCategory(categories.getCategories());
    }


}
