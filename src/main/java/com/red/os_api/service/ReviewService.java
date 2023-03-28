package com.red.os_api.service;


import com.red.os_api.entity.Auth;
import com.red.os_api.entity.Product;
import com.red.os_api.entity.Review;
import com.red.os_api.entity.req_resp.ReviewRequest;
import com.red.os_api.entity.req_resp.ReviewResponse;
import com.red.os_api.repository.AuthRepository;
import com.red.os_api.repository.ProductRepository;
import com.red.os_api.repository.ReviewRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final AuthService authService;

    private final AuthRepository authRepository;

    private final ProductRepository productRepository;

    public ResponseEntity<ReviewResponse> insertReview(ReviewRequest reviewRequest, @NonNull HttpServletRequest request,
                                                       @NonNull HttpServletResponse response,
                                                       @NonNull FilterChain filterChain){
        Review review = new Review();
        try{
            review = convertToEntity(reviewRequest,request,response,filterChain);
            reviewRepository.save(review);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("The review was added successfully");
        return new ResponseEntity<>(convertToResponse(review),HttpStatus.OK);
    }

    public ResponseEntity<List<ReviewResponse>> insertReview(List<ReviewRequest> reviewRequestList, @NonNull HttpServletRequest request,
                                                       @NonNull HttpServletResponse response,
                                                       @NonNull FilterChain filterChain){
        List<Review> reviews = new ArrayList<>();
        try{
            reviews = convertToEntity(reviewRequestList,request,response,filterChain);
            reviewRepository.saveAll(reviews);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("The review was added successfully");
        return new ResponseEntity<>(convertToResponse(reviews),HttpStatus.OK);
    }

    public ResponseEntity<ReviewResponse> getUserReviewById(Integer id,@NonNull HttpServletRequest request,
                                                            @NonNull HttpServletResponse response,
                                                            @NonNull FilterChain filterChain){
        Review review = new Review();
        try {
            review = reviewRepository.findByReviewIdAndAuth(id,authRepository.getReferenceById(authService.getUserId(request,response,filterChain))).get();
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("The review was retrieved for id " + id);
        return new ResponseEntity<>(convertToResponse(review),HttpStatus.OK);
    }

    public ResponseEntity<ReviewResponse> getUserReviewById(Integer id){
        Review review = new Review();
        try {
            review = reviewRepository.getReferenceById(id);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("The review was retrieved for id " + id);
        return new ResponseEntity<>(convertToResponse(review),HttpStatus.OK);
    }

    public ResponseEntity<List<ReviewResponse>> getAllUserReviews(@NonNull HttpServletRequest request,
                                                            @NonNull HttpServletResponse response,
                                                            @NonNull FilterChain filterChain){
        List<Review> reviews = new ArrayList<>();
        try {
            reviews = reviewRepository.findAllByAuth(authRepository.getReferenceById(authService.getUserId(request,response,filterChain)));
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("All reviews were retrieved for the user");
        return new ResponseEntity<>(convertToResponse(reviews),HttpStatus.OK);
    }

    public ResponseEntity<List<ReviewResponse>> getAllUserReviews(Integer auth_id){
        List<Review> reviews = new ArrayList<>();
        try {
            reviews = reviewRepository.findAllByAuth(authRepository.getReferenceById(auth_id));
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("All reviews were retrieved for the user");
        return new ResponseEntity<>(convertToResponse(reviews),HttpStatus.OK);
    }

    public ResponseEntity<List<ReviewResponse>> getAllReviews(){
        List<Review> reviews = new ArrayList<>();
        try{
            reviews = reviewRepository.findAll();
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("All reviews were retrieved");
        return new ResponseEntity<>(convertToResponse(reviews),HttpStatus.OK);
    }

    public ResponseEntity<String> deleteByIdForUser(Integer id,@NonNull HttpServletRequest request,
                                                    @NonNull HttpServletResponse response,
                                                    @NonNull FilterChain filterChain){
        try{
//            reviewRepository.deleteByReviewIdAndAuth(id,
//                    authRepository.findById(authService.getUserId(request,response,filterChain)).get());
            reviewRepository.delete(reviewRepository.findByReviewIdAndAuth(id,
                    authRepository.findById(authService.getUserId(request,response,filterChain))
                            .get())
                    .get());
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("The review was deleted for a user");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<String> deleteByIdForAdmin(Integer id){
        try{
            reviewRepository.deleteById(id);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("The review was deleted for a user");
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private Review convertToEntity(ReviewRequest reviewRequest, @NonNull HttpServletRequest request,
                                   @NonNull HttpServletResponse response,
                                   @NonNull FilterChain filterChain) throws ServletException, IOException, NoSuchFieldException {
        Review review = new Review();
        if(reviewRequest.getReview_id()!=null)
            review.setReviewId(reviewRequest.getReview_id());
        review.setAuth(authRepository.getReferenceById(authService.getUserId(request,response,filterChain)));
        if(!productRepository.existsById(reviewRequest.getProduct_id())) throw new NoSuchFieldException("The product wasn't found");
        review.setProduct(productRepository.findById(reviewRequest.getProduct_id()).get());
        review.setGrade(reviewRequest.getGrade());
        review.setReview_text(reviewRequest.getReview_text());
        review.setReview_date(LocalDateTime.now());
        return review;
    }

    private List<Review> convertToEntity(List<ReviewRequest> reviewRequestList, @NonNull HttpServletRequest request,
                                         @NonNull HttpServletResponse response,
                                         @NonNull FilterChain filterChain) throws ServletException, IOException, NoSuchFieldException {
        List<Review> reviews = new ArrayList<>();
        for(ReviewRequest reviewRequest:reviewRequestList) {
            Review review = new Review();
            if (reviewRequest.getReview_id() != null) review.setReviewId(reviewRequest.getReview_id());
            review.setAuth(authRepository.getReferenceById(authService.getUserId(request, response, filterChain)));
            if (!productRepository.existsById(reviewRequest.getProduct_id()))
                throw new NoSuchFieldException("The product wasn't found");
            review.setProduct(productRepository.getReferenceById(reviewRequest.getReview_id()));
            review.setGrade(reviewRequest.getGrade());
            review.setReview_text(reviewRequest.getReview_text());
            review.setReview_date(LocalDateTime.now());
            reviews.add(review);
        }
        return reviews;
    }

    private ReviewResponse convertToResponse(Review review){
        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setAuth_id(review.getAuth().getId());
        reviewResponse.setProduct_id(review.getProduct().getProduct_id());
        reviewResponse.setReview_id(review.getReviewId());
        reviewResponse.setGrade(review.getGrade());
        reviewResponse.setReview_text(review.getReview_text());
        reviewResponse.setReview_date(review.getReview_date());
        return reviewResponse;
    }

    private List<ReviewResponse> convertToResponse(List<Review> reviews){
        List<ReviewResponse> reviewResponseList = new ArrayList<>();
        for(Review review: reviews) {
            ReviewResponse reviewResponse = new ReviewResponse();
            reviewResponse.setAuth_id(review.getAuth().getId());
            reviewResponse.setProduct_id(review.getProduct().getProduct_id());
            reviewResponse.setReview_id(review.getReviewId());
            reviewResponse.setGrade(review.getGrade());
            review.setReview_text(review.getReview_text());
            review.setReview_date(review.getReview_date());
            reviewResponseList.add(reviewResponse);
        }
        return reviewResponseList;
    }


}
