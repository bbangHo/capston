package com.example.capstone.inquiry.dto;

import com.example.capstone.inquiry.common.InquiryStatus;
import com.example.capstone.item.dto.GroupItemResponseDTO;
import com.example.capstone.item.dto.ItemResponseDTO;
import com.example.capstone.member.dto.MemberResponseDTO;
import com.example.capstone.seller.SellerResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class InquiryResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Inquiry{
        private Long id;

        private Long fromMemberId;

        private String fromMemberNickname;

        private InquiryStatus status;

        private String content;

        private String answer;

        private LocalDateTime createdAt;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InquiryList {
        private Integer listSize;
        private Integer page;
        private Long totalElement;
        private Boolean isFirst;
        private Boolean isLast;
        private List<Inquiry> InquiryList;
    }

}
