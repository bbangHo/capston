package com.example.capstone.member.converter;

import com.example.capstone.member.Subscription;
import com.example.capstone.member.dto.SubscriptionResponseDTO;

import java.time.LocalDateTime;

public class SubscriptionConverter {

    public static SubscriptionResponseDTO.Subscription toSubscription(Subscription subscription) {
        return SubscriptionResponseDTO.Subscription.builder()
                .isSubscribe(true)
                .performedAt(subscription.getCreatedAt())
                .fromMemberId(subscription.getFromMember().getId())
                .toMemberId(subscription.getToMember().getMember().getId())
                .build();
    }

    public static SubscriptionResponseDTO.Subscription toUnsubscription(Subscription subscription) {
        return SubscriptionResponseDTO.Subscription.builder()
                .isSubscribe(false)
                .performedAt(LocalDateTime.now())
                .fromMemberId(subscription.getFromMember().getId())
                .toMemberId(subscription.getToMember().getMember().getId())
                .build();
    }
}
