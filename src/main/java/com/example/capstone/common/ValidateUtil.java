package com.example.capstone.common;

import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.exception.GeneralException;
import com.example.capstone.member.Member;
import com.example.capstone.member.Subscription;
import com.example.capstone.member.repository.MemberRepository;
import com.example.capstone.member.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidateUtil {
    private final MemberRepository memberRepository;
    private final SubscriptionRepository subscriptionRepository;

    public Member validMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
    }

    public Boolean memberIsPresent(Long memberId) {
        return Optional.ofNullable(memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND)))
                .isPresent();
    }

    public Boolean isSubscribe(Long fromMemberId, Long toMemberId) {
        Optional<Subscription> subscriptionOptional = Optional.ofNullable(
                subscriptionRepository.findByFromMemberIdAndToMemberMemberId(fromMemberId, toMemberId));

        return subscriptionOptional.isPresent();
    }

    public Subscription validSubscription(Long fromMemberId, Long toMemberId) {
        Optional<Subscription> subscriptionOptional = Optional.ofNullable(
                subscriptionRepository.findByFromMemberIdAndToMemberMemberId(fromMemberId, toMemberId));

        return subscriptionOptional.orElseThrow(() -> new GeneralException(ErrorStatus.ALREADY_UNSUBSCRIBED));
    }
}
