package com.red.os_api.repository;

import com.red.os_api.entity.Product;
import com.red.os_api.entity.search.ProductPage;
import com.red.os_api.entity.search.ProductSearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class ProductCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    private final CategoryRepository categoryRepository;

    public Page<Product> findByFilters(ProductPage productPage, ProductSearchCriteria productSearchCriteria) {
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        Predicate predicate = getPredicate(productSearchCriteria, productRoot);
        criteriaQuery.where(predicate);
        setOrder(productPage, criteriaQuery, productRoot);
        TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(productPage.getPageNum() * productPage.getPageSize());
        query.setMaxResults(productPage.getPageSize());
        Pageable pageable = getPageable(productPage);
        return new PageImpl<>(query.getResultList(),pageable,productPage.getPageSize());
    }


    private Pageable getPageable(ProductPage productPage) {
        Sort sort = Sort.by(productPage.getSortDirection(), productPage.getSortBy());
        return PageRequest.of(productPage.getPageNum(),productPage.getPageSize(),sort);
    }

    private void setOrder(ProductPage productPage, CriteriaQuery<Product> criteriaQuery, Root<Product> productRoot) {
        if(productPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(productRoot.get(productPage.getSortBy())));
        } else{
            criteriaQuery.orderBy(criteriaBuilder.desc(productRoot.get(productPage.getSortBy())));
        }
    }

    private Predicate getPredicate(ProductSearchCriteria productSearchCriteria, Root<Product> productRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(productSearchCriteria.getProduct_name())){
            predicates.add(criteriaBuilder.like(productRoot.get("product_name"),
                    "%" + productSearchCriteria.getProduct_name() + "%"));
        }
        if(Objects.nonNull(productSearchCriteria.getPrice_max())){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(productRoot.get("price"),productSearchCriteria.getPrice_max()));
        }
        if(Objects.nonNull(productSearchCriteria.getPrice_min())){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(productRoot.get("price"),productSearchCriteria.getPrice_min()));
        }
        if(Objects.nonNull(productSearchCriteria.getCategory_name())){
            predicates.add(criteriaBuilder.equal(productRoot.get("category"),
                    categoryRepository.findCategoryByName(productSearchCriteria.getCategory_name())));
         }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
