package com.example.capstone.inquiry.service;

import com.example.capstone.inquiry.dto.InquiryRequestDTO;
import com.example.capstone.inquiry.dto.InquiryResponseDTO;

public interface InquiryService {

    InquiryResponseDTO.InquiryList searchInquiries(Long itemId, Integer page, Integer size);
    void addInquiry(InquiryRequestDTO.requestedInquiry inquiry, Long memberId);

    }
