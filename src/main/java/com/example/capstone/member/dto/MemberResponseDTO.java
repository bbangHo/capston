package com.example.capstone.member.dto;

import com.example.capstone.member.common.MemberStatus;
import com.example.capstone.member.common.MemberType;
import com.example.capstone.seller.dto.TempSellerResponseDTO;
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

        private TempSellerResponseDTO.Seller seller;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberState {
        private Long id;
        private MemberType memberType;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DupCheckField {

        private Long id;

        private String type;

        private String loginId;

        private String nickName;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpMember{

        private Long id;

        private String loginId;

        private String password;

        private String name;

        private String nickName;

        private String phone;

        private String address;

        private String details;
    }


}
