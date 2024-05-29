package com.example.capstone.member.converter;


import com.example.capstone.member.Address;
import com.example.capstone.member.Member;
import com.example.capstone.member.dto.AddressDTO;
import com.example.capstone.member.dto.MemberRequestDTO;
import com.example.capstone.member.dto.MemberResponseDTO;
import com.example.capstone.seller.converter.SellerConverter;

import java.util.Optional;

import static com.example.capstone.seller.converter.SellerConverter.toSeller;
import static com.example.capstone.seller.converter.SellerConverter.toSellerResponseDTO;


public class MemberConverter {


    public static MemberResponseDTO.MemberState toMemberState(Member member) {
        return MemberResponseDTO.MemberState.builder()
                .id(member.getId())
                .memberType(member.getType())
                .build();
    }

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

    public static Member toSignUpMember(MemberRequestDTO.SignUpMember signUpMember, String encodedPassword) {
        return Member.builder()
                .id(signUpMember.getId())
                .loginId(signUpMember.getLoginId())
                .password(encodedPassword)
                .name(signUpMember.getName())
                .nickName(signUpMember.getNickName())
                .phone(signUpMember.getPhone())
                .build();
    }

    public static Member toMember(MemberResponseDTO.Member member){
        return Member.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .password(member.getPassword())
                .name(member.getName())
                .nickName(member.getNickName())
                .phone(member.getPhone())
                .type(member.getMemberType())
                .status(member.getMemberStatus())
                .seller(toSeller(member.getSeller()))
                .build();
    }

    public static Address toAddress(AddressDTO.Address address) {
        return Address.builder()
                .member(toMember(address.getMember()))
                .address(address.getAddress())
                .details(address.getDetails())
                .build();
    }

}
