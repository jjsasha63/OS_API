package com.red.os_api.rest.admin;


import com.red.os_api.entity.req_resp.ReviewResponse;
import com.red.os_api.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/store/api/admin/review")
public class ReviewAdminController {

    private final ReviewService reviewService;

    @PostMapping("/deleteById")
    public ResponseEntity<String> deleteById(Integer id){
        return reviewService.deleteByIdForAdmin(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ReviewResponse>> getAllReviews(){
        return reviewService.getAllReviews();
    }

    @GetMapping("/getById")
    public ResponseEntity<ReviewResponse> getAllReviewsById(@RequestParam Integer id){
        return reviewService.getUserReviewById(id);
    }

    @GetMapping("/getByAuth")
    public ResponseEntity<List<ReviewResponse>> getAllReviewsByAuth(@RequestParam Integer auth){
        return reviewService.getAllUserReviews(auth);
    }




}
