package com.example.capstone.member.service;

import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.common.QueryService;
import com.example.capstone.exception.GeneralException;
import com.example.capstone.member.Member;
import com.example.capstone.member.common.MemberType;
import com.example.capstone.member.converter.MemberConverter;
import com.example.capstone.member.dto.MemberResponseDTO;
import com.example.capstone.member.repository.MemberRepository;
import com.example.capstone.seller.Seller;
import com.example.capstone.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final SellerRepository sellerRepository;
    private final QueryService queryService;

    public MemberResponseDTO.MemberState changeMemberRole(Long memberId) {
        Member member = queryService.findMember(memberId);

        if (member.getType() == MemberType.ROLE_SELLER) {
            throw new GeneralException(ErrorStatus.MEMBER_ALREADY_SELLER);
        }

        member.changeRole();
        memberRepository.save(member);

        Seller seller = Seller.builder()
                .member(member)
                .build();

        sellerRepository.save(seller);

        return MemberConverter.toMemberState(member);
    }
}
