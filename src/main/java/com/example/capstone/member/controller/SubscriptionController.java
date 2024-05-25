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
@RequestMapping
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping("/auth/test")
    public ApiResponse<String> test(@AuthenticationPrincipal MemberSecurityDTO memberSecurityDTO) {
        return ApiResponse.onSuccess(memberSecurityDTO.getLoginId().toString());
    }

    // TODO: 사용자 검증 어노테이션 추가
    @PostMapping("/subscription/{to-member-id}")
    public ApiResponse<SubscriptionResponseDTO.Subscription> subscribe(
            @AuthenticationPrincipal MemberSecurityDTO member,
            @PathVariable(name = "to-member-id") Long toMemberId
    ) {
        Long fromMemberId = member.getId();
        SubscriptionResponseDTO.Subscription result = subscriptionService.subscribe(fromMemberId, toMemberId);
        return ApiResponse.of(SuccessStatus._OK_SUBSCRIBE, result);
    }

    @DeleteMapping("/subscription/{to-member-id}")
    public ApiResponse<SubscriptionResponseDTO.Subscription> unsubscribe(
            @AuthenticationPrincipal MemberSecurityDTO member,
            @PathVariable(name = "to-member-id") Long toMemberId
    ) {
        Long fromMemberId = member.getId();
        SubscriptionResponseDTO.Subscription result = subscriptionService.unsubscribe(fromMemberId, toMemberId);
        return ApiResponse.of(SuccessStatus._OK_UNSUBSCRIBE, result);
    }
}
