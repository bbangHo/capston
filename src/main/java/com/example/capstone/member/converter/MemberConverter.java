package com.example.capstone.member.converter;


import com.example.capstone.member.Member;
import com.example.capstone.member.dto.MemberResponseDTO;
import com.example.capstone.seller.converter.SellerConverter;

import java.util.Optional;

import static com.example.capstone.seller.converter.SellerConverter.toSellerResponseDTO;


public class MemberConverter {

    public static MemberResponseDTO.Member toMemberResponseDTO(Member member) {
        return MemberResponseDTO.Member.builder()
                .id(member.getId())
                .memberStatus(member.getStatus())
                .memberType(member.getType())
                .loginId(member.getLoginId())
                .password(member.getPassword())
                .phone(member.getPhone())
                .nickName(member.getNickName())
                .name(member.getName())
                .seller(Optional.ofNullable(member.getSeller())
                        .map(SellerConverter::toSellerResponseDTO)
                        .orElse(null))
                .build();
    }

}
