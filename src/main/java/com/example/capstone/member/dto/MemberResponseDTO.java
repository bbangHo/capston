package com.example.capstone.member.dto;

import com.example.capstone.member.common.MemberStatus;
import com.example.capstone.member.common.MemberType;
import com.example.capstone.seller.Seller;
import com.example.capstone.seller.SellerResponseDTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Member{
        private Long id;

        private String loginId;

        private String password;

        private String name;

        private String nickName;

        private String phone;

        private MemberType memberType;

        private MemberStatus memberStatus;

        private SellerResponseDTO.Seller seller;
    }


}
