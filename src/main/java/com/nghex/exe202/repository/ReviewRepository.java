package com.nghex.exe202.repository;

import com.nghex.exe202.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewsByUserId(Long userId);
    List<Review> findReviewsByProductId(Long productId);
}
