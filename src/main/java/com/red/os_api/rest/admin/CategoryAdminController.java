package com.red.os_api.rest.admin;

import com.red.os_api.entity.Category;
import com.red.os_api.entity.CustomerDetails;
import com.red.os_api.entity.Product;
import com.red.os_api.entity.Token;
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

    @PostMapping("/insert-many")
    public ResponseEntity<List<Category>> putCategories(@RequestBody Category categories){
        return categoryService.insertCategory(categories.getCategories());
    }

    @PostMapping("/deleteById")
    public ResponseEntity<String> deleteCategory(@Nullable @RequestParam(name = "id") Integer id, @Nullable @RequestParam(name = "name") String name){
        if(id != null) return categoryService.deleteCategoryById(id);
        else return categoryService.deleteCategoryByName(name);
    }

//    @PostMapping("/deleteByName")
//    public ResponseEntity<String> deleteCategoryByName(@RequestParam(name = "name") String name){
//        return categoryService.deleteCategoryByName(name);
//    }

    @PutMapping("/update-one")
    public  ResponseEntity<Category> updateCategory(@RequestBody Category category){
       return categoryService.insertCategory(category);
    }

    @PutMapping("/update-many")
    public  ResponseEntity<List<Category>> updateProducts(@RequestBody Category categories){
        return categoryService.insertCategory(categories.getCategories());
    }


}
