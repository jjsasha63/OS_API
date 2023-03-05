package com.red.os_api.service;

import com.red.os_api.entity.Category;
import com.red.os_api.entity.Review;
import com.red.os_api.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService implements AppService<Review, Integer> {

    AppRepository<Review,Integer> reviewRep;

    @Autowired
    public void setReviewRep(AppRepository<Review, Integer> reviewRep) {
        this.reviewRep = reviewRep;
    }

    @Override
    public List<Review> getAll() {
        List<Review> reviewList = new ArrayList<>();
        reviewRep.findAll().forEach(reviewList::add);
        return reviewList;
    }

    @Override
    public Review getById(Integer id) {
        return reviewRep.findById(id).get();
    }

    @Override
    public Review insert(Review review) {
        return reviewRep.save(review);
    }

    @Override
    public void update(Integer id, Review review) {
        Review reviewNew = reviewRep.findById(id).get();
        reviewNew.setGrade(review.getGrade());
        reviewNew.setReview_text(review.getReview_text());
        reviewNew.setReview_date(review.getReview_date());
        reviewNew.setCustomers(review.getCustomers());
        reviewNew.setProducts(review.getProducts());
        reviewRep.save(reviewNew);
    }

    @Override
    public void delete(Integer id) {
        reviewRep.delete(reviewRep.findById(id).get());
    }
}
