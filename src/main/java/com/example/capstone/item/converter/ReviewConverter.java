package com.example.capstone.item.converter;


import com.example.capstone.item.Review;
import com.example.capstone.item.dto.ReviewResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public class ReviewConverter {

    public static ReviewResponseDTO.Review toReviewResponseDTO(Review review) {
        return ReviewResponseDTO.Review.builder()
                .id(review.getId())
                .itemId(review.getItem().getId())
                .orderItemId(review.getOrderItem().getId())
                .score(review.getScore())
                .content(review.getContent())
                .createAt(review.getCreatedAt())
                .build();
    }

    public static ReviewResponseDTO.ReviewList toReviewList (Page<Review> reviewPage) {
        List<ReviewResponseDTO.Review> reviewList = reviewPage.stream()
                .map(ReviewConverter::toReviewResponseDTO)
                .toList();

        return ReviewResponseDTO.ReviewList.builder()
                .listSize(reviewPage.getSize())
                .page(reviewPage.getTotalPages())
                .totalElement(reviewPage.getTotalElements())
                .isFirst(reviewPage.isFirst())
                .isLast(reviewPage.isLast())
                .reviewList(reviewList)
                .build();
    }

    public static ReviewResponseDTO.ReviewListWithAVG toReviewListWithAVG (Double averageScore, ReviewResponseDTO.ReviewList reviewList) {

        return ReviewResponseDTO.ReviewListWithAVG.builder()
                .averageScore(averageScore)
                .listSize(reviewList.getListSize())
                .page(reviewList.getPage())
                .totalElement(reviewList.getTotalElement())
                .isFirst(reviewList.getIsFirst())
                .isLast(reviewList.getIsLast())
                .reviewList(reviewList.getReviewList())
                .build();
    }
}
