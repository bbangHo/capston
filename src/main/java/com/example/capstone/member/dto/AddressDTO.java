package com.example.capstone.member.dto;

import com.example.capstone.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AddressDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {

        private MemberResponseDTO.Member member;

        private String address;

        private String details;

    }
}
