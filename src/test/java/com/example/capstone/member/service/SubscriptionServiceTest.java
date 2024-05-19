package com.example.capstone.member.service;

import com.example.capstone.common.ValidateUtil;
import com.example.capstone.member.Member;
import com.example.capstone.member.Subscription;
import com.example.capstone.member.dto.SubscriptionResponseDTO;
import com.example.capstone.member.repository.SubscriptionRepository;
import com.example.capstone.seller.Seller;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@Slf4j
@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @InjectMocks
    SubscriptionService subscriptionService;

    @Mock
    ValidateUtil validateUtil;

    @Mock
    SubscriptionRepository subscriptionRepository;

    @Test
    void 구독_성공_테스트() {
        // given
        Member fromMember = Member.builder().id(1L).build();
        Member toMember = Member.builder().id(2L).build();
        Seller seller = Seller.builder().member(toMember).build();
        toMember.addSeller(seller);
        Subscription subscription = Subscription.builder()
                .id(1L)
                .fromMember(fromMember)
                .toMember(toMember.getSeller())
                .build();

        when(validateUtil.validMember(fromMember.getId())).thenReturn(fromMember);
        when(validateUtil.validMember(toMember.getId())).thenReturn(toMember);

//        when(subscriptionRepository.findByFromMemberIdAndToMemberMemberId(fromMember.getId(), toMember.getId())).thenReturn(null);
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);

        // when
        SubscriptionResponseDTO.Subscription subscribe = subscriptionService.subscribe(fromMember.getId(), toMember.getId());

        // then
        assertThat(subscribe).isInstanceOf(SubscriptionResponseDTO.Subscription.class);
        assertThat(subscribe.getFromMemberId()).isEqualTo(fromMember.getId());
        assertThat(subscribe.getToMemberId()).isEqualTo(toMember.getId());
    }

}