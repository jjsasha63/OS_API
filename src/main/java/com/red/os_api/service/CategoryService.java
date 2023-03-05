package com.red.os_api.service;

import com.red.os_api.entity.Category;
import com.red.os_api.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements AppService<Category, Integer> {

    AppRepository<Category,Integer> categoryRep;

    @Autowired
    public void setCategoryRep(AppRepository<Category, Integer> categoryRep) {
        this.categoryRep = categoryRep;
    }

    @Override
    public List<Category> getAll() {
        List<Category> categoryList = new ArrayList<>();
        categoryRep.findAll().forEach(categoryList::add);
        return categoryList;
    }

    @Override
    public Category getById(Integer id) {
        return categoryRep.findById(id).get();
    }

    @Override
    public Category insert(Category category) {
        return categoryRep.save(category);
    }

    @Override
    public void update(Integer id, Category category) {
        Category categoryNew = categoryRep.findById(id).get();
        categoryNew.setName(category.getName());
        categoryNew.setDescription(category.getDescription());
        categoryRep.save(categoryNew);
    }

    @Override
    public void delete(Integer id) {
        categoryRep.delete(categoryRep.findById(id).get());
    }
}
