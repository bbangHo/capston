package com.example.capstone.item.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.inquiry.dto.InquiryResponseDTO;
import com.example.capstone.item.dto.ReviewResponseDTO;
import com.example.capstone.item.service.ReviewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public ApiResponse<ReviewResponseDTO.ReviewListWithAVG> getItemList(@Valid @Positive @RequestParam(name = "itemId") Long itemId,
                                                                  @Min(1) @RequestParam(name = "page") Integer page,
                                                                  @Positive @RequestParam(name = "size") Integer size) {

        return ApiResponse.onSuccess(reviewService.searchReviews( itemId, page - 1, size));
    }
}
