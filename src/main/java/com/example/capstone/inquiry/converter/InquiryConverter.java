package com.example.capstone.inquiry.converter;

import com.example.capstone.inquiry.Inquiry;
import com.example.capstone.inquiry.dto.InquiryResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.example.capstone.item.converter.ItemConverter.toItemResponseDTO;
import static com.example.capstone.member.converter.MemberConverter.toMemberResponseDTO;
import static com.example.capstone.seller.converter.SellerConverter.toSellerResponseDTO;


public class InquiryConverter {


    public static InquiryResponseDTO.Inquiry toInquiryResponseDTO(Inquiry inquiry) {
        return InquiryResponseDTO.Inquiry.builder()
                .id(inquiry.getId())
                .fromMemberId(inquiry.getFromMember().getId())
                .fromMemberNickname(inquiry.getFromMember().getNickName())
                .status(inquiry.getStatus())
                .content(inquiry.getContent())
                .answer(inquiry.getAnswer())
                .createdAt(inquiry.getCreatedAt())
                .build();
    }

    public static InquiryResponseDTO.InquiryList toInquiryList (Page<Inquiry> inquiries) {
        List<InquiryResponseDTO.Inquiry> inquiryList = inquiries.stream()
                .map(InquiryConverter::toInquiryResponseDTO)
                .toList();


        return InquiryResponseDTO.InquiryList.builder()
                .listSize(inquiries.getSize())
                .page(inquiries.getTotalPages())
                .totalElement(inquiries.getTotalElements())
                .isFirst(inquiries.isFirst())
                .isLast(inquiries.isLast())
                .InquiryList(inquiryList)
                .build();
    }
}
