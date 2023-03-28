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
@RequestMapping("/store/api/account")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/addReview-one")
    public ResponseEntity<ReviewResponse> insertReview(@RequestBody ReviewRequest reviewRequest,@NonNull HttpServletRequest request,
                                                       @NonNull HttpServletResponse response,
                                                       @NonNull FilterChain filterChain){
        return reviewService.insertReview(reviewRequest,request,response,filterChain);
    }

    @PostMapping("/addReview-many")
    public ResponseEntity<List<ReviewResponse>> insertReviews(@RequestBody ReviewRequest reviewRequest, @NonNull HttpServletRequest request,
                                                             @NonNull HttpServletResponse response,
                                                             @NonNull FilterChain filterChain){
        return reviewService.insertReview(reviewRequest.getReviewRequestList(),request,response,filterChain);
    }


    @PutMapping("/updateReview-one")
    public ResponseEntity<ReviewResponse> updateReview(@RequestBody ReviewRequest reviewRequest,@NonNull HttpServletRequest request,
                                                       @NonNull HttpServletResponse response,
                                                       @NonNull FilterChain filterChain){
        return reviewService.insertReview(reviewRequest,request,response,filterChain);
    }

    @PutMapping("/updateReview-many")
    public ResponseEntity<List<ReviewResponse>> updateReviews(@RequestBody ReviewRequest reviewRequest, @NonNull HttpServletRequest request,
                                                              @NonNull HttpServletResponse response,
                                                              @NonNull FilterChain filterChain){
        return reviewService.insertReview(reviewRequest.getReviewRequestList(),request,response,filterChain);
    }

    @PostMapping("/deleteReviewById")
    public ResponseEntity<String> deleteById(@RequestParam Integer id, @NonNull HttpServletRequest request,
                                                              @NonNull HttpServletResponse response,
                                                              @NonNull FilterChain filterChain){
        return reviewService.deleteByIdForUser(id,request,response,filterChain);
    }


    @GetMapping("/getReviewById")
    public ResponseEntity<ReviewResponse> getById(@RequestParam Integer id, @NonNull HttpServletRequest request,
                                             @NonNull HttpServletResponse response,
                                             @NonNull FilterChain filterChain){
        return reviewService.getUserReviewById(id,request,response,filterChain);
    }

    @GetMapping("/getReviewAll")
    public ResponseEntity<List<ReviewResponse>> getAll( @NonNull HttpServletRequest request,
                                                  @NonNull HttpServletResponse response,
                                                  @NonNull FilterChain filterChain){
        return reviewService.getAllUserReviews(request,response,filterChain);
    }



}
