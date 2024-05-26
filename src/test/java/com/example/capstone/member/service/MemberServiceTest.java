package com.example.capstone.member.service;

import com.example.capstone.common.QueryService;
import com.example.capstone.exception.GeneralException;
import com.example.capstone.member.Member;
import com.example.capstone.member.common.MemberType;
import com.example.capstone.member.dto.MemberResponseDTO;
import com.example.capstone.member.repository.MemberRepository;
import com.example.capstone.seller.Seller;
import com.example.capstone.seller.repository.SellerRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    MemberServiceImpl memberService;

    @Mock
    QueryService queryService;

    @Mock
    MemberRepository memberRepository;

    @Mock
    SellerRepository sellerRepository;

    @Test
    void 성공테스트_구매자를_판매자로_전환() {
        //given
        Member member = Member.builder().id(1L).build();
        Seller seller = Seller.builder().member(member).build();

        when(queryService.findMember(member.getId())).thenReturn(member);
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        when(sellerRepository.save(any(Seller.class))).thenReturn(seller);

        //when
        MemberResponseDTO.MemberState memberState = memberService.changeMemberRole(member.getId());

        //then
        Assertions.assertThat(memberState.getMemberType()).isEqualTo(MemberType.ROLE_SELLER);
    }

    @Test
    void 실패테스트_이미_판매자인_사용자를_판매자로_전환() {
        //given
        Member member = Member.builder().id(1L).type(MemberType.ROLE_SELLER).build();

        when(queryService.findMember(member.getId())).thenReturn(member);

        //when

        //then
        assertThrows(GeneralException.class, () ->
                memberService.changeMemberRole(member.getId()));
    }

}