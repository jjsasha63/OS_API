package com.red.os_api.repository;

import com.red.os_api.entity.Category;
import com.red.os_api.entity.Product;
import com.red.os_api.entity.search.CategoryPage;
import com.red.os_api.entity.search.CategorySearchCriteria;
import com.red.os_api.entity.search.ProductPage;
import com.red.os_api.entity.search.ProductSearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class CategoryCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    private final CategoryRepository categoryRepository;

    public Page<Category> findByFilters(CategoryPage categoryPage, CategorySearchCriteria categorySearchCriteria) {
        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
        Root<Category> categoryRoot = criteriaQuery.from(Category.class);
        Predicate predicate = getPredicate(categorySearchCriteria, categoryRoot);
        criteriaQuery.where(predicate);
        setOrder(categoryPage, criteriaQuery, categoryRoot);
        TypedQuery<Category> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(categoryPage.getPageNum() * categoryPage.getPageSize());
        query.setMaxResults(categoryPage.getPageSize());
        Pageable pageable = getPageable(categoryPage);
        return new PageImpl<>(query.getResultList(),pageable,categoryPage.getPageSize());
    }


    private Pageable getPageable(CategoryPage categoryPage) {
        Sort sort = Sort.by(categoryPage.getSortDirection(), categoryPage.getSortBy());
        return PageRequest.of(categoryPage.getPageNum(),categoryPage.getPageSize(),sort);
    }

    private void setOrder(CategoryPage categoryPage, CriteriaQuery<Category> criteriaQuery, Root<Category> categoryRoot) {
        if(categoryPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(categoryRoot.get(categoryPage.getSortBy())));
        } else{
            criteriaQuery.orderBy(criteriaBuilder.desc(categoryRoot.get(categoryPage.getSortBy())));
        }
    }

    private Predicate getPredicate(CategorySearchCriteria categorySearchCriteria, Root<Category> categoryRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(categorySearchCriteria.getName())){
            predicates.add(criteriaBuilder.like(categoryRoot.get("name"),
                    "%" + categorySearchCriteria.getName() + "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
