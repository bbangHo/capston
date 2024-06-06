package com.example.capstone.inquiry.dto;

import com.example.capstone.inquiry.common.InquiryStatus;
import com.example.capstone.item.Item;
import com.example.capstone.member.Member;
import com.example.capstone.seller.Seller;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class InquiryRequestDTO {


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class requestedInquiry{

        private Long itemId;

        private String content;

    }


}
