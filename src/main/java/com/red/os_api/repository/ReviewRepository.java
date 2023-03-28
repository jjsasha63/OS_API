package com.red.os_api.repository;

import com.red.os_api.entity.Auth;
import com.red.os_api.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Integer> {

    Optional<Review> findByReviewIdAndAuth(Integer id, Auth auth);

    List<Review> findAllByAuth(Auth auth);




}
