package com.example.capstone.member.service;

import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.common.ValidateUtil;
import com.example.capstone.exception.GeneralException;
import com.example.capstone.member.Subscription;
import com.example.capstone.member.converter.SubscriptionConverter;
import com.example.capstone.member.dto.SubscriptionResponseDTO;
import com.example.capstone.member.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final ValidateUtil validateUtil;

    public SubscriptionResponseDTO.Subscription subscribe(Long fromMemberId, Long toMemberId) {
        if (validateUtil.isSubscribe(fromMemberId, toMemberId)) {
            throw new GeneralException(ErrorStatus.ALREADY_SUBSCRIBED);
        }

        Subscription build = Subscription.builder()
                .fromMember(validateUtil.validMember(fromMemberId))
                .toMember(validateUtil.validMember(toMemberId).getSeller())
                .build();

        Subscription subscription = subscriptionRepository.save(build);

        return SubscriptionConverter.toSubscription(subscription);
    }

    public SubscriptionResponseDTO.Subscription unsubscribe(Long fromMemberId, Long toMemberId) {
        Subscription valid = validateUtil.validSubscription(fromMemberId, toMemberId);
        subscriptionRepository.delete(valid);
        return SubscriptionConverter.toUnsubscription(valid);
    }
}
