package com.example.capstone.item.service;

import com.example.capstone.exception.handler.ExceptionHandler;
import com.example.capstone.item.Item;
import com.example.capstone.item.Review;
import com.example.capstone.item.converter.ReviewConverter;
import com.example.capstone.item.dto.ReviewResponseDTO;
import com.example.capstone.item.repository.ItemRepository;
import com.example.capstone.item.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.capstone.apiPayload.code.status.ErrorStatus.ITEM_NOT_FOUND;
import static com.example.capstone.item.converter.ReviewConverter.toReviewListWithAVG;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReviewServiceImpl implements ReviewService{

    private final ItemRepository itemRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public ReviewResponseDTO.ReviewListWithAVG searchReviews(Long itemId, Integer page, Integer size) {
        Long validatedItemId = validateItem(itemId).getId();

        Double averageScore = reviewRepository.getAverageScore(itemId);

        Page<Review> reviewPage = reviewRepository.findByItemId(validatedItemId, PageRequest.of(page, size, Sort.by("createdAt").descending()));
        ReviewResponseDTO.ReviewList reviewList = ReviewConverter.toReviewList(reviewPage);

        return toReviewListWithAVG(averageScore, reviewList);
    }

    private Item validateItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ExceptionHandler(ITEM_NOT_FOUND));
    }
}
