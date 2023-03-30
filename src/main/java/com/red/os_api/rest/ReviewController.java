package com.red.os_api.rest;

import com.red.os_api.entity.req_resp.ReviewRequest;
import com.red.os_api.entity.req_resp.ReviewResponse;
import com.red.os_api.service.ReviewService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store/api/account/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/add-one")
    public ResponseEntity<ReviewResponse> insertReview(@RequestBody ReviewRequest reviewRequest,@NonNull HttpServletRequest request,
                                                       @NonNull HttpServletResponse response,
                                                       @NonNull FilterChain filterChain){
        return reviewService.insertReview(reviewRequest,request,response,filterChain);
    }

    @PostMapping("/add-many")
    public ResponseEntity<List<ReviewResponse>> insertReviews(@RequestBody ReviewRequest reviewRequest, @NonNull HttpServletRequest request,
                                                             @NonNull HttpServletResponse response,
                                                             @NonNull FilterChain filterChain){
        return reviewService.insertReview(reviewRequest.getReviewList(),request,response,filterChain);
    }


    @PutMapping("/update-one")
    public ResponseEntity<ReviewResponse> updateReview(@RequestBody ReviewRequest reviewRequest,@NonNull HttpServletRequest request,
                                                       @NonNull HttpServletResponse response,
                                                       @NonNull FilterChain filterChain){
        return reviewService.insertReview(reviewRequest,request,response,filterChain);
    }

    @PutMapping("/update-many")
    public ResponseEntity<List<ReviewResponse>> updateReviews(@RequestBody ReviewRequest reviewRequest, @NonNull HttpServletRequest request,
                                                              @NonNull HttpServletResponse response,
                                                              @NonNull FilterChain filterChain){
        return reviewService.insertReview(reviewRequest.getReviewList(),request,response,filterChain);
    }

    @PostMapping("/deleteById")
    public ResponseEntity<String> deleteById(@RequestParam Integer id, @NonNull HttpServletRequest request,
                                                              @NonNull HttpServletResponse response,
                                                              @NonNull FilterChain filterChain){
        return reviewService.deleteByIdForUser(id,request,response,filterChain);
    }


    @GetMapping("/getById")
    public ResponseEntity<ReviewResponse> getById(@RequestParam Integer id, @NonNull HttpServletRequest request,
                                             @NonNull HttpServletResponse response,
                                             @NonNull FilterChain filterChain){
        return reviewService.getUserReviewById(id,request,response,filterChain);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ReviewResponse>> getAll( @NonNull HttpServletRequest request,
                                                  @NonNull HttpServletResponse response,
                                                  @NonNull FilterChain filterChain){
        return reviewService.getAllUserReviews(request,response,filterChain);
    }



}
