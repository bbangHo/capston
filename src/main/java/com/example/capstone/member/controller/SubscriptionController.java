package com.example.capstone.member.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.apiPayload.code.status.SuccessStatus;
import com.example.capstone.member.dto.SubscriptionResponseDTO;
import com.example.capstone.member.service.SubscriptionService;
import com.example.capstone.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/{to-member-id}")
    public ApiResponse<SubscriptionResponseDTO.Subscription> subscribe(
            @AuthenticationPrincipal MemberSecurityDTO member,
            @PathVariable(name = "to-member-id") Long toMemberId
    ) {
        Long fromMemberId = member.getId();
        SubscriptionResponseDTO.Subscription result = subscriptionService.subscribe(fromMemberId, toMemberId);
        return ApiResponse.of(SuccessStatus._OK_SUBSCRIBE, result);
    }

    @DeleteMapping("/{to-member-id}")
    public ApiResponse<SubscriptionResponseDTO.Subscription> unsubscribe(
            @AuthenticationPrincipal MemberSecurityDTO member,
            @PathVariable(name = "to-member-id") Long toMemberId
    ) {
        Long fromMemberId = member.getId();
        SubscriptionResponseDTO.Subscription result = subscriptionService.unsubscribe(fromMemberId, toMemberId);
        return ApiResponse.of(SuccessStatus._OK_UNSUBSCRIBE, result);
    }
}
