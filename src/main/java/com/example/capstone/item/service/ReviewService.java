package com.example.capstone.item.service;

import com.example.capstone.inquiry.dto.InquiryResponseDTO;
import com.example.capstone.item.dto.ReviewResponseDTO;

public interface ReviewService {

    ReviewResponseDTO.ReviewListWithAVG searchReviews(Long itemId, Integer page, Integer size);

}
