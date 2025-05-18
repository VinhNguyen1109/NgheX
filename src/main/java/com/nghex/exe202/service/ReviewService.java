package com.nghex.exe202.service;

import com.nghex.exe202.entity.Product;
import com.nghex.exe202.entity.Review;
import com.nghex.exe202.entity.User;
import com.nghex.exe202.exception.ReviewNotFoundException;
import com.nghex.exe202.request.CreateReviewRequest;

import javax.naming.AuthenticationException;
import java.util.List;

public interface ReviewService {
    Review createReview(CreateReviewRequest req,
                        User user,
                        Product product);
    List<Review> getReviewsByProductId(Long productId);
    Review updateReview(Long reviewId,
                        String reviewText,
                        double rating,
                        Long userId) throws ReviewNotFoundException, AuthenticationException;
    void deleteReview(Long reviewId, Long userId) throws ReviewNotFoundException, AuthenticationException;

}
